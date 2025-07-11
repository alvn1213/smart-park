package cn.dtransfer.system.service.impl;

import cn.dtransfer.common.constant.Constants;
import cn.dtransfer.common.redis.util.RedisUtils;
import cn.dtransfer.common.utils.ServletUtils;
import cn.dtransfer.common.utils.StringUtils;
import cn.dtransfer.system.domain.vo.CurrentUserVO;
import cn.dtransfer.system.service.ICurrentUserService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wf.jwtp.provider.Token;
import org.wf.jwtp.util.SubjectUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class CurrentUserServiceImpl implements ICurrentUserService {

    private final static String ACCESS_USERID = Constants.ACCESS_USERID;

    @Autowired
    private RedisUtils redis;

    /**
     * 当前用户租户Id
     */
    @Override
    public Long getUserId() {
        CurrentUserVO currentUserVO = getCurrentUser();
        if (currentUserVO != null) {
            return currentUserVO.getUserId();
        }
        return null;
    }

    /**
     * 当前用户租户Id
     */
    @Override
    public Long getTenantId() {
        CurrentUserVO currentUserVO = getCurrentUser();
        if (currentUserVO != null) {
            return currentUserVO.getTenantId();
        }
        return null;
    }

    /**
     * 当前用户部门Id
     */
    @Override
    public Long getParkId() {
        CurrentUserVO currentUserVO = getCurrentUser();
        if (currentUserVO != null) {
            return currentUserVO.getParkId();
        }
        return null;
    }

    /**
     * 当前登录名
     */
    @Override
    public String getLoginName() {
        CurrentUserVO currentUserVO = getCurrentUser();
        if (currentUserVO != null) {
            return currentUserVO.getLoginName();
        }
        return null;
    }

    /**
     * 查询在线用户
     *
     * @return 在线用户
     */
    @Override
    public CurrentUserVO getCurrentUser() {
        // 对于排除拦截的接口可以这样获取
        Token token = SubjectUtil.getToken(ServletUtils.getRequest());
        CurrentUserVO currentUserVO = null;
        if (token != null) {
            currentUserVO = redis.get(ACCESS_USERID + ":" + token.getUserId(), CurrentUserVO.class);
        }
        return currentUserVO;
    }

    /**
     * 根据条件查询在线用户列表
     *
     * @param loginName
     * @param ipaddr
     * @return
     */
    @Override
    public List<CurrentUserVO> selectCurrentUserList(String loginName, String ipaddr) {
        List<CurrentUserVO> userOnlineList = Lists.newArrayList();
        Collection<String> keys = redis.keys(ACCESS_USERID + "*");
        for (String key : keys) {
            CurrentUserVO userOnlineVo = redis.get(key, CurrentUserVO.class);
            // 设置在线用户信息
            if (userOnlineVo != null) {
                if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(loginName)) {
                    if (StringUtils.equals(ipaddr, userOnlineVo.getIpaddr()) && StringUtils.equals(loginName, userOnlineVo.getLoginName())) {
                        userOnlineList.add(userOnlineVo);
                    }
                } else if (StringUtils.isNotEmpty(ipaddr)) {
                    if (StringUtils.equals(ipaddr, userOnlineVo.getIpaddr())) {
                        userOnlineList.add(userOnlineVo);
                    }
                } else if (StringUtils.isNotEmpty(loginName)) {
                    if (StringUtils.equals(loginName, userOnlineVo.getLoginName())) {
                        userOnlineList.add(userOnlineVo);
                    }
                } else {
                    userOnlineList.add(userOnlineVo);
                }
                Collections.reverse(userOnlineList);
                userOnlineList.removeAll(Collections.singleton(null));
            }
        }
        return userOnlineList;
    }


}
