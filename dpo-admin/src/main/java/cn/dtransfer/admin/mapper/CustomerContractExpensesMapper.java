package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.CustomerContractExpenses;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 客户合同费用Mapper接口
 *
 * @author dtransfer
 * @date 2024-03-30
 */
@Mapper
public interface CustomerContractExpensesMapper extends BaseMapper<CustomerContractExpenses> {
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
     * 根据帐单删除
     * @param billid
     */
    void deleteByBillId(Long billid);

}
