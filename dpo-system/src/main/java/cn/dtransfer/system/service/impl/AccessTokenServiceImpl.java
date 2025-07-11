package cn.dtransfer.system.service.impl;

import cn.dtransfer.system.domain.vo.CurrentUserVO;
import cn.dtransfer.system.log.event.UserOnlineEvent;
import cn.dtransfer.system.service.IAccessTokenService;
import com.google.common.collect.Maps;
import cn.dtransfer.common.constant.Constants;
import cn.dtransfer.common.redis.annotation.RedisEvict;
import cn.dtransfer.common.redis.util.RedisUtils;
import cn.dtransfer.common.utils.AddressUtils;
import cn.dtransfer.common.utils.DateUtils;
import cn.dtransfer.common.utils.IpUtils;
import cn.dtransfer.common.utils.ServletUtils;
import cn.dtransfer.common.utils.spring.SpringContextHolder;
import cn.dtransfer.system.domain.User;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wf.jwtp.provider.Token;
import org.wf.jwtp.provider.TokenStore;
import org.wf.jwtp.util.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Token
 *
 * @author dtransfer
 */
@Service("accessTokenService")
public class AccessTokenServiceImpl implements IAccessTokenService {

    @Autowired
    private RedisUtils redis;

    @Autowired
    private TokenStore tokenStore;

    /**
     * 12小时后过期
     */
    private final static long EXPIRE = 30 * 24 * 60 * 60 * 30;

    private final static String ACCESS_TOKEN = Constants.ACCESS_TOKEN;

    private final static String ACCESS_USERID = Constants.ACCESS_USERID;

    @Override
    public User queryByToken(String token) {
        return redis.get(ACCESS_TOKEN + token, User.class);
    }

    @Override
    @RedisEvict(key = "user_perms", fieldKey = "#user.id")
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createToken(User user) {

        // 签发token
        Long userId = user.getId();
        Token token = tokenStore.createNewToken(userId.toString(), new String[]{"member:center:view"},  new String[]{"appUser"}, TokenUtil.DEFAULT_EXPIRE_REFRESH_TOKEN);
        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", userId);
        map.put("token", token.getAccessToken());
        map.put("expire", RedisUtils.DEFAULT_EXPIRE);

        // 记录在线用户
        user = recordUserOnlineInfo(user, token.toString());

        redis.set(ACCESS_TOKEN + token, user, RedisUtils.DEFAULT_EXPIRE);
        redis.set(ACCESS_USERID + user.getId(), token, RedisUtils.DEFAULT_EXPIRE);
        return map;
    }

    @Override
    public void expireToken(long userId) {
        String token = redis.get(ACCESS_USERID + userId);
        if (StringUtils.isNotBlank(token)) {
            redis.delete(ACCESS_USERID + userId);
            redis.delete(ACCESS_TOKEN + token);
        }
    }

    /**
     * 记录登录信息
     *
     * @param user
     * @param token
     */
    private User recordUserOnlineInfo(final User user, final String token) {
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
        userOnline.setLoginName(user.getUsername());
        userOnline.setLoginTime(DateUtils.getNowDate());
        userOnline.setOs(os);
        userOnline.setIpaddr(ip);
        userOnline.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        userOnline.setBrowser(browser);
        user.setUserOnline(userOnline);
        SpringContextHolder.publishEvent(new UserOnlineEvent(userOnline));
        return user;
    }

}
