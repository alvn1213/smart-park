package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.dtransfer.admin.domain.enumHandler.RepairStatusEnumHandler;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 报修记录对象 dpo_repair_log
 *
 * @author dtransfer
 * @date 2024-03-25
 */
@Data
@TableName("dpo_repair_log")
public class RepairLog extends BaseEntity<RepairLog> {
    private static final long serialVersionUID = 1L;

    /** 类型 */
    @TableField(typeHandler = RepairStatusEnumHandler.class)
    private Repair.Status type;

    /** 详情 */
    private String detail;

    /** 报修单 */
    private Long repairId;

}
