package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.CustomerContractExpenses;

import java.util.List;

/**
 * 客户合同费用Service接口
 *
 * @author dtransfer
 * @date 2024-03-30
 */
public interface ICustomerContractExpensesService extends IService<CustomerContractExpenses> {
    /**
     * 查询客户合同费用
     *
     * @param id 客户合同费用ID
     * @return 客户合同费用
     */
    CustomerContractExpenses selectCustomerContractExpensesById(Long id);

    /**
     * 查询客户合同费用列表
     *
     * @param customerContractExpenses 客户合同费用
     * @return 客户合同费用集合
     */
    List<CustomerContractExpenses> selectCustomerContractExpensesList(CustomerContractExpenses customerContractExpenses);

    /**
     * 新增客户合同费用
     *
     * @param customerContractExpenses 客户合同费用
     * @return 结果
     */
    int insertCustomerContractExpenses(CustomerContractExpenses customerContractExpenses);

    /**
     * 修改客户合同费用
     *
     * @param customerContractExpenses 客户合同费用
     * @return 结果
     */
    int updateCustomerContractExpenses(CustomerContractExpenses customerContractExpenses);

    /**
     * 批量删除客户合同费用
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteCustomerContractExpensesByIds(String ids);

    /**
     * 删除客户合同费用信息
     *
     * @param id 客户合同费用ID
     * @return 结果
     */
    int deleteCustomerContractExpensesById(Long id);
}
