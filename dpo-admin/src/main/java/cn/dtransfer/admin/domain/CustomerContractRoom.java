package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 房间合同关联表 dpo_customer_contract_room
 *
 * @author dtransfer
 * @date 2024-03-30
 */
@Data
@TableName("dpo_customer_contract_room")
public class CustomerContractRoom implements Serializable {


    /** 关联合同id */
    private Long contractId;

    /** 关联房间id */
    private Long roomId;


}
