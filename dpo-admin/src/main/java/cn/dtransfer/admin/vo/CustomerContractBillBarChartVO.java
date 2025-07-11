package cn.dtransfer.admin.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *  当年账单每月柱状图VO
 * @author dtransfer
 */
@Data
public class CustomerContractBillBarChartVO implements Serializable {

    /** 月份*/
    private String monthData;

    /** 应收款*/
    private BigDecimal totalFee;

    /** 已收款*/
    private BigDecimal receiveFee;

}
