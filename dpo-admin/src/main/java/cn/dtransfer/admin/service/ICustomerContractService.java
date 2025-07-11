package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.CustomerContract;

import java.util.List;

/**
 * 客户合同Service接口
 *
 * @author dtransfer
 * @date 2024-03-29
 */
public interface ICustomerContractService extends IService<CustomerContract> {
    /**
     * 查询客户合同
     *
     * @param id 客户合同ID
     * @return 客户合同
     */
    CustomerContract selectCustomerContractById(Long id);

    /**
     * 查询客户合同列表
     *
     * @param customerContract 客户合同
     * @return 客户合同集合
     */
    List<CustomerContract> selectCustomerContractList(CustomerContract customerContract);

    /**
     * 新增客户合同
     *
     * @param customerContract 客户合同
     * @return 结果
     */
    int insertCustomerContract(CustomerContract customerContract);

    /**
     * 修改客户合同
     *
     * @param customerContract 客户合同
     * @return 结果
     */
    int updateCustomerContract(CustomerContract customerContract);

    /**
     * 批量删除客户合同
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteCustomerContractByIds(String ids);

    /**
     * 判断是否存在
     *
     * @param property 属性名称
     * @param value    属性值
     * @return 是否存在
     */
    boolean exists(String property, Object value);


    /**
     * 判断是否唯一
     *
     * @param id       ID
     * @param property 属性名称
     * @param value    属性值
     * @return 是否唯一
     */
    boolean unique(Long id, String property, Object value);


    /**
     * 提交合同
     *
     * @param ids 合同ids
     * @return 结果
     */
    int commit(String ids);


    /**
     * 作废合同
     *
     * @param ids 合同ids
     * @return 结果
     */
    int voided(String ids);

    /**
     * 修改合同状态
     *
     * @param ids 合同ids
     * @param status 合同状态
     * @return 结果
     */
    int updateContractsStatus(String ids, CustomerContract.Status status);

    /**
     * 审批合同
     *
     * @param ids 合同ids
     * @param status 合同状态
     * @param remark 原因
     * @return 结果
     */
    int approveContract(String ids, CustomerContract.Status status, String remark);

}
