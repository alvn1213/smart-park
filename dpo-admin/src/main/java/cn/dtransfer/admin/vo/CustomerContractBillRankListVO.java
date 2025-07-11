package cn.dtransfer.admin.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *  企业账单排行榜
 * @author dtransfer
 */
@Data
public class CustomerContractBillRankListVO implements Serializable {

    /** 企业名称*/
    private String name;

    /** 应收款*/
    private BigDecimal total;
}
