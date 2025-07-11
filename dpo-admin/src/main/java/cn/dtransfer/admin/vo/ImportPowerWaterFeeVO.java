package cn.dtransfer.admin.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 导入水电费类
 * @author dtransfer
 */
@Data
public class ImportPowerWaterFeeVO implements Serializable {

    /**序号*/
    private Long id;

    /**合同号*/
    private String contractSn;

    /**账单号*/
    private String sn;

    /**公司名称*/
    private String name;

    /**公司地址*/
    private String address;

    /**电费*/
    private BigDecimal powerFee;

    /**水费*/
    private BigDecimal waterFee;

}
