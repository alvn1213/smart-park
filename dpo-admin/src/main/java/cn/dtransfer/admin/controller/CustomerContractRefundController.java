package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.ICustomerContractRefundService;
import cn.dtransfer.admin.domain.CustomerContractRefund;
import cn.dtransfer.common.constant.Constants;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 客户合同退租 提供者
 *
 * @author dtransfer
 * @date 2024-03-31
 */
@RestController
@RequestMapping("/admin/customerContractRefund")
public class CustomerContractRefundController extends BaseController {

    @Autowired
    private ICustomerContractRefundService customerContractRefundService;

    /**
     * 查询退租
     */
    @RequiresPermissions("admin:refund:edit")
    @GetMapping("/get")
    public CustomerContractRefund get(Long id) {
        CustomerContractRefund customerContractRefund = customerContractRefundService.selectCustomerContractRefundById(id);
        return customerContractRefund;
    }

    /**
     * 查询客户合同退租列表
     */
    @RequiresPermissions("admin:refund:list")
    @GetMapping("list")
    public R list(CustomerContractRefund customerContractRefund) {
        startPage();
        return result(customerContractRefundService.selectCustomerContractRefundList(customerContractRefund));
    }

    /**
     * 新增保存客户合同退租
     */
    @RequiresPermissions("admin:refund:add")
    @PostMapping("save")
    public R addSave(@RequestBody CustomerContractRefund customerContractRefund) {
        customerContractRefund.setStatus(false);
        customerContractRefund.setSn(RandomUtil.generate_sn(Constants.REFUND_PREFIX));
        return toAjax(customerContractRefundService.insertCustomerContractRefund(customerContractRefund));
    }

    /**
     * 确定退租（改状态）
     * ids合同id， id退租id
     */
    @RequiresPermissions("admin:refund:edit")
    @PostMapping("determine")
    public R determine(String ids, Long id) {
        return toAjax(customerContractRefundService.voided(ids, id));
    }

    /**
     * 修改保存客户合同退租
     */
    @RequiresPermissions("admin:refund:edit")
    @PostMapping("update")
    public R editSave(@RequestBody CustomerContractRefund customerContractRefund) {
        return toAjax(customerContractRefundService.updateById(customerContractRefund));
    }

    /**
     * 删除客户合同退租
     */
    @RequiresPermissions("admin:refund:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(customerContractRefundService.deleteCustomerContractRefundByIds(ids));
    }

}
