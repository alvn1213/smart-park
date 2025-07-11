package cn.dtransfer.admin.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import cn.dtransfer.admin.domain.Customer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 账单报表vo
 * @author dtransfer
 */
@Data
public class CustomerContractBillReportVO implements Serializable {


    /** 客户id */
    private Long customerId;

    /** 应收租金 */
    private BigDecimal totalRent = BigDecimal.ZERO;

    /** 已收租金 */
    private BigDecimal receiveTotalRent = BigDecimal.ZERO;

    /** 应收物业管理费 */
    private BigDecimal managementTotalFee = BigDecimal.ZERO;

    /** 已收物业管理费 */
    private BigDecimal receiveManagementTotalFee = BigDecimal.ZERO;

    /** 应收电费 */
    private BigDecimal powerFee = BigDecimal.ZERO;

    /** 已收电费 */
    private BigDecimal receivePowerFee = BigDecimal.ZERO;

    /** 应收水费 */
    private BigDecimal waterFee = BigDecimal.ZERO;

    /** 已收水费 */
    private BigDecimal receiveWaterFee = BigDecimal.ZERO;

    /** 应收押金*/
    private BigDecimal deposit = BigDecimal.ZERO;

    /** 其他费用 */
    private BigDecimal otherFee = BigDecimal.ZERO;

    /** 退费费用 */
    private BigDecimal refundFee = BigDecimal.ZERO;

    /** 合计应收*/
    private BigDecimal totalBill = BigDecimal.ZERO;

    /** 合计已收*/
    private BigDecimal receiveTotalBill = BigDecimal.ZERO;

    /** 合计未收*/
    private BigDecimal waitPay = BigDecimal.ZERO;

    /** 关联客户*/
    private Customer customer;


    @TableField(exist = false)
    private String name;


}
