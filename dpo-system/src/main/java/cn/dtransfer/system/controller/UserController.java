package cn.dtransfer.system.controller;

import cn.dtransfer.common.annotation.LoginUser;
import cn.dtransfer.common.constant.UserConstants;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.RandomUtil;
import cn.dtransfer.system.domain.vo.UserVO;
import cn.dtransfer.system.log.annotation.OperLog;
import cn.dtransfer.system.log.enums.BusinessType;
import cn.dtransfer.system.service.IMenuService;
import cn.dtransfer.system.service.IUserService;
import cn.dtransfer.system.domain.User;
import cn.dtransfer.system.util.PasswordUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.Logical;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 用户 提供者
 *
 */
@RestController
@RequestMapping("system/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IMenuService menuService;

    /**
     * 查询用户
     */
    @RequiresPermissions("system:user:list")
    @GetMapping("get/{userId}")
    public User get(@PathVariable("userId") Long userId) {
        return userService.selectUserById(userId);
    }

    /**
     * 获取用户信息
     */
    @RequiresPermissions("system:user:info")
    @GetMapping("info")
    public UserVO info(@LoginUser User user) {
        user.setButtons(menuService.selectPermsByUserId(user.getId()));
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    /**
     * 查询用户列表
     */
    @RequiresPermissions(value = {"system:user:list", "system:repair:user"}, logical = Logical.OR)
    @GetMapping("list")
    public R list(User user) {
        startPage();
        return result(userService.selectUserList(user));
    }


    /**
     * 新增保存用户
     */
    @RequiresPermissions("system:user:add")
    @OperLog(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("save")
    public R addSave(@RequestBody User user) {
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkUsernameUnique(user))) {
            return R.error("新增用户'" + user.getUsername() + "'失败，登录账号已存在");
        } else if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkMobileUnique(user))) {
            return R.error("新增用户'" + user.getUsername() + "'失败，手机号码已存在");
        } else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return R.error("新增用户'" + user.getUsername() + "'失败，邮箱账号已存在");
        }
        user.setCreateBy(getLoginName());
        user.setSalt(RandomUtil.randomStr(6));
        user.setPassword(PasswordUtils.encryptPassword(user.getUsername(), user.getPassword(), user.getSalt()));
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改保存用户
     */
    @RequiresPermissions("system:user:edit")
    @OperLog(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("update")
    public R editSave(@RequestBody User user) {
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkUsernameUnique(user))) {
            return R.error("新增用户'" + user.getUsername() + "'失败，登录账号已存在");
        } else if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkMobileUnique(user))) {
            return R.error("修改用户'" + user.getUsername() + "'失败，手机号码已存在");
        } else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return R.error("修改用户'" + user.getUsername() + "'失败，邮箱账号已存在");
        }
        user.setUpdateBy(getLoginName());
        return toAjax(userService.updateUser(user));
    }

    /**
     * 修改用户信息
     *
     */
    @RequiresPermissions("system:user:edit")
    @PostMapping("update/info")
    @OperLog(title = "用户管理", businessType = BusinessType.UPDATE)
    public R updateInfo(@RequestBody User user) {
        return toAjax(userService.updateUserInfo(user));
    }

    /**
     * 重置密码
     *
     * @param user
     * @return
     */
    @RequiresPermissions("system:user:resetPwd")
    @OperLog(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    public R resetPwdSave(@RequestBody User user) {
        user.setSalt(RandomUtil.randomStr(6));
        user.setPassword(PasswordUtils.encryptPassword(user.getUsername(), user.getPassword(), user.getSalt()));
        return toAjax(userService.resetUserPwd(user));
    }

    /**
     * 修改状态
     *
     */
    @RequiresPermissions("system:user:edit")
    @PostMapping("status")
    @OperLog(title = "用户管理", businessType = BusinessType.UPDATE)
    public R status(@RequestBody User user) {
        return toAjax(userService.changeStatus(user));
    }

    /**
     * 删除用户
     *
     * @throws Exception
     */
    @RequiresPermissions("system:user:remove")
    @OperLog(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("remove")
    public R remove(String ids) throws Exception {
        return toAjax(userService.deleteUserByIds(ids));
    }

}
