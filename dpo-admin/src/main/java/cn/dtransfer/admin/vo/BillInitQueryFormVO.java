package cn.dtransfer.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import cn.dtransfer.common.enums.PaymentCycle;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 生成账单查询参数
 */
@Data
public class BillInitQueryFormVO implements Serializable {

    /**
     * 合同id
     */
    @NotNull(message = "合同id不能为空")
    private Long contractId;

    /**
     * 付款周期
     */
    @NotNull(message = "付款周期不能为空")
    private PaymentCycle paymentCycle;

    /**
     * 账单开始日期
     */
    @NotNull(message = "账单开始日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date billStartDate;

    /**
     * 账单结束日期
     */
    @NotNull(message = "账单结束日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date billEndDate;


}
