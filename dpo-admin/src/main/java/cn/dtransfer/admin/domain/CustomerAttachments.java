package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 客户附件对象 dpo_customer_attachments
 *
 * @author dtransfer
 * @date 2024-03-29
 */
@Data
@TableName("dpo_customer_attachments")
public class CustomerAttachments extends BaseEntity<CustomerAttachments> {
    private static final long serialVersionUID = 1L;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件地址
     */
    private String url;

    /**
     * 客户id
     */
    private Long customerId;

}
