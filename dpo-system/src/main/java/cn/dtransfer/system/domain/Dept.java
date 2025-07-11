package cn.dtransfer.system.domain;

import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

import javax.persistence.Table;

/**
 * 部门表 sys_dept
 *
 */
@Data
@Table(name = "sys_dept")
public class Dept extends BaseEntity<BaseEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 显示顺序
     */
    private String orderNum;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 负责人编号
     */
    private Long leaderId;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门状态:0正常,1停用
     */
    private String status;

    /**
     * 是否默认
     */
    private Boolean isDefault;

    /**
     * 父部门名称
     */
    private String parentName;

}
