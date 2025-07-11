package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.IServiceMenuService;
import cn.dtransfer.admin.domain.ServiceMenu;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.DateUtils;
import cn.dtransfer.common.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 菜单管理 提供者
 *
 * @author dtransfer
 * @date 2024-03-25
 */
@RestController
@RequestMapping("/admin/menu")
public class ServiceMenuController extends BaseController {

    @Autowired
    private IServiceMenuService serviceMenuService;

    /**
     * 查询菜单管理
     */
    @RequiresPermissions("admin:ServiceMenu:edit")
    @GetMapping("get/{id}")
    public ServiceMenu get(@PathVariable("id") Long id) {
        return serviceMenuService.selectServiceMenuById(id);
    }

    /**
     * 查询菜单管理列表
     */
    @RequiresPermissions("admin:ServiceMenu:list")
    @GetMapping("list")
    public R list(ServiceMenu serviceMenu) {
        startPage();
        return result(serviceMenuService.selectServiceMenuList(serviceMenu));
    }


    /**
     * 新增保存菜单管理
     */
    @RequiresPermissions("admin:ServiceMenu:add")
    @PostMapping("save")
    public R addSave(@RequestBody ServiceMenu serviceMenu) {
        ValidatorUtils.validateEntity(serviceMenu);
        return toAjax(serviceMenuService.insertServiceMenu(serviceMenu));
    }

    /**
     * 修改保存菜单管理
     */
    @RequiresPermissions("admin:ServiceMenu:edit")
    @PostMapping("update")
    public R editSave(@RequestBody ServiceMenu serviceMenu) {
        return toAjax(serviceMenuService.updateServiceMenu(serviceMenu));
    }

    /**
     * 删除菜单管理
     */
    @RequiresPermissions("admin:ServiceMenu:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(serviceMenuService.deleteServiceMenuByIds(ids));
    }

    /**
     * 上架、下架
     */
    @RequiresPermissions("admin:ServiceMenu:edit")
    @PostMapping("changeMarketable")
    public R changeMarketable(@RequestBody ServiceMenu serviceMenu) {
        ServiceMenu newServiceMenu = serviceMenuService.selectServiceMenuById(serviceMenu.getId());
        if (newServiceMenu == null) {
            return R.error("菜单不存在!");
        }
        serviceMenu.setMarketableTime(DateUtils.getNowDate());
        return toAjax(serviceMenuService.updateServiceMenu(serviceMenu));
    }
}
