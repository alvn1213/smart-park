package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 客户合同对象 dpo_customer_contract
 *
 * @author dtransfer
 * @date 2024-03-29
 */
@Data
@TableName("dpo_customer_contract")
public class CustomerContract extends BaseEntity<CustomerContract> {
    private static final long serialVersionUID = 1L;

    /**
     * 客户id
     */
    @NotNull(message = "客户id不能为空!")
    private Long customerId;

    /**
     * 合同名称
     */
    @NotBlank(message = "合同名称不能为空!")
    private String name;

    /**
     * 合同编号
     */
    @NotBlank(message = "合同编号不能为空!")
    private String sn;

    /**
     * 物管合同编号
     */
    @NotBlank(message = "物管合同编号不能为空!")
    private String pmSn;

    /**
     * 管理编号
     */
    private String manageSn;

    /**
     * 合同状态
     */
    private Status status;


    /**
     * 合同状态
     */
    public enum Status implements IEnum<Integer> {

        /**
         * 草稿
         */
        DRAFT("草稿", 0),

        /**
         * 待审批
         */
        APPROVING("待审批", 1),

        /**
         * 已审批
         */
        APPROVED("已审批", 2),

        /**
         * 已拒绝
         */
        REFUSE("已拒绝", 3),

        /**
         * 执行中
         */
        EXECUTING("执行中", 4),

        /**
         * 已作废
         */
        VOIDED("已作废", 5),


        /**
         * 已到期
         */
        OVERDUE("已到期", 6),


        /**
         * 已结束
         */
        END("已结束", 7);


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
     * 合同类型（0-正式合同,1-意向合同）
     */
    private Type type;

    /**
     * 合同类型
     */
    public enum Type implements IEnum<Integer> {

        /**
         * 正式合同
         */
        OFFICIAL("正式合同", 0),

        /**
         * 意向合同
         */
        INTENTION("意向合同", 1);


        private String name;
        private int    value;

        Type(String name, int value) {
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

        public static Type parse(Integer value) {
            for (Type type : values()) {
                if (type.getValue().equals(value)) {
                    return type;
                }
            }
            return null;
        }
    }

    /**
     * 备注
     */
    private String remark;

    /**
     * 需求开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date needStartDate;

    /**
     * 需求结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date needEndDate;

    /**
     * 租赁开始期限
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /**
     * 租赁结束期限
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /**
     * 账单收款日
     */
    private Long receiveRentDay;

    /**
     * 租赁共几个月
     */
    private Long totalMonths;

    /**
     * 签订日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date signDate;

    /**
     * 租赁方 0：公司租赁 2：个人租赁
     */
    private Long lessorType;

    /**
     * 客户名称（企业名称）
     */
    private String customerName;

    /**
     * 客户类型
     */
    private Long customerType;

    /**
     * 所属行业
     */
    private String sector;

    /**
     * 公司邮箱
     */
    private String email;

    /**
     * 统一信用代码
     */
    private String creditNo;

    /**
     * 法人
     */
    private String operName;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 意向金
     */
    private BigDecimal intentionMoney;

    /**
     * 管理费单价
     */
    private BigDecimal managementFee;

    /**
     * 管理费单价单位(0-日计算，月计算)
     */
    private String managementFeeUnit;

    /**
     * 日管理费
     */
    private BigDecimal managementFeeIncreaseDay;

    /**
     * 月管理费
     */
    private BigDecimal managementFeeIncreaseMonth;

    /**
     * 租金单价
     */
    private BigDecimal rentPrice;

    /**
     * 租金单价单位(0-日计算，月计算)
     */
    private String rentPriceUnit;

    /**
     * 日租金
     */
    private BigDecimal rentIncreaseDay;

    /**
     * 月租金
     */
    private BigDecimal rentIncreaseMonth;

    /**
     * 0-按固定租金，1-按实际天数
     */
    private Long rentIncreaseWay;

    /**
     * 付款周期
     */
    private Long paymentPeriod;

    /**
     * 押金
     */
    private BigDecimal deposit;

    /**
     * 押几月
     */
    private Long depositPeriod;

    /**
     * 押金滞纳金比例
     */
    private BigDecimal depositOverduePercent;

    /**
     * 租金滞纳金比例
     */
    private BigDecimal rentOverduePercent;

    /**
     * 邮编
     */
    private String postCode;

    /**
     * 合同关联房间
     */
    @TableField(exist = false)
    private List<Room> customerContractRooms;

    /**
     * 合同关联账单
     */
    @TableField(exist = false)
    private List<CustomerContractBill> customerContractBills;

    /**
     * 合同关联其他账单
     */
    @TableField(exist = false)
    private List<CustomerContractBill> customerContractOtherBills;


    @TableField(exist = false)
    private Integer pageNum;

    @TableField(exist = false)
    private Integer pageSize;

    /**
     * 关联其他费用
     */
    @TableField(exist = false)
    private List<CustomerContractOther> customerContractOthers;

}
