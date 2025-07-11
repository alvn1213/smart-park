package cn.dtransfer.admin.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.dtransfer.admin.domain.CustomerContractBill;
import cn.dtransfer.common.enums.PaymentCycle;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 新建账单VO
 */
@Data
public class BillFormVO implements Serializable {


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

    /**
     * 合同关联账单
     */
    @TableField(exist = false)
    @NotNull(message = "合同关联账单不能为空")
    private List<CustomerContractBill> customerContractBills;

    /**
     * 合同关联其他账单
     */
    @TableField(exist = false)
    private List<CustomerContractBill> customerContractOtherBills;


    /**
     * 创建人
     */
    private String createBy;


    // 待废弃
    private List<CustomerContractBillFormVO> customerContractBillFormVOList;

}
