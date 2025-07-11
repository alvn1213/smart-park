package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.dtransfer.common.core.domain.BaseEntity;
import cn.dtransfer.common.enums.ExpenseType;
import lombok.Data;

/**
 * 费项配置对象 dpo_expense_settings
 *
 * @author dtransfer
 * @date 2024-03-24
 */
@Data
@TableName("dpo_expense_settings")
public class ExpenseSettings extends BaseEntity<ExpenseSettings> {
    private static final long serialVersionUID = 1L;

    /**
     * 费项名称
     */
    private String name;

    /**
     * 费项类型（0-系统费项，1-周期性费项，2-一次性费项）
     */
    private ExpenseType type;

    /**
     * 计量单位（度，平方米，吨，立方米，千克）
     */
    private String unitsCode;

    /**
     * 是否启用
     */
    private Boolean isEnabled;

    /**
     * 税率%
     */
    private Double taxFee;

    /**
     * 备注
     */
    private String memo;

}
