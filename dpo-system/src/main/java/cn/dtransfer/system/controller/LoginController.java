package cn.dtransfer.system.controller;

import cn.dtransfer.system.domain.form.LoginForm;
import cn.dtransfer.system.log.publish.PublishFactory;
import cn.dtransfer.system.service.ILoginService;
import cn.dtransfer.system.service.IMenuService;
import cn.dtransfer.system.service.IRoleService;
import cn.dtransfer.system.service.IUserService;
import cn.hutool.core.lang.Validator;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.service.CaptchaService;
import com.google.common.collect.Maps;
import cn.dtransfer.common.constant.Constants;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.redis.util.RedisUtils;
import cn.dtransfer.common.utils.StringUtils;
import cn.dtransfer.system.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wf.jwtp.annotation.RequiresPermissions;
import org.wf.jwtp.provider.Token;
import org.wf.jwtp.provider.TokenStore;
import org.wf.jwtp.util.SubjectUtil;
import org.wf.jwtp.util.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 登录认证
 *
 */
@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private RedisUtils redis;
    @Autowired
    private TokenStore  tokenStore;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private ILoginService sysLoginService;
    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private IUserService userService;

    private final static String ACCESS_USERID = Constants.ACCESS_USERID;

    /**
     * 全部数据权限
     */
    public static final String DATA_SCOPE_ALL = "1";


    /**
     * 部门数据权限
     */
    public static final String DATA_SCOPE_DEPT = "3";

    /**
     * 部门及以下数据权限
     */
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

    /**
     * 登入系统
     */
    @PostMapping("slide")
    public R loginSilde(@RequestBody LoginForm form) {
        ResponseModel response = captchaService.verification(form.getCaptchaVO());

        if (response.isSuccess()) {
            User user = null;
            // 用户名登录
            if (StringUtils.isNotEmpty(form.getUsername()) && StringUtils.isNotEmpty(form.getPassword())) {
                user = sysLoginService.login(form.getUsername(), form.getPassword(), User.Type.Admin);
            } else if (StringUtils.isNotEmpty(form.getMobile()) && StringUtils.isNotEmpty(form.getCaptcha()) && Validator.isMobile(form.getMobile())) {
                // 手机号登录
                user = sysLoginService.loginCaptcha(form.getMobile(), form.getCaptcha());
            } else {
                return R.error("用户与手机不能都为空!");
            }

            // 签发token
            Long userId = user.getId();
            Token token = tokenStore.createNewToken(userId.toString(), menuService.selectPermsStrArray(userId), roleService.selectRoleStrArray(userId), TokenUtil.DEFAULT_EXPIRE_REFRESH_TOKEN);
            Map<String, Object> map = Maps.newHashMap();
            map.put("userId", userId);
            map.put("token", token.getAccessToken());
            map.put("expire", TokenUtil.DEFAULT_EXPIRE_REFRESH_TOKEN);

            // 登记在线信息
            PublishFactory.recordUserOnlineInfo(user, token.getAccessToken());
            return R.ok(map);
        }
        return R.error().put("repCode", response.getRepCode());
    }

    /**
     * 登出系统
     */
    @RequiresPermissions("system:auth:logout")
    @PostMapping("logout")
    public R logout(HttpServletRequest request) {
        // 正常可以这样获取
        Token token = SubjectUtil.getToken(request);
        if (null != token) {
            User user = userService.selectUserById(Long.valueOf(token.getUserId()));
            // 移除用户的某个token
            tokenStore.removeTokensByUserId(token.getUserId());
            redis.delete(ACCESS_USERID + ":" + token.getUserId());
            sysLoginService.logout(user.getUsername());
        }
        return R.ok();
    }

}
