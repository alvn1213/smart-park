package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 客户合同其他费用对象 dpo_customer_contract_other
 *
 * @author dtransfer
 * @date 2024-09-27
 */
@Data
@TableName("dpo_customer_contract_other")
public class CustomerContractOther extends BaseEntity<CustomerContractOther> {
    private static final long serialVersionUID = 1L;

    /** 合同id */
    private Long contractId;

    /** 费项id */
    private Long expenseSettingsId;

    /** 金额 */
    private Double otherFee;

    /** 费项类型id */
    private Long expenseType;

    /** 开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /** 结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /** 备注 */
    private String remark;

    /** 关联其他费用配置 */
    @TableField(exist = false)
    private ExpenseSettings expenseSettings;

}
