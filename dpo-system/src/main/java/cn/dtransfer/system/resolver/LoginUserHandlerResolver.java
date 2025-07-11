package cn.dtransfer.system.resolver;

import cn.dtransfer.common.annotation.LoginUser;
import cn.dtransfer.system.service.IUserService;
import cn.dtransfer.system.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.wf.jwtp.provider.Token;
import org.wf.jwtp.util.SubjectUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 *
 */
@Configuration
public class LoginUserHandlerResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private IUserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(User.class) && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest nativeWebRequest, WebDataBinderFactory factory) throws Exception {
        // 获取用户
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);

        Token token = SubjectUtil.getToken(request);
        Long userId = Long.valueOf(token.getUserId());
        if (userId == null) {
            return null;
        }
        return userService.selectUserById(userId);
    }
}
