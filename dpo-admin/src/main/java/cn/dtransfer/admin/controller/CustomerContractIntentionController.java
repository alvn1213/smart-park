package cn.dtransfer.admin.controller;


import cn.dtransfer.admin.service.ICustomerContractService;
import cn.dtransfer.admin.domain.CustomerContract;
import cn.dtransfer.common.constant.Constants;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;


/**
 * 客户意向信息 提供者
 *
 * @author dtransfer
 * @date 2024-11-17
 */
@RestController
@RequestMapping("/admin/intentionContract")
public class CustomerContractIntentionController extends BaseController {

    @Autowired
    private ICustomerContractService customerContractService;



    /**
     * 查询客户意向
     */
    @RequiresPermissions("admin:intentionContract:edit")
    @GetMapping("get")
    public CustomerContract get(Long id) {
        CustomerContract customerContract =  customerContractService.selectCustomerContractById(id);
        return customerContract;
    }

    /**
     * 查询客户意向列表
     */
    @RequiresPermissions("admin:intentionContract:list")
    @PostMapping("list")
    public R list(@RequestBody CustomerContract customerContract) {
        startPage();
        customerContract.setType(CustomerContract.Type.INTENTION);
        return result(customerContractService.selectCustomerContractList(customerContract));
    }

    /**
     * 新增保存客户意向信息
     */
    @RequiresPermissions("admin:intentionContract:add")
    @PostMapping("save")
    public R addSave(@RequestBody CustomerContract customerContract) {
        if (customerContractService.exists("name", customerContract.getName())) {
            return R.error("意向名称重复!");
        }
        customerContract.setType(CustomerContract.Type.INTENTION);
        customerContract.setStatus(CustomerContract.Status.DRAFT);
        customerContract.setManageSn(RandomUtil.generate_sn(Constants.CONTRACT_PREFIX));
        return toAjax(customerContractService.insertCustomerContract(customerContract));
    }

    /**
     * 修改保存客户意向信息
     */
    @RequiresPermissions("admin:intentionContract:edit")
    @PostMapping("update")
    public R editSave(@RequestBody CustomerContract customerContract) {
        if (customerContractService.unique(customerContract.getId(), "name", customerContract.getName())) {
            return R.error("合同名称重复!");
        }
        customerContract.setType(CustomerContract.Type.INTENTION);
        return toAjax(customerContractService.updateCustomerContract(customerContract));
    }

    /**
     *  修改状态为作废信息
     */
    @RequiresPermissions("admin:intentionContract:edit")
    @PostMapping("updateStatus")
    public R updateStatus(@RequestBody CustomerContract customerContract) {
        customerContract.setStatus(CustomerContract.Status.VOIDED);
        return toAjax(customerContractService.updateCustomerContract(customerContract));
    }

    /**
     * 删除客户意向信息
     */
    @RequiresPermissions("admin:intentionContract:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(customerContractService.deleteCustomerContractByIds(ids));
    }

}
