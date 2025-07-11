package cn.dtransfer.controller.mobile;


import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.system.domain.User;
import cn.dtransfer.system.domain.form.LoginForm;
import cn.dtransfer.system.service.IAccessTokenService;
import cn.dtransfer.system.service.ISysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录
 *
 * @author dtransfer
 */
@RestController
@RequestMapping("/auth")
public class LoginAPIController {

    @Autowired
    private IAccessTokenService tokenService;
    @Autowired
    private ISysLoginService sysLoginService;

    @PostMapping("login")
    public R login(@RequestBody LoginForm form) {
        // 用户登录
        User user = sysLoginService.login(form.getUsername(), form.getPassword());
        // 获取登录token
        return R.ok(tokenService.createToken(user));
    }

}
