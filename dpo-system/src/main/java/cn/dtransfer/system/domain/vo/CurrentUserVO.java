package cn.dtransfer.system.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * 在线用户记录对象
 *
 */
@Data
public class CurrentUserVO {

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 会话编号
     */
    private String tokenId;

    /**
     * 租户Id
     */
    private Long tenantId;

    /**
     * 部门Id
     */
    private Long deptId;

    /**
     * 园区Id
     */
    private Long parkId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 用户名称
     */
    private String loginName;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地址
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 是否超级管理员
     */
    private Boolean isAdmin;

}
