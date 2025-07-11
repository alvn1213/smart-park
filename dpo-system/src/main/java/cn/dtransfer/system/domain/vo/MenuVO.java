package cn.dtransfer.system.domain.vo;

import lombok.Data;

@Data
public class MenuVO {

    /**
     * 菜单Id
     */
    private Long id;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单URL
     */
    private String menuKey;

    /**
     * 图标
     */
    private String icon;

    /**
     * 菜单状态:0显示,1隐藏
     */
    private String visible;

    /**
     * 链接地址
     */
    private String path;

    /**
     * 组件
     */
    private String component;

    /**
     * 重定向地址
     */
    private String redirect;

    /**
     * 打开方式 (_blank新窗口)
     */
    private String target;

    /**
     * 隐藏子菜单
     */
    private Boolean hiddenChildren;

    /**
     * 隐藏 PageHeader 组件中的页面带的 面包屑和页面标题栏
     */
    private Boolean hiddenHeader;

}
