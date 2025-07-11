package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.ICustomerAttachmentsService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.CustomerAttachments;
import cn.dtransfer.admin.mapper.CustomerAttachmentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 客户附件Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-29
 */
@Service
public class CustomerAttachmentsServiceImpl extends ServiceImpl<CustomerAttachmentsMapper, CustomerAttachments> implements ICustomerAttachmentsService {
    @Autowired
    private CustomerAttachmentsMapper customerAttachmentsMapper;

    /**
     * 查询客户附件
     *
     * @param id 客户附件ID
     * @return 客户附件
     */
    @Override
    public CustomerAttachments selectCustomerAttachmentsById(Long id) {
        return customerAttachmentsMapper.selectById(id);
    }

    /**
     * 查询客户附件列表
     *
     * @param customerAttachments 客户附件
     * @return 客户附件
     */
    @Override
    public List<CustomerAttachments> selectCustomerAttachmentsList(CustomerAttachments customerAttachments) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("customer_id", customerAttachments.getCustomerId());
        queryWrapper.eq("delete_flag", customerAttachments.getDeleteFlag());
        return customerAttachmentsMapper.selectList(queryWrapper);
    }

    /**
     * 新增客户附件
     *
     * @param customerAttachments 客户附件
     * @return 结果
     */
    @Override
    public int insertCustomerAttachments(CustomerAttachments customerAttachments) {
        return customerAttachmentsMapper.insert(customerAttachments);
    }

    /**
     * 修改客户附件
     *
     * @param customerAttachments 客户附件
     * @return 结果
     */
    @Override
    public int updateCustomerAttachments(CustomerAttachments customerAttachments) {
        return customerAttachmentsMapper.updateById(customerAttachments);
    }

    /**
     * 删除客户附件对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCustomerAttachmentsByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return customerAttachmentsMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除客户附件信息
     *
     * @param id 客户附件ID
     * @return 结果
     */
    @Override
    public int deleteCustomerAttachmentsById(Long id) {
        return customerAttachmentsMapper.deleteById(id);
    }
}
