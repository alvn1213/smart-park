package cn.dtransfer.controller.mobile;

import cn.dtransfer.service.IServiceManageService;
import cn.dtransfer.service.IServiceOrderService;
import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Maps;

import cn.dtransfer.admin.domain.Park;
import cn.dtransfer.admin.domain.ServiceManage;
import cn.dtransfer.admin.domain.ServiceOrder;
import cn.dtransfer.admin.service.IParkService;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.enums.SmsType;
import cn.dtransfer.common.utils.RandomUtil;
import cn.dtransfer.common.utils.StringUtils;
import cn.dtransfer.system.domain.User;
import cn.dtransfer.system.service.ISmsService;
import cn.dtransfer.system.service.IUserService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.List;
import java.util.Map;

/**
 * 服务管理 APP端
 *
 * @author dtransfer
 * @date 2024-11-09
 */
@RestController
@RequestMapping("/manage")
public class ServiceManagerAPIController extends BaseController {


    @Autowired
    private IServiceManageService serviceManageService;

    @Autowired
    private IServiceOrderService serviceOrderService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ISmsService smsService;

    @Autowired
    private IParkService parkService;



    /**
     * 查询服务管理列表
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("list")
    public R list(ServiceManage serviceManage) {
        if (serviceManage.getParkId() == null) {
            return R.error("请选择园区！!");
        }
        serviceManage.setIsMarketable(true);
        List<ServiceManage> serviceManages = serviceManageService.selectServiceManageList(serviceManage);
        List<Map> serviceManageMaps = getServiceMangeMaps(serviceManages);
        return R.data(serviceManageMaps);
    }

    /**
     * 企业服务详情
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("detail")
    public R detail(Long id) {
        Map<String, Object> serviceManageMap = Maps.newHashMap();
        if (id == null) {
            return R.error("id 不能为空！");
        }
        ServiceManage serviceManage = serviceManageService.selectServiceManageById(id);
        if (serviceManage != null) {
            serviceManageMap.put("id", serviceManage.getId());
            serviceManageMap.put("serviceName", serviceManage.getServiceName());
            serviceManageMap.put("serviceDesc", serviceManage.getServiceDesc());
            serviceManageMap.put("serviceContent", serviceManage.getServiceContent());
            serviceManageMap.put("price", serviceManage.getPrice());
            serviceManageMap.put("priceUnit", serviceManage.getPriceUnit());
            serviceManageMap.put("supplierId", serviceManage.getSupplierId());
            serviceManageMap.put("supplierImg", serviceManage.getServiceSupplier().getSupplierImg());
            serviceManageMap.put("supplierName", serviceManage.getServiceSupplier().getSupplierName());
            serviceManageMap.put("supplierAddress", serviceManage.getServiceSupplier().getSupplierAddress());
        }else {
            return R.error("企业服务不存在！");
        }
        return R.data(serviceManageMap);
    }


    /**
     * 发送手机短信
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("/send_mobile")
    public R sendMobile(String mobile) {
        String content = RandomUtil.randomInt(4);
        String result = smsService.sendApplyServiceSms(mobile, content);
        return StringUtils.equals("OK", result) ? R.ok("发送成功！测试：" + content) : R.error("发送失败, 原因：" + result);
    }


    /**
     * 服务申请
     */
    @RequiresPermissions("member:center:view")
    @PostMapping("apply")
    public R apply(Long memberId, Long supplierId, Long serviceId, String code, Long parkId, String mobile) {
        if (parkId == null) {
            return R.error("请选择园区！！");
        }
        Park park = parkService.selectParkById(parkId);
        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setMemberId(memberId);
        User user =  userService.selectUserById(memberId);
        if (user == null) {
            return R.error("用户不存在！");
        }
        serviceOrder.setSupplierId(supplierId);
        serviceOrder.setServiceId(serviceId);
        serviceOrder.setParkId(park.getId());
        serviceOrder.setTenantId(park.getTenantId());
        serviceOrder.setCreateBy(user.getUsername());
        if (!smsService.verify(mobile, code, SmsType.APPLY_SERVICE)) {
            return R.error("验证码错误或已过期！");
        }
        serviceOrder.setMobile(mobile);
        serviceOrder.setName(user.getUsername());
        return toAjax(serviceOrderService.insertServiceOrder(serviceOrder));
    }


    /**
     * 我的服务列表
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("myOrderList")
    public R myOrderList(ServiceOrder serviceOrder) {
        serviceOrder.setMemberId(getCurrentUserId());
        List<ServiceOrder> serviceOrders = serviceOrderService.selectMyServiceOrderAppList(serviceOrder);
        List<Map> serviceManageMaps = getServiceOrderMaps(serviceOrders);
        return R.data(serviceManageMaps);
    }

    /**
     * 封闭服务类字段数据
     * @param serviceManages
     * @return
     */
    private List<Map> getServiceMangeMaps(List<ServiceManage> serviceManages) {
        List<Map> serviceManageMaps = Lists.newArrayList();
        if (CollectionUtil.isNotEmpty(serviceManages)) {
            for (ServiceManage item : serviceManages) {
                Map<String, Object> serviceManageMap = Maps.newHashMap();
                serviceManageMap.put("id", item.getId());
                serviceManageMap.put("serviceName", item.getServiceName());
                serviceManageMap.put("serviceDesc", item.getServiceDesc());
                serviceManageMap.put("supplierImg", item.getServiceSupplier() != null ? item.getServiceSupplier().getSupplierImg() : "");
                serviceManageMap.put("supplierName", item.getServiceSupplier() != null ? item.getServiceSupplier().getSupplierName() : "");
                serviceManageMap.put("price", item.getPrice());
                serviceManageMap.put("priceUnit", item.getPriceUnit());
                serviceManageMaps.add(serviceManageMap);
            }
        }
        return serviceManageMaps;
    }
    /**
     * 封闭服务订单类字段数据
     * @param serviceOrders
     * @return
     */
    private List<Map> getServiceOrderMaps(List<ServiceOrder> serviceOrders) {
        List<Map> serviceOrderMaps = Lists.newArrayList();
        if (CollectionUtil.isNotEmpty(serviceOrders)) {
            for (ServiceOrder item : serviceOrders) {
                Map<String, Object> serviceOrderMap = Maps.newHashMap();
                serviceOrderMap.put("id", item.getId());
                serviceOrderMap.put("serviceId", item.getServiceId());
                serviceOrderMap.put("serviceName", item.getServiceManage().getServiceName());
                serviceOrderMap.put("serviceDesc", item.getServiceManage().getServiceDesc());
                serviceOrderMap.put("supplierImg", item.getServiceSupplier().getSupplierImg());
                serviceOrderMap.put("supplierName", item.getServiceSupplier().getSupplierName());
                serviceOrderMap.put("price", item.getServiceManage().getPrice());
                serviceOrderMap.put("priceUnit", item.getServiceManage().getPriceUnit());
                serviceOrderMap.put("createTime", item.getCreateTime());
                serviceOrderMaps.add(serviceOrderMap);
            }
        }
        return serviceOrderMaps;
    }


}
