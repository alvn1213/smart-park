package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.CustomerContractOther;

import java.util.List;

/**
 * 客户合同其他费用Service接口
 *
 * @author dtransfer
 * @date 2024-09-27
 */
public interface ICustomerContractOtherService extends IService<CustomerContractOther> {
    /**
     * 查询客户合同其他费用
     *
     * @param id 客户合同其他费用ID
     * @return 客户合同其他费用
     */
    CustomerContractOther selectCustomerContractOtherById(Long id);

    /**
     * 查询客户合同其他费用列表
     *
     * @param customerContractOther 客户合同其他费用
     * @return 客户合同其他费用集合
     */
    List<CustomerContractOther> selectCustomerContractOtherList(CustomerContractOther customerContractOther);

    /**
     * 新增客户合同其他费用
     *
     * @param customerContractOther 客户合同其他费用
     * @return 结果
     */
    int insertCustomerContractOther(CustomerContractOther customerContractOther);

    /**
     * 修改客户合同其他费用
     *
     * @param customerContractOther 客户合同其他费用
     * @return 结果
     */
    int updateCustomerContractOther(CustomerContractOther customerContractOther);

    /**
     * 批量删除客户合同其他费用
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteCustomerContractOtherByIds(String ids);

    /**
     * 删除客户合同其他费用信息
     *
     * @param id 客户合同其他费用ID
     * @return 结果
     */
    int deleteCustomerContractOtherById(Long id);
}
