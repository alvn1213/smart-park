package cn.dtransfer.admin.controller;


import cn.dtransfer.admin.service.ICustomerContactsService;
import cn.dtransfer.admin.domain.CustomerContacts;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;


/**
 * 联系人 提供者
 *
 * @author dtransfer
 * @date 2024-10-26
 */
@RestController
@RequestMapping("admin/contacts")
public class CustomerContactsController extends BaseController {

    @Autowired
    private ICustomerContactsService customerContactsService;


    /**
     * 查询${tableComment}
     */
    @RequiresPermissions("admin:customer:view")
    @GetMapping("get/{id}")
    public CustomerContacts get(@PathVariable("id") Long id) {
        return customerContactsService.selectCustomerContactsById(id);

    }

    /**
     * 查询联系人列表
     */
    @RequiresPermissions("admin:customer:list")
    @GetMapping("list")
    public R list(CustomerContacts customerContacts) {
        startPage();
        customerContacts.setDeleteFlag(0);
        return result(customerContactsService.selectCustomerContactsList(customerContacts));
    }


    /**
     * 新增保存联系人
     */
    @RequiresPermissions("admin:customer:add")
    @PostMapping("save")
    public R addSave(@RequestBody CustomerContacts customerContacts) {
        ValidatorUtils.validateEntity(customerContacts);
        return toAjax(customerContactsService.insertCustomerContacts(customerContacts));
    }

    /**
     * 修改保存联系人
     */
    @RequiresPermissions("admin:customer:edit")
    @PostMapping("update")
    public R editSave(@RequestBody CustomerContacts customerContacts) {
        ValidatorUtils.validateEntity(customerContacts);
        return toAjax(customerContactsService.updateCustomerContacts(customerContacts));
    }

    /**
     * 删除${tableComment}
     */
    @RequiresPermissions("admin:customer:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(customerContactsService.deleteCustomerContactsByIds(ids));
    }

}
