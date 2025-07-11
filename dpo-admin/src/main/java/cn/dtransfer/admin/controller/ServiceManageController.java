package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.IServiceManageService;
import cn.dtransfer.admin.domain.ServiceManage;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.DateUtils;
import cn.dtransfer.common.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 服务管理 提供者
 *
 * @author dtransfer
 * @date 2024-03-26
 */
@RestController
@RequestMapping("/admin/manage")
public class ServiceManageController extends BaseController {

    @Autowired
    private IServiceManageService serviceManageService;

    /**
     * 查询服务管理
     */
    @RequiresPermissions("admin:ServiceManage:edit")
    @GetMapping("get/{id}")
    public ServiceManage get(@PathVariable("id") Long id) {
        return serviceManageService.selectServiceManageById(id);
    }

    /**
     * 查询服务管理列表
     */
    @RequiresPermissions("admin:ServiceManage:list")
    @GetMapping("list")
    public R list(ServiceManage serviceManage) {
        startPage();
        return result(serviceManageService.selectServiceManageList(serviceManage));
    }


    /**
     * 新增保存服务管理
     */
    @RequiresPermissions("admin:ServiceManage:add")
    @PostMapping("save")
    public R addSave(@RequestBody ServiceManage serviceManage) {
        ValidatorUtils.validateEntity(serviceManage);
        return toAjax(serviceManageService.insertServiceManage(serviceManage));
    }

    /**
     * 修改保存服务管理
     */
    @RequiresPermissions("admin:ServiceManage:edit")
    @PostMapping("update")
    public R editSave(@RequestBody ServiceManage serviceManage) {
        return toAjax(serviceManageService.updateServiceManage(serviceManage));
    }

    /**
     * 删除服务管理
     */
    @RequiresPermissions("admin:ServiceManage:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(serviceManageService.deleteServiceManageByIds(ids));
    }

    /**
     * 上架、下架
     */
    @RequiresPermissions("admin:ServiceManage:edit")
    @PostMapping("changeMarketable")
    public R changeMarketable(@RequestBody ServiceManage serviceManage) {
        ServiceManage newServiceManage = serviceManageService.selectServiceManageById(serviceManage.getId());
        if (newServiceManage == null) {
            return R.error("服务不存在!");
        }
        serviceManage.setMarketableTime(DateUtils.getNowDate());
        return toAjax(serviceManageService.updateServiceManage(serviceManage));
    }

}
