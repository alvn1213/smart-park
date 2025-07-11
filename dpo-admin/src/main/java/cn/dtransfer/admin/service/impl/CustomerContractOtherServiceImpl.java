package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.ICustomerContractOtherService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.CustomerContractOther;
import cn.dtransfer.admin.mapper.CustomerContractOtherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户合同其他费用Service业务层处理
 *
 * @author dtransfer
 * @date 2024-09-27
 */
@Service
public class CustomerContractOtherServiceImpl extends ServiceImpl<CustomerContractOtherMapper, CustomerContractOther> implements ICustomerContractOtherService {
    @Autowired
    private CustomerContractOtherMapper customerContractOtherMapper;

    /**
     * 查询客户合同其他费用
     *
     * @param id 客户合同其他费用ID
     * @return 客户合同其他费用
     */
    @Override
    public CustomerContractOther selectCustomerContractOtherById(Long id) {
        return customerContractOtherMapper.selectById(id);
    }

    /**
     * 查询客户合同其他费用列表
     *
     * @param customerContractOther 客户合同其他费用
     * @return 客户合同其他费用
     */
    @Override
    public List<CustomerContractOther> selectCustomerContractOtherList(CustomerContractOther customerContractOther) {
        QueryWrapper queryWrapper = new QueryWrapper();
        return customerContractOtherMapper.selectList(queryWrapper);
    }

    /**
     * 新增客户合同其他费用
     *
     * @param customerContractOther 客户合同其他费用
     * @return 结果
     */
    @Override
    public int insertCustomerContractOther(CustomerContractOther customerContractOther) {
        return customerContractOtherMapper.insert(customerContractOther);
    }

    /**
     * 修改客户合同其他费用
     *
     * @param customerContractOther 客户合同其他费用
     * @return 结果
     */
    @Override
    public int updateCustomerContractOther(CustomerContractOther customerContractOther) {
        return customerContractOtherMapper.updateById(customerContractOther);
    }

    /**
     * 删除客户合同其他费用对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCustomerContractOtherByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return customerContractOtherMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除客户合同其他费用信息
     *
     * @param id 客户合同其他费用ID
     * @return 结果
     */
    @Override
    public int deleteCustomerContractOtherById(Long id) {
        return customerContractOtherMapper.deleteById(id);
    }
}
