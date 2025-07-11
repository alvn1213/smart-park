package cn.dtransfer.admin.controller;


import cn.dtransfer.admin.service.ICustomerContractRoomService;
import cn.dtransfer.admin.service.ICustomerContractService;
import cn.dtransfer.admin.service.IRoomService;
import cn.dtransfer.admin.domain.CustomerContract;
import cn.dtransfer.admin.domain.CustomerContractRoom;
import cn.dtransfer.admin.domain.Room;
import cn.dtransfer.common.constant.Constants;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.core.text.Convert;
import cn.dtransfer.common.utils.RandomUtil;
import cn.dtransfer.common.utils.StringUtils;
import cn.dtransfer.common.utils.ValidatorUtils;
import cn.dtransfer.system.service.ICurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.List;


/**
 * 客户合同 提供者
 *
 * @author dtransfer
 * @date 2024-11-17
 */
@RestController
@RequestMapping("/admin/contract")
public class CustomerContractController extends BaseController {

    @Autowired
    private ICustomerContractService customerContractService;

    @Autowired
    private ICustomerContractRoomService customerContractRoomService;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private ICurrentUserService currentUserService;


    /**
     * 查询客户合同
     */
    @RequiresPermissions("admin:contract:view")
    @GetMapping("get")
    public CustomerContract get(Long id) {
        CustomerContract customerContract = customerContractService.selectCustomerContractById(id);
        return customerContract;
    }

    /**
     * 查询客户合同列表
     */
    @RequiresPermissions("admin:contract:list")
    @PostMapping("list")
    public R list(@RequestBody CustomerContract customerContract) {
        startPage();
        customerContract.setDeleteFlag(0);
        customerContract.setType(CustomerContract.Type.OFFICIAL);
        return result(customerContractService.selectCustomerContractList(customerContract));
    }

    /**
     * 查询客户合同列表(新建账单的搜索栏)
     */
    @RequiresPermissions("admin:contract:list")
    @GetMapping("searchList")
    public R searchList(String name) {
        if (StringUtils.isEmpty(name)) {
            return R.error("无相关合同!");
        }
        CustomerContract searchModel = new CustomerContract();
        searchModel.setName(name);
        searchModel.setStatus(CustomerContract.Status.APPROVED);
        return R.data(customerContractService.selectCustomerContractList(searchModel));
    }


    /**
     * 新增保存客户合同
     */
    @RequiresPermissions("admin:contract:add")
    @PostMapping("save")
    public R addSave(@RequestBody CustomerContract customerContract) {
        ValidatorUtils.validateEntity(customerContract);
        if (customerContractService.exists("name", customerContract.getName())) {
            return R.error("合同名称重复!");
        }
        customerContract.setType(CustomerContract.Type.OFFICIAL);
        customerContract.setStatus(CustomerContract.Status.DRAFT);
        customerContract.setManageSn(RandomUtil.generate_sn(Constants.CONTRACT_PREFIX));
        return toAjax(customerContractService.insertCustomerContract(customerContract));
    }

    /**
     * 修改保存客户合同
     */
    @RequiresPermissions("admin:contract:edit")
    @PostMapping("update")
    public R editSave(@RequestBody CustomerContract customerContract) {
        ValidatorUtils.validateEntity(customerContract);
        if (customerContractService.unique(customerContract.getId(), "name", customerContract.getName())) {
            return R.error("合同名称重复!");
        }
        customerContract.setType(CustomerContract.Type.OFFICIAL);
        customerContract.setUpdateBy(getLoginName());
        return toAjax(customerContractService.updateCustomerContract(customerContract));
    }


    /**
     * 变更合同
     */
    @RequiresPermissions("admin:contract:edit")
    @PostMapping("change")
    public R change(@RequestBody CustomerContract customerContract) {
        ValidatorUtils.validateEntity(customerContract);
        customerContract.setType(CustomerContract.Type.OFFICIAL);
        customerContract.setStatus(CustomerContract.Status.DRAFT);
        customerContract.setManageSn(RandomUtil.generate_sn(Constants.CONTRACT_PREFIX));
        return toAjax(customerContractService.insertCustomerContract(customerContract));
    }


    /**
     * 提交合同
     */
    @RequiresPermissions("admin:contract:edit")
    @PostMapping("commit")
    public R commit(String ids) {
        String[] idArr = Convert.toStrArray(ids);
        for (String id : idArr) {
            CustomerContract customerContract = customerContractService.selectCustomerContractById(Long.valueOf(id));
            List<CustomerContractRoom> customerContractRooms = customerContractRoomService.findByContractId(Long.valueOf(id));
            for (CustomerContractRoom ccr : customerContractRooms) {
                Room room = roomService.selectRoomById(ccr.getRoomId());
                if (Room.Status.YES.equals(room.getStatus())) {
                    return R.error("合同号：" + customerContract.getSn() + "选择的房间已被出租,无法提交!");
                }
            }
        }
        return toAjax(customerContractService.commit(ids));
    }


    /**
     * 审批合同
     */
    @RequiresPermissions("admin:contract:edit")
    @PostMapping("approve")
    public R approve(String ids, CustomerContract.Status status, String remark) {
        return toAjax(customerContractService.approveContract(ids, status, remark));
    }

    /**
     * 作废合同
     */
    @RequiresPermissions("admin:contract:edit")
    @PostMapping("cancel")
    public R cancel(String ids) {
        return toAjax(customerContractService.voided(ids));
    }

    /**
     * 删除客户合同
     */
    @RequiresPermissions("admin:contract:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        if (StringUtils.isEmpty(ids)) {
            return R.error("合同id不能为空！");
        }
        String[] idArr = Convert.toStrArray(ids);
        for (String id : idArr) {
            CustomerContract customerContract = customerContractService.selectCustomerContractById(Long.valueOf(id));
            if (!customerContract.getStatus().equals(CustomerContract.Status.DRAFT)) {
                return R.error("合同号:" + customerContract.getSn() + "已经提交，无法删除!");
            }
        }
        return toAjax(customerContractService.deleteCustomerContractByIds(ids));
    }

}
