package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.CustomerAttachments;

import java.util.List;

/**
 * 客户附件Service接口
 *
 * @author dtransfer
 * @date 2024-03-29
 */
public interface ICustomerAttachmentsService extends IService<CustomerAttachments> {
    /**
     * 查询客户附件
     *
     * @param id 客户附件ID
     * @return 客户附件
     */
    CustomerAttachments selectCustomerAttachmentsById(Long id);

    /**
     * 查询客户附件列表
     *
     * @param customerAttachments 客户附件
     * @return 客户附件集合
     */
    List<CustomerAttachments> selectCustomerAttachmentsList(CustomerAttachments customerAttachments);

    /**
     * 新增客户附件
     *
     * @param customerAttachments 客户附件
     * @return 结果
     */
    int insertCustomerAttachments(CustomerAttachments customerAttachments);

    /**
     * 修改客户附件
     *
     * @param customerAttachments 客户附件
     * @return 结果
     */
    int updateCustomerAttachments(CustomerAttachments customerAttachments);

    /**
     * 批量删除客户附件
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteCustomerAttachmentsByIds(String ids);

    /**
     * 删除客户附件信息
     *
     * @param id 客户附件ID
     * @return 结果
     */
    int deleteCustomerAttachmentsById(Long id);
}
