package cn.dtransfer.admin.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author dtransfer
 */
@Data
public class ImportRentVO implements Serializable {


    private Long billId;

    /** 合同编号 */
    private String contractSn;

    /** 物管合同编号 */
    private String pmSn;

    /** 账单号 */
    private String sn;

    /** 公司名称 */
    private String name;

    /** 账期 */
    private String billDate;

    /** 租赁起止期 */
    private String rentDate;

    /** 租金 */
    private BigDecimal rent = BigDecimal.ZERO;

    /** 已收到租金*/
    private BigDecimal receiveRent = BigDecimal.ZERO;

    /** 物业管理费 */
    private BigDecimal managementTotalFee = BigDecimal.ZERO;;

    /** 已收到物业管理费*/
    private BigDecimal receiveManagementTotalFee = BigDecimal.ZERO;

    /** 电费 */
    private BigDecimal powerFee = BigDecimal.ZERO;

    /** 已收到电费*/
    private BigDecimal receivePowerFee = BigDecimal.ZERO;

    /** 水费 */
    private BigDecimal waterFee = BigDecimal.ZERO;

    /** 已收到水费*/
    private BigDecimal receiveWaterFee = BigDecimal.ZERO;

    /** 其他费用*/
    private BigDecimal otherFee = BigDecimal.ZERO;

    /** 应退费用*/
    private BigDecimal refundFee = BigDecimal.ZERO;

    /** 押金 */
    private BigDecimal deposit = BigDecimal.ZERO;

    /** 账单状态 */
    private String status;

    /** 是否逾期 */
    private String isOverDue;

    /** 备注 */
    private String remark;



}
