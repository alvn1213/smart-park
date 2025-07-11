package cn.dtransfer.controller.mobile.member;

import com.google.common.collect.Maps;

import cn.dtransfer.admin.domain.Customer;
import cn.dtransfer.admin.service.ICustomerService;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.Map;


/**
 * 企业管理 app端
 *
 * @author dtransfer
 * @date 2024-10-16
 */
@RestController
@RequestMapping("/customer")
public class CustomerAPIController extends BaseController {

    @Autowired
    private ICustomerService customerService;

    /**
     * 查询${tableComment}
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("detail")
    public R detail(Long id) {
        Map<String, Object> customerMap = Maps.newHashMap();
        if (id == null) {
            return R.error("id 不能为空");
        }
        Customer customer = customerService.selectCustomerById(id);
        if (customer == null) {
            return R.error("企业不存在！");
        }
        customerMap.put("id", customer.getId());
        customerMap.put("name", customer.getName());
        // 企业简介
        customerMap.put("remark", customer.getRemark());
        customerMap.put("scope", customer.getScope());
        customerMap.put("headImg", customer.getHeadImg());
        customerMap.put("bannerImages", customer.getBannerImages());
        customerMap.put("address", customer.getAddress());
        customerMap.put("phone", customer.getPhone());
        customerMap.put("mailAddress", customer.getMailAddress());
        customerMap.put("parkName", customer.getPark().getName());
        return R.data(customerMap);

    }

}
