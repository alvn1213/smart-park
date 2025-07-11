package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.ICustomerService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import cn.dtransfer.admin.domain.Customer;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.ValidatorUtils;
import cn.dtransfer.common.utils.http.HttpUtils;
import cn.dtransfer.system.domain.User;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.List;
import java.util.Map;


/**
 * 客户管理 提供者
 *
 * @author dtransfer
 * @date 2024-10-16
 */
@RestController
@RequestMapping("admin/customer")
public class CustomerController extends BaseController {

    @Autowired
    private ICustomerService customerService;



    /**
     * app的key值
     */
    @Value("${qixin.appKey}")
    private String appKey;

    /**
     * app的密钥值
     */
    @Value("${qixin.secretKey}")
    private String secretKey;

    /**
     * url
     */
    @Value("${qixin.url}")
    private String url;


    /**
     * 调用启信api获取企业照面
     */
    @RequiresPermissions("admin:customer:view")
    @PostMapping("getBasicInfo")
    public R getQiXinBasicInfo(String keyword) {
        String qiXinBasicInfo = HttpUtils.sendGet(url, "appkey=" + appKey + "&secret_key=" + secretKey + "&keyword=" + keyword);
        JSONObject result = (JSONObject) JSON.parse(qiXinBasicInfo);
        // 启信宝的接口返回成功码-200
        String SUCCESS_CODE = "200";
        if (result != null) {
            String status = result.get("status").toString();
            if (SUCCESS_CODE.equals(status)) {
                return R.data(result.get("data").toString());
            } else {
                return R.error(result.get("message").toString());
            }
        }
        return R.error("无当前企业数据！");
    }

    /**
     * 查询${tableComment}
     */
    @RequiresPermissions("admin:customer:view")
    @GetMapping("get/{id}")
    public Customer get(@PathVariable("id") Long id) {
        return customerService.selectCustomerById(id);

    }

    /**
     * 查询客户管理列表
     */
    @RequiresPermissions("admin:customer:list")
    @GetMapping("list")
    public R list(Customer customer) {
        startPage();
        customer.setDeleteFlag(0);
        return result(customerService.selectCustomerList(customer));
    }

    /**
     * 合同查询客户管理列表
     */
    @RequiresPermissions("admin:customer:list")
    @GetMapping("allList")
    public R allList(Customer customer) {
        List<Customer> customerList = customerService.selectCustomerList(customer);
        List<Map> customerMaps = Lists.newArrayList();
        for (Customer item : customerList) {
            Map<String, Object> customerMap = Maps.newHashMap();
            getCustomerMap(customerMap, item);
            customerMaps.add(customerMap);
        }
        return R.data(customerMaps);
    }


    /**
     * 合同根据客户id查询客户部分信息
     */
    @RequiresPermissions("admin:customer:view")
    @GetMapping("findByCustomerId")
    public R findByCustomerId(Long customerId) {
        Map<String, Object> customerMap = Maps.newHashMap();
        Customer customer = customerService.selectCustomerById(customerId);
        getCustomerMap(customerMap, customer);
        return R.data(customerMap);
    }

    /**
     * 合同获取客户的部分信息
     */
    private void getCustomerMap(Map<String, Object> customerMap, Customer customer) {
        customerMap.put("id", customer.getId());
        customerMap.put("name", customer.getName());
        customerMap.put("sector", customer.getSector());
        customerMap.put("creditNo", customer.getCreditNo());
        customerMap.put("mail", customer.getMailAddress());
        customerMap.put("operName", customer.getOperName());
        customerMap.put("phone", customer.getPhone());
        customerMap.put("address", customer.getAddress());
        customerMap.put("channelName", customer.getChannelName());
    }


    /**
     * 新增保存客户管理
     */
    @RequiresPermissions("admin:customer:add")
    @PostMapping("save")
    public R addSave(@RequestBody Customer customer) {
        if (User.isAdmin(getCurrentUserId())) {
            return R.error("不允许超级管理员用户新增");
        }
        ValidatorUtils.validateEntity(customer);
        customer.setCreateBy(getLoginName());
        return toAjax(customerService.insertCustomer(customer));
    }

    /**
     * 修改保存客户管理
     */
    @RequiresPermissions("admin:customer:edit")
    @PostMapping("update")
    public R editSave(@RequestBody Customer customer) {
        if (User.isAdmin(getCurrentUserId())) {
            return R.error("不允许超级管理员用户修改");
        }
        ValidatorUtils.validateEntity(customer);
        customer.setUpdateBy(getLoginName());
        return toAjax(customerService.updateCustomer(customer));
    }

    /**
     * 删除${tableComment}
     */
    @RequiresPermissions("admin:customer:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(customerService.deleteCustomerByIds(ids));
    }

}
