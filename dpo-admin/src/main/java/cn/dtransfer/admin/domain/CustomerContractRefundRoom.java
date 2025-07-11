package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 退租房间关联对象 dpo_customer_contract_refund_room
 *
 * @author dtransfer
 * @date 2024-03-31
 */
@Data
@TableName("dpo_customer_contract_refund_room")
public class CustomerContractRefundRoom implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 退租id */
    private Long refundId;

    /** 房间id */
    private Long roomId;

}
