package cn.dtransfer.admin.controller;


import cn.dtransfer.admin.service.ICustomerAttachmentsService;
import cn.dtransfer.admin.domain.CustomerAttachments;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;


/**
 * 客户附件 提供者
 *
 * @author dtransfer
 * @date 2024-10-27
 */
@RestController
@RequestMapping("/admin/attachments")
public class CustomerAttachmentsController extends BaseController {

    @Autowired
    private ICustomerAttachmentsService customerAttachmentsService;

    /**
     * 查询${tableComment}
     */
    @RequiresPermissions("admin:customer:view")
    @GetMapping("get/{id}")
    public CustomerAttachments get(@PathVariable("id") Long id) {
        return customerAttachmentsService.selectCustomerAttachmentsById(id);

    }

    /**
     * 查询客户附件列表
     */
    @RequiresPermissions("admin:customer:view")
    @GetMapping("list")
    public R list(CustomerAttachments customerAttachments) {
        startPage();
        customerAttachments.setDeleteFlag(0);
        return result(customerAttachmentsService.selectCustomerAttachmentsList(customerAttachments));
    }


    /**
     * 新增保存客户附件
     */
    @RequiresPermissions("admin:customer:add")
    @PostMapping("save")
    public R addSave(@RequestBody CustomerAttachments customerAttachments) {
        return toAjax(customerAttachmentsService.insertCustomerAttachments(customerAttachments));
    }

    /**
     * 修改保存客户附件
     */
    @RequiresPermissions("admin:customer:edit")
    @PostMapping("update")
    public R editSave(@RequestBody CustomerAttachments customerAttachments) {
        return toAjax(customerAttachmentsService.updateCustomerAttachments(customerAttachments));
    }

    /**
     * 删除${tableComment}
     */
    @RequiresPermissions("admin:customer:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(customerAttachmentsService.deleteCustomerAttachmentsByIds(ids));
    }

}
