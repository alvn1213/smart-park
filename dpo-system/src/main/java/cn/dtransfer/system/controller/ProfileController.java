package cn.dtransfer.system.controller;

import cn.dtransfer.common.annotation.LoginUser;
import cn.dtransfer.common.config.RuoYiConfig;
import cn.dtransfer.common.constant.UserConstants;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.enums.SmsType;
import cn.dtransfer.common.utils.RandomUtil;
import cn.dtransfer.common.utils.StringUtils;
import cn.dtransfer.common.utils.bean.BeanUtils;
import cn.dtransfer.common.utils.file.FileUploadUtils;
import cn.dtransfer.system.service.ISmsService;
import cn.dtransfer.system.service.IUserService;
import cn.dtransfer.system.config.DfsConfig;
import cn.dtransfer.system.domain.User;
import cn.dtransfer.system.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 个人信息修改
 *
 */
@RestController
@RequestMapping("system/profile")
public class ProfileController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private DfsConfig    dfsConfig;
    @Autowired
    private ISmsService smsService;


    /**
     * 修改头像
     *
     * @param file
     * @param user
     * @return
     */
    @RequiresPermissions("system:profile:avatar")
    @PostMapping("/avatar")
    public R avatar(MultipartFile file, @LoginUser User user) {
        try {
//            // 上传文件路径
//            String filePath = dfsConfig.getPath();
//            // 上传并返回新文件名称
//            String fileName = FileUploadUtils.upload(filePath, file);
            String fileName = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file);
            String url = fileName;
            user.setAvatar(fileName);
            userService.updateUser(user);
            return R.ok().put("fileName", fileName).put("url", url);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 更新当前用户
     */
    @RequiresPermissions("system:profile:update")
    @PostMapping("/update")
    public R update(@RequestBody User user, @LoginUser User currentUser) {
        BeanUtils.copyBeanProp(currentUser, user);
        if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkMobileUnique(currentUser))) {
            return R.error("修改用户'" + currentUser.getUsername() + "'失败，手机号码已存在");
        }
        currentUser.setUpdateBy(getLoginName());
        return toAjax(userService.updateUser(currentUser));
    }

    /**
     * 会员重置密码
     */
    @RequiresPermissions("system:profile:password")
    @GetMapping("/reset")
    public R reset(String password, String newPassword, String rePassword, @LoginUser User user) {
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(rePassword)) {
            return R.error("新旧密码不能为空!");
        }
        if (!StringUtils.equals(newPassword, rePassword)) {
            return R.error("两次密码录入不一致！");
        }
        // 密码如果不在指定范围内 错误
        if (newPassword.length() < UserConstants.PASSWORD_MIN_LENGTH || newPassword.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            return R.error(String.format("新密码长度在%s与%s之间", UserConstants.PASSWORD_MIN_LENGTH, UserConstants.PASSWORD_MAX_LENGTH));
        }

        if (!PasswordUtils.matches(user, password)) {
            return R.error("旧密码不正确！");
        }

        user.setSalt(RandomUtil.randomStr(6));
        user.setPassword(PasswordUtils.encryptPassword(user.getUsername(), newPassword, user.getSalt()));
        return toAjax(userService.resetUserPwd(user));
    }

    /**
     * 绑定手机
     */
    @RequiresPermissions("system:profile:binding")
    @GetMapping("/binding")
    public R binding(String mobile, String captcha, @LoginUser User user) {
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(captcha)) {
            return R.error("手机或验证码不能为空!");
        }
        if (StringUtils.equals(mobile, user.getMobile())) {
            return R.error("将要修改的手机与系统中一样!");
        }
        if (!smsService.verify(mobile, captcha, SmsType.RESET_MOBILE)) {
            return R.error("验证码不合法!");
        }
        user.setMobile(mobile);
        return toAjax(userService.updateUser(user));
    }

}
