package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.ICustomerContractExpensesService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.CustomerContractExpenses;
import cn.dtransfer.admin.mapper.CustomerContractExpensesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户合同费用Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-30
 */
@Service
public class CustomerContractExpensesServiceImpl extends ServiceImpl<CustomerContractExpensesMapper, CustomerContractExpenses> implements ICustomerContractExpensesService {
    @Autowired
    private CustomerContractExpensesMapper customerContractExpensesMapper;

    /**
     * 查询客户合同费用
     *
     * @param id 客户合同费用ID
     * @return 客户合同费用
     */
    @Override
    public CustomerContractExpenses selectCustomerContractExpensesById(Long id) {
        return customerContractExpensesMapper.selectById(id);
    }

    /**
     * 查询客户合同费用列表
     *
     * @param customerContractExpenses 客户合同费用
     * @return 客户合同费用
     */
    @Override
    public List<CustomerContractExpenses> selectCustomerContractExpensesList(CustomerContractExpenses customerContractExpenses) {
        QueryWrapper queryWrapper = new QueryWrapper();
        return customerContractExpensesMapper.selectList(queryWrapper);
    }

    /**
     * 新增客户合同费用
     *
     * @param customerContractExpenses 客户合同费用
     * @return 结果
     */
    @Override
    public int insertCustomerContractExpenses(CustomerContractExpenses customerContractExpenses) {
        return customerContractExpensesMapper.insert(customerContractExpenses);
    }

    /**
     * 修改客户合同费用
     *
     * @param customerContractExpenses 客户合同费用
     * @return 结果
     */
    @Override
    public int updateCustomerContractExpenses(CustomerContractExpenses customerContractExpenses) {
        return customerContractExpensesMapper.updateById(customerContractExpenses);
    }

    /**
     * 删除客户合同费用对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCustomerContractExpensesByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return customerContractExpensesMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除客户合同费用信息
     *
     * @param id 客户合同费用ID
     * @return 结果
     */
    @Override
    public int deleteCustomerContractExpensesById(Long id) {
        return customerContractExpensesMapper.deleteById(id);
    }
}
