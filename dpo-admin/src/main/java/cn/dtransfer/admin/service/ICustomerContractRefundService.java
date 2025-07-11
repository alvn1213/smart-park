package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.CustomerContractRefund;

import java.util.List;

/**
 * 客户合同退租Service接口
 *
 * @author dtransfer
 * @date 2024-03-31
 */
public interface ICustomerContractRefundService extends IService<CustomerContractRefund> {
    /**
     * 查询客户合同退租
     *
     * @param id 客户合同退租ID
     * @return 客户合同退租
     */
    CustomerContractRefund selectCustomerContractRefundById(Long id);

    /**
     * 查询客户合同退租列表
     *
     * @param customerContractRefund 客户合同退租
     * @return 客户合同退租集合
     */
    List<CustomerContractRefund> selectCustomerContractRefundList(CustomerContractRefund customerContractRefund);

    /**
     * 新增客户合同退租
     *
     * @param customerContractRefund 客户合同退租
     * @return 结果
     */
    int insertCustomerContractRefund(CustomerContractRefund customerContractRefund);

    /**
     * 修改客户合同退租
     *
     * @param customerContractRefund 客户合同退租
     * @return 结果
     */
    int updateCustomerContractRefund(CustomerContractRefund customerContractRefund);

    /**
     * 确定退款
     *
     * @param ids 合同ids
     * @return 结果
     */
    int voided(String ids,Long id);

    /**
     * 批量删除客户合同退租
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteCustomerContractRefundByIds(String ids);

    /**
     * 删除客户合同退租信息
     *
     * @param id 客户合同退租ID
     * @return 结果
     */
    int deleteCustomerContractRefundById(Long id);
}
