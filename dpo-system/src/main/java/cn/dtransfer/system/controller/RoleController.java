package cn.dtransfer.system.controller;

import cn.dtransfer.common.constant.UserConstants;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.system.log.enums.BusinessType;
import cn.dtransfer.system.service.ICurrentUserService;
import cn.dtransfer.system.service.IRoleService;
import cn.dtransfer.system.domain.Role;
import cn.dtransfer.system.log.annotation.OperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.Logical;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 角色 提供者
 *
 */
@RestController
@RequestMapping("system/role")
public class RoleController extends BaseController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private ICurrentUserService currentUserService;

    /**
     * 查询所有角色
     */
    @RequiresPermissions(value = {"system:role:list", "system:user:list"}, logical = Logical.OR)
    @GetMapping("all")
    public R all() {
        return R.ok().put("rows", roleService.selectRoleAll());
    }

    /**
     * 查询角色列表
     */
    @RequiresPermissions("system:role:list")
    @GetMapping("list")
    public R list(Role role) {
        startPage();
        return result(roleService.selectRoleList(role));
    }

    /**
     * 新增保存角色
     */
    @RequiresPermissions("system:role:add")
    @PostMapping("save")
    @OperLog(title = "角色管理", businessType = BusinessType.INSERT)
    public R addSave(@RequestBody Role role) {
        if (UserConstants.ROLE_NAME_NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return R.error("新增角色'" + role.getRoleName() + "'失败，角色已存在");
        } else if (UserConstants.ROLE_KEY_NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return R.error("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setCreateBy(getLoginName());
        return toAjax(roleService.insertRole(role));
    }

    /**
     * 修改保存角色
     */
    @RequiresPermissions("system:role:edit")
    @OperLog(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("update")
    public R editSave(@RequestBody Role role) {
        if (UserConstants.ROLE_NAME_NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return R.error("修改角色'" + role.getRoleName() + "'失败，角色已存在");
        } else if (UserConstants.ROLE_KEY_NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return R.error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setTenantId(currentUserService.getTenantId());
        role.setUpdateBy(getLoginName());
        return toAjax(roleService.updateRole(role));
    }

    /**
     * 删除角色
     */
    @RequiresPermissions("system:role:remove")
    @OperLog(title = "角色管理", businessType = BusinessType.DELETE)
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(roleService.deleteRoleByIds(ids));
    }

    /**
     * 修改保存角色
     */
    @OperLog(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("status")
    public R status(@RequestBody Role role) {
        return toAjax(roleService.changeStatus(role));
    }

    /**
     * 保存角色分配数据权限
     */
    @RequiresPermissions("system:role:edit")
    @OperLog(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("/authDataScope")
    public R authDataScopeSave(@RequestBody Role role) {
        role.setUpdateBy(getLoginName());
        if (roleService.authDataScope(role) > 0) {
            return R.ok();
        }
        return R.error();
    }


}
