package cn.dtransfer.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 租户管理对象 sys_tenant
 *
 * @author dtransfer
 * @date 2024-04-02
 */
@Data
@TableName("sys_tenant")
public class Tenant implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /** 租户名称 */
    private String name;

    /**
     * 版本
     */
    @Version
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer version;

    /**
     * 逻辑删除
     */
    @TableField(fill = FieldFill.INSERT)
//    @TableLogic
    private Integer deleteFlag;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    /**
     * 搜索值
     */
    @TableField(exist = false)
    private String searchValue;

    /**
     * 开始日期
     */
    @TableField(exist = false)
    private String beginTime;

    /**
     * 结束日期
     */
    @TableField(exist = false)
    private String endTime;


}
