package cn.dtransfer.admin.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *  通用柱状图VO
 * @author dtransfer
 */
@Data
public class ChartVO implements Serializable {

    /** x轴数据*/
    private String x;
    /** y轴数据*/
    private BigDecimal y;
}
