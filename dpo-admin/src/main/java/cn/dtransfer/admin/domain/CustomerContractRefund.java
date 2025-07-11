package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.dtransfer.admin.handler.BannerImageHandler;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 客户合同退租对象 dpo_customer_contract_refund
 *
 * @author dtransfer
 * @date 2024-03-31
 */
@Data
@TableName("dpo_customer_contract_refund")
public class CustomerContractRefund extends BaseEntity<CustomerContractRefund> {
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
     * 退租单号
     */
    private String sn;

    /**
     * 退租时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date refundDate;

    /**
     * 应收费用
     */
    private BigDecimal receiveFee;

    /**
     * 应退费用
     */
    private BigDecimal refundFee;

    /**
     * 合计费用
     */
    private BigDecimal totalFee;

    /**
     * 信息检索（搜索专用）
     */
    @Transient
    private String searchValue;

    /**
     * 退租状态
     */
    private Boolean status;

    /**
     * 退款原因
     */
    private RefundReason refundReason;

    public enum RefundReason implements IEnum<Integer> {

        /**
         * 正常到租
         */
        NORMAL("正常到租", 0),

        /**
         * 协商提前退租
         */
        CONSULT("协商提前退租", 1),

        /**
         * 违约清退
         */
        VIOLATION("违约清退", 2),

        /**
         * 其他
         */
        OTHER("其他", 3);


        private String name;
        private int    value;

        RefundReason(String name, int value) {
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

        public static RefundReason parse(Integer value) {
            for (RefundReason refundReason : values()) {
                if (refundReason.getValue().equals(value)) {
                    return refundReason;
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
     * 存档图片
     */
    @TableField(typeHandler = BannerImageHandler.class)
    private List<BannerImage> archiveImageList = new ArrayList<>();

    /**
     * 客户合同对象
     */
    @TableField(exist = false)
    private CustomerContract customerContract;

    /**
     * 合同关联房间
     */
    @TableField(exist = false)
    private List<CustomerContractRefundRoom> customerContractRefundRooms;

}
