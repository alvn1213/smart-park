package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 客户合同费用对象 dpo_customer_contract_expenses
 *
 * @author dtransfer
 * @date 2024-03-30
 */
@Data
@TableName("dpo_customer_contract_expenses")
public class CustomerContractExpenses extends BaseEntity<CustomerContractExpenses> {
    private static final long serialVersionUID = 1L;

    /**
     * 账单id
     */
    private Long billId;

    /**
     * 费用名称
     */
    private String expenseName;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 费用类型(0-收费，1-退费)
     */
    private ExpenseType expenseType;

    /**
     * 费项类型
     */
    public enum ExpenseType implements IEnum<Integer> {

        /**
         * 收费
         */
        IN("收费", 0),

        /**
         * 退费
         */
        OUT("退费", 1);


        private String name;
        private int    value;

        ExpenseType(String name, int value) {
            this.name  = name;
            this.value = value;
        }

        @Override
        public Integer getValue() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }

        public static ExpenseType parse(Integer value) {
            for (ExpenseType expenseType : values()) {
                if (expenseType.getValue().equals(value)) {
                    return expenseType;
                }
            }
            return null;
        }
    }

    /**
     * 备注
     */
    private String remark;

}
