package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.dtransfer.common.core.domain.BaseEntity;
import cn.dtransfer.common.enums.BillType;
import cn.dtransfer.common.enums.PaymentCycle;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 客户合同账单对象 dpo_customer_contract_bill
 *
 * @author dtransfer
 * @date 2024-03-30
 */
@Data
@TableName("dpo_customer_contract_bill")
public class CustomerContractBill extends BaseEntity<CustomerContractBill> {
    private static final long serialVersionUID = 1L;

    /**
     * 客户id
     */
    private Long customerId;

    /**
     * 合同id
     */
    private Long contractId;

    /**
     * 账单名称
     */
    private String name;

    /**
     * 账单编号
     */
    private String sn;

    /**
     * 管理编号
     */
    private String manageSn;

    /**
     * 账单状态
     */
    private Status status;

    /**
     * 账单状态
     */
    public enum Status implements IEnum<Integer> {

        /**
         * 未收
         */
        UNRECEIVED("未收", 0),

        /**
         * 已收
         */
        RECEIVED("已收", 1),


        /**
         * 部分
         */
        PART("部分", 2),

        /**
         * 已作废
         */
        VOIDED("已作废", 3);


        private String name;
        private int    value;

        Status(String name, int value) {
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


        public static Status parse(Integer value) {
            for (Status status : values()) {
                if (status.getValue().equals(value)) {
                    return status;
                }
            }
            return null;
        }
    }

    /**
     * 付款周期
     */
    private PaymentCycle paymentCycle;

    /**
     * 合同类型
     */
    private CustomerContract.Type type;

    /**
     * 账单类型
     */
    private BillType billType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 收款截止日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /**
     * 账单收款日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date receiveRentDate;

    /**
     * 账期
     */
    private String billDate;

    /**
     * 账单开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date billStartDate;

    /**
     * 账单结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date billEndDate;

    /**
     * 0-按固定租金，1-按实际天数
     */
    private Long rentIncreaseWay;

    /**
     * 0-普通发票，1-专用发票
     */
    private String invoiceType;

    /**
     * 收到金额
     */
    private BigDecimal receiveRent = BigDecimal.ZERO;

    /**
     * 物业管理费
     */
    private BigDecimal managementTotalFee = BigDecimal.ZERO;

    /**
     * 租金
     */
    private BigDecimal rent = BigDecimal.ZERO;

    /**
     * 租金滞纳金
     */
    private BigDecimal rentOverdue = BigDecimal.ZERO;

    /**
     * 押金
     */
    private BigDecimal deposit = BigDecimal.ZERO;

    /**
     * 押金滞纳金
     */
    private BigDecimal depositOverdue = BigDecimal.ZERO;

    /**
     * 电费
     */
    private BigDecimal powerFee = BigDecimal.ZERO;

    /**
     * 已收电费
     */
    private BigDecimal receivePowerFee = BigDecimal.ZERO;

    /**
     * 水费
     */
    private BigDecimal waterFee = BigDecimal.ZERO;

    /**
     * 已收水费
     */
    private BigDecimal receiveWaterFee = BigDecimal.ZERO;

    /**
     * 已收物业管理费
     */
    private BigDecimal receiveManagementTotalFee = BigDecimal.ZERO;

    /**
     * 其他费用
     */
    private BigDecimal otherFee = BigDecimal.ZERO;

    /**
     * 已收其他费用
     */
    private BigDecimal receiveOtherFee = BigDecimal.ZERO;

    /**
     * 应退款
     */
    private BigDecimal refundFee = BigDecimal.ZERO;

    /**
     * 关联合同
     */
    @TableField(exist = false)
    private CustomerContract customerContract;

    /**
     * 关联多个费项
     */
    @TableField(exist = false)
    private List<CustomerContractExpenses> customerContractExpenses;

    /**
     * 关联房间
     */
    @TableField(exist = false)
    private List<Room> rooms;


    /**
     * 根据客户名称查询
     */
    @TableField(exist = false)
    private String queryCustomerName;

    /**
     * 根据年月查询
     */
    @TableField(exist = false)
    private String queryMonth;

    /**
     * 合计应收
     */
    @TableField(exist = false)
    private BigDecimal totalBill;

    /**
     * 待付款
     */
    @TableField(exist = false)
    private BigDecimal waitPay = BigDecimal.ZERO;

    /**
     * 合计已收
     */
    @TableField(exist = false)
    private BigDecimal receiveTotalBill = BigDecimal.ZERO;

    @TableField(exist = false)
    private Integer pageNum;

    @TableField(exist = false)
    private Integer pageSize;

    @TableField(exist = false)
    private Integer isOverdue;

    /**
     * 租赁合计月份
     */
    @TableField(exist = false)
    private Integer totalRentMonth;

    /**
     * 租赁日期
     */
    @TableField(exist = false)
    private String rentDate;

}
