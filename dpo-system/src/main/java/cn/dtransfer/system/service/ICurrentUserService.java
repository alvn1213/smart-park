package cn.dtransfer.system.service;

import cn.dtransfer.system.domain.vo.CurrentUserVO;

import java.util.List;

public interface ICurrentUserService {

    /**
     * 当前用户Id
     */
    Long getUserId();

    /**
     * 当前用户租户Id
     */
    Long getTenantId();

    /**
     * 当前用户园区Id
     */
    Long getParkId();

    /**
     * 当前登录名
     */
    String getLoginName();


    /**
     * 查询在线用户
     *
     */
    CurrentUserVO getCurrentUser();

    /**
     * 根据条件查询在线用户列表
     */
    List<CurrentUserVO> selectCurrentUserList(String loginName, String ipaddr);
}
