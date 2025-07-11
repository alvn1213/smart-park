package cn.dtransfer.system.log.publish;

import cn.dtransfer.common.constant.Constants;
import cn.dtransfer.common.utils.AddressUtils;
import cn.dtransfer.common.utils.DateUtils;
import cn.dtransfer.common.utils.IpUtils;
import cn.dtransfer.common.utils.ServletUtils;
import cn.dtransfer.common.utils.spring.SpringContextHolder;
import cn.dtransfer.system.domain.vo.CurrentUserVO;
import cn.dtransfer.system.log.event.LoginInfoEvent;
import cn.dtransfer.system.log.event.UserOnlineEvent;
import cn.dtransfer.system.domain.LoginInfo;
import cn.dtransfer.system.domain.User;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;

/**
 * 发布工厂
 *
 */
public class PublishFactory {


    /**
     * 记录登陆信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     */
    public static void recordLoginInfo(final String username, final String status, final String message, final Object... args) {
        HttpServletRequest request = ServletUtils.getRequest();
        final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr(request);
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 封装对象
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setLoginName(username);
        loginInfo.setIpaddr(ip);
        loginInfo.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        loginInfo.setBrowser(browser);
        loginInfo.setOs(os);
        loginInfo.setMsg(message);
        // 日志状态
        if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status)) {
            loginInfo.setStatus(Constants.SUCCESS);
        } else if (Constants.LOGIN_FAIL.equals(status)) {
            loginInfo.setStatus(Constants.FAIL);
        }
        // 发布事件
        SpringContextHolder.publishEvent(new LoginInfoEvent(loginInfo));
    }


    /**
     * 记录登录信息
     *
     * @param user
     * @param token
     */
    public static void recordUserOnlineInfo(final User user, final String token) {
        HttpServletRequest request = ServletUtils.getRequest();

        final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr(request);
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 封装对象
        CurrentUserVO userOnline = new CurrentUserVO();
        userOnline.setUserId(user.getId());

        userOnline.setTokenId(token);
        userOnline.setTenantId(user.getTenantId());
        userOnline.setLoginName(user.getUsername());
        userOnline.setParkId(user.getParkId());
        userOnline.setLoginTime(DateUtils.getNowDate());
        userOnline.setOs(os);
        userOnline.setIpaddr(ip);
        userOnline.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        userOnline.setBrowser(browser);
        if (user.getDept() != null) {
            userOnline.setDeptId(user.getDept().getId());
            userOnline.setDeptName(user.getDept().getDeptName());
        }
        // 发布事件
        SpringContextHolder.publishEvent(new UserOnlineEvent(userOnline));
    }

}
