package cn.dtransfer.system.domain;

import lombok.Data;

import javax.persistence.Table;

/**
 * 角色和部门关联 sys_role_dept
 *
 */
@Data
@Table(name = "sys_role_dept")
public class RoleDept {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 部门ID
     */
    private Long deptId;
}
