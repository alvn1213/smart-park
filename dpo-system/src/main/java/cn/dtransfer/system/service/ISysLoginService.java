package cn.dtransfer.system.service;


import cn.dtransfer.system.domain.User;

/**
 * 登录认证
 *
 * @author dtransfer
 */
public interface ISysLoginService {

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password);

    /**
     * 验证码登录
     *
     * @param username
     * @param code
     * @return
     */
    User loginCaptcha(String username, String code);


    /**
     * 记录登录信息
     * @param user
     */
    void recordLoginInfo(User user);


    /**
     * 登出
     *
     * @param loginName
     */
    void logout(String loginName);


}
