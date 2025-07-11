package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.IServiceSupplierService;
import cn.dtransfer.admin.domain.ServiceSupplier;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 供应商管理 提供者
 *
 * @author dtransfer
 * @date 2024-03-26
 */
@RestController
@RequestMapping("/admin/supplier")
public class ServiceSupplierController extends BaseController {

    @Autowired
    private IServiceSupplierService serviceSupplierService;

    /**
     * 查询供应商管理
     */
    @RequiresPermissions("admin:supplier:edit")
    @GetMapping("get/{id}")
    public ServiceSupplier get(@PathVariable("id") Long id) {
        return serviceSupplierService.selectServiceSupplierById(id);
    }

    /**
     * 查询供应商管理列表
     */
    @RequiresPermissions("admin:supplier:list")
    @GetMapping("list")
    public R list(ServiceSupplier serviceSupplier) {
        startPage();
        return result(serviceSupplierService.selectServiceSupplierList(serviceSupplier));
    }

    /**
     * 查询供应商管理列表
     */
    @RequiresPermissions("admin:supplier:list")
    @GetMapping("allList")
    public R allList(ServiceSupplier serviceSupplier) {
        return R.data(serviceSupplierService.selectServiceSupplierList(serviceSupplier));
    }

    /**
     * 新增保存供应商管理
     */
    @RequiresPermissions("admin:supplier:add")
    @PostMapping("save")
    public R addSave(@RequestBody ServiceSupplier serviceSupplier) {
        ValidatorUtils.validateEntity(serviceSupplier);
        return toAjax(serviceSupplierService.insertServiceSupplier(serviceSupplier));
    }

    /**
     * 修改保存供应商管理
     */
    @RequiresPermissions("admin:supplier:edit")
    @PostMapping("update")
    public R editSave(@RequestBody ServiceSupplier serviceSupplier) {
        ValidatorUtils.validateEntity(serviceSupplier);
        return toAjax(serviceSupplierService.updateServiceSupplier(serviceSupplier));
    }

    /**
     * 删除供应商管理
     */
    @RequiresPermissions("admin:supplier:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(serviceSupplierService.deleteServiceSupplierByIds(ids));
    }

}
