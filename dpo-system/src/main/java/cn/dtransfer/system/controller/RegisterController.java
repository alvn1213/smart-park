package cn.dtransfer.system.controller;

import cn.dtransfer.common.constant.Constants;
import cn.dtransfer.common.constant.UserConstants;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.enums.SmsType;
import cn.dtransfer.common.redis.util.RedisUtils;
import cn.dtransfer.common.utils.DateUtils;
import cn.dtransfer.common.utils.RandomUtil;
import cn.dtransfer.common.utils.StringUtils;
import cn.dtransfer.system.domain.Dept;
import cn.dtransfer.system.service.IDeptService;
import cn.dtransfer.system.service.IRoleService;
import cn.dtransfer.system.service.IUserService;
import cn.dtransfer.system.domain.Role;
import cn.dtransfer.system.domain.User;
import cn.dtransfer.system.util.PasswordUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户注册
 *
 */
@RestController
@RequestMapping("/user/register")
public class RegisterController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private RedisUtils   redis;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IDeptService deptService;

    /**
     * 注册提交
     */
    @PostMapping("/submit")
    public R submit(String username, String password, String password2, String captcha, String mobile) {
        User user = new User();
        user.setUsername(username);
        user.setMobile(mobile);
        if (StringUtils.isEmpty(username)) {
            return R.error("用户名不能为空");
        }
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(password2)) {
            return R.error("密码不能为空");
        }
        if (!StringUtils.equals(password, password2)) {
            return R.error("两次密码不一致");
        }
        String key = mobile + SmsType.MEMBER_REGISTER.getName();
        // 检验
        String pCaptcha = redis.get(key);
        if (StringUtils.isNotEmpty(mobile) && !StringUtils.equals(captcha, pCaptcha)) {
            return R.error("验证码错误");
        }
        if (StringUtils.isEmpty(mobile)) {
            return R.error("手机号码不能为空!");
        }

        if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkMobileUnique(user))) {
            return R.error("新增用户'" + user.getUsername() + "'失败，手机号码已存在");
        }

        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            return R.error(String.format("新密码长度在%s与%s之间", UserConstants.PASSWORD_MIN_LENGTH, UserConstants.PASSWORD_MAX_LENGTH));
        }

        user.setNickname("用户" + RandomUtil.randomInt(6));
        user.setAvatar("/avatar2.jpg");
        user.setPassword(password);

        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkUsernameUnique(user))) {
            return R.error("新增用户'" + user.getUsername() + "'失败，登录账号已存在");
        } else if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkMobileUnique(user))) {
            return R.error("新增用户'" + user.getUsername() + "'失败，手机号码已存在");
        } else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return R.error("新增用户'" + user.getUsername() + "'失败，邮箱账号已存在");
        }

        //默认角色
        Role defaultRole = roleService.findDefault();
        if (StringUtils.isNull(defaultRole)) {
            return R.error("默认角色未设置，请联系管理员!");
        }
        List<Long> roleIds = Lists.newArrayList();
        roleIds.add(defaultRole.getId());
        user.setRoleIds(roleIds);
        // 默认部门
        Dept defaultDept = deptService.findDefault();
        if (StringUtils.isNull(defaultDept)) {
            return R.error("默认部门未设置，请联系管理员!");
        }

        user.setAvatar(Constants.RESOURCE_PREFIX + "/avatar2.jpg");
        user.setDeptId(defaultDept.getId());
        user.setCreateBy(user.getMobile());
        user.setCreateTime(DateUtils.getNowDate());
        user.setSalt(RandomUtil.randomStr(6));
        user.setPassword(PasswordUtils.encryptPassword(user.getUsername(), user.getPassword(), user.getSalt()));
        return toAjax(userService.insertUser(user));
    }

}
