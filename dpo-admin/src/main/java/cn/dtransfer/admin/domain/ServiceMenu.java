package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 菜单管理对象 dpo_service_menu
 *
 * @author dtransfer
 * @date 2024-03-25
 */
@Data
@TableName("dpo_service_menu")
public class ServiceMenu extends BaseEntity<ServiceMenu> {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单简介（可选）
     */
    private String menuDesc;

    /**
     * 上传菜单图标
     */
    private String menuImg;

    /**
     * 菜单级别
     */
    private Long menuLevel;

    /**
     * 父节点
     */
    private Long parentId;


    /**
     * 是否上架
     */
    private Boolean isMarketable;

    /**
     * 上架时间
     */
    private Date marketableTime;

}
