package cn.dtransfer.admin.domain;

import cn.dtransfer.admin.handler.ContractStatusEnumHandler;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 合同操作日志对象 dpo_customer_contract_log
 *
 * @author dtransfer
 * @date 2024-03-30
 */
@Data
@TableName("dpo_customer_contract_log")
public class CustomerContractLog extends BaseEntity<CustomerContractLog> {
    private static final long serialVersionUID = 1L;

    /**
     * 合同id
     */
    private Long contractId;

    /**
     * 合同状态
     */
    @TableField(typeHandler = ContractStatusEnumHandler.class)
    private CustomerContract.Status status;

    /**
     * 备注
     */
    private String remark;

}
