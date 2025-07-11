package cn.dtransfer.system.controller;

import cn.dtransfer.common.annotation.LoginUser;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.system.domain.Menu;
import cn.dtransfer.system.domain.vo.MenuVO;
import cn.dtransfer.system.log.annotation.OperLog;
import cn.dtransfer.system.log.enums.BusinessType;
import cn.dtransfer.system.service.IMenuService;
import cn.dtransfer.system.domain.User;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.List;

/**
 * 菜单权限
 *
 */
@RestController
@RequestMapping("system/menu")
public class MenuController extends BaseController {

    @Autowired
    private IMenuService menuService;

    /**
     * 根据角色编号查询菜单编号（用于勾选）
     *
     */
    @RequiresPermissions("system:menu:list")
    @GetMapping("role/{roleId}")
    public List<MenuVO> role(@PathVariable("roleId") Long roleId) {
        if (null == roleId || roleId <= 0) {
            return null;
        }
        List<Menu>   menuList   = menuService.selectMenuIdsByRoleId(roleId);
        List<MenuVO> menuVOList = Lists.newArrayList();
        for (Menu menu : menuList) {
            MenuVO menuVO = new MenuVO();
            BeanUtils.copyProperties(menu, menuVO);
            menuVOList.add(menuVO);
        }
        return menuVOList;
    }

    /**
     * 查询菜单权限
     */
    @RequiresPermissions("system:menu:user")
    @GetMapping("user")
    public List<MenuVO> user(@LoginUser User user) {
        List<Menu> menuList = menuService.selectMenusByUser(user);
        List<MenuVO> menuVOList = Lists.newArrayList();
        for (Menu menu : menuList) {
            MenuVO menuVO = new MenuVO();
            BeanUtils.copyProperties(menu, menuVO);
            menuVOList.add(menuVO);
        }
        return menuVOList;
    }

    /**
     * 查询所有菜单权限列表
     */
    @RequiresPermissions("system:menu:list")
    @GetMapping("all")
    public R all(Menu menu) {
        return result(menuService.selectMenuList(menu));
    }

    /**
     * 查询菜单权限列表
     */
    @RequiresPermissions("system:menu:list")
    @GetMapping("list")
    public R list(Menu menu) {
        return result(menuService.selectMenuList(menu));
    }

    /**
     * 新增保存菜单权限
     */
    @RequiresPermissions("system:menu:add")
    @PostMapping("save")
    @OperLog(title = "菜单管理", businessType = BusinessType.INSERT)
    public R addSave(@RequestBody Menu menu) {
        menu.setCreateBy(getLoginName());
        return toAjax(menuService.insertMenu(menu));
    }

    /**
     * 修改保存菜单权限
     */
    @RequiresPermissions("system:menu:edit")
    @OperLog(title = "菜单管理", businessType = BusinessType.UPDATE)
    @PostMapping("update")
    public R editSave(@RequestBody Menu menu) {
        menu.setUpdateBy(getLoginName());
        return toAjax(menuService.updateMenu(menu));
    }

    /**
     * 删除菜单权限
     */
    @RequiresPermissions("system:menu:remove")
    @OperLog(title = "菜单管理", businessType = BusinessType.DELETE)
    @PostMapping("remove/{menuId}")
    public R remove(@PathVariable("menuId") Long menuId) {
        return toAjax(menuService.deleteMenuById(menuId));
    }
}
