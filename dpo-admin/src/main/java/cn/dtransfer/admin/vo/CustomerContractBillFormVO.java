package cn.dtransfer.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import cn.dtransfer.common.enums.MoneyType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 新建合同账单VO
 */
@Data
public class CustomerContractBillFormVO implements Serializable {

    /**
     * 合同id
     */
    private Long contractId;

    /**
     * 账单名称
     */
    private String name;

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
     * 账单收款日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date receiveRentDate;


    /**
     * 其他费用
     */
    private BigDecimal otherFee = BigDecimal.ZERO;

    /**
     * 应退款
     */
    private BigDecimal refundFee = BigDecimal.ZERO;

    /**
     * 款项类型
     */
    private MoneyType moneyType;
}
