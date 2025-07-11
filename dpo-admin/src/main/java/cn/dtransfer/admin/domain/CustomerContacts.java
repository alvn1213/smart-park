package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 联系人对象 dpo_customer_contacts
 *
 * @author dtransfer
 * @date 2024-03-29
 */
@Data
@TableName("dpo_customer_contacts")
public class CustomerContacts extends BaseEntity<CustomerContacts> {
    private static final long serialVersionUID = 1L;

    /**
     * 联系人
     */
    private String name;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 备注
     */
    private String remark;

    /**
     * 客户id
     */
    private Long customerId;

}
