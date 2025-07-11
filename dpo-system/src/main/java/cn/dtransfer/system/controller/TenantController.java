package cn.dtransfer.system.controller;

import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.RandomUtil;
import cn.dtransfer.system.service.ITenantService;
import cn.dtransfer.system.service.IUserService;
import cn.dtransfer.system.domain.Tenant;
import cn.dtransfer.system.domain.User;
import cn.dtransfer.system.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.Logical;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 租户管理 提供者
 *
 * @author dtransfer
 * @date 2024-04-02
 */
@RestController
@RequestMapping("/system/tenant")
public class TenantController extends BaseController {

    @Autowired
    private ITenantService tenantService;

    @Autowired
    private IUserService userService;

    /**
     * 查询租户管理
     */
    @RequiresPermissions("system:tenant:view")
    @GetMapping("get/{id}")
    public Tenant get(@PathVariable("id") Long id) {
        return tenantService.selectTenantById(id);
    }

    /**
     * 查询租户管理列表
     */
    @RequiresPermissions(value = {"system:tenant:list", "system:user:list"}, logical = Logical.OR)
    @GetMapping("list")
    public R list(Tenant tenant) {
        startPage();
        return result(tenantService.selectTenantList(tenant));
    }


    /**
     * 新增保存租户管理
     */
    @RequiresPermissions("system:tenant:add")
    @PostMapping("save")
    public R addSave(@RequestBody Tenant tenant) {
        return toAjax(tenantService.insertTenant(tenant));
    }

    /**
     * 修改保存租户管理
     */
    @RequiresPermissions("system:tenant:edit")
    @PostMapping("update")
    public R editSave(@RequestBody Tenant tenant) {
        return toAjax(tenantService.updateTenant(tenant));
    }

    /**
     * 删除租户管理
     */
    @RequiresPermissions("system:tenant:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(tenantService.deleteTenantByIds(ids));
    }


    /**
     * 重置租户用户默认密码
     *
     * @param userId
     * @return
     */
    @RequiresPermissions("system:tenant:edit")
    @PostMapping("/resetPwd")
    public R resetPwdSave(Long userId) {
        User user = userService.selectUserById(userId);
        user.setSalt(RandomUtil.randomStr(6));
        user.setPassword(PasswordUtils.encryptPassword(user.getUsername(), "888888", user.getSalt()));
        return toAjax(userService.resetUserPwd(user));
    }

}
