package cn.dtransfer.system.service;


import cn.dtransfer.system.domain.User;

import java.util.Map;

/**
 * Token
 *
 * @author dtransfer
 */
public interface IAccessTokenService {

    /**
     * 查询token
     *
     * @param token
     * @return
     */
    User queryByToken(String token);

    /**
     * 创建token
     *
     * @param user
     * @return
     */
    Map<String, Object> createToken(User user);

    /**
     * 删除token
     *
     * @param userId
     */
    void expireToken(long userId);
}
