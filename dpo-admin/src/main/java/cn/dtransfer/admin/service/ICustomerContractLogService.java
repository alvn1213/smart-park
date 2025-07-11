package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.CustomerContractLog;

import java.util.List;

/**
 * 合同操作日志Service接口
 *
 * @author dtransfer
 * @date 2024-03-30
 */
public interface ICustomerContractLogService extends IService<CustomerContractLog> {
    /**
     * 查询合同操作日志
     *
     * @param id 合同操作日志ID
     * @return 合同操作日志
     */
    CustomerContractLog selectCustomerContractLogById(Long id);

    /**
     * 查询合同操作日志列表
     *
     * @param customerContractLog 合同操作日志
     * @return 合同操作日志集合
     */
    List<CustomerContractLog> selectCustomerContractLogList(CustomerContractLog customerContractLog);

    /**
     * 新增合同操作日志
     *
     * @param customerContractLog 合同操作日志
     * @return 结果
     */
    int insertCustomerContractLog(CustomerContractLog customerContractLog);

    /**
     * 修改合同操作日志
     *
     * @param customerContractLog 合同操作日志
     * @return 结果
     */
    int updateCustomerContractLog(CustomerContractLog customerContractLog);

    /**
     * 批量删除合同操作日志
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteCustomerContractLogByIds(String ids);

    /**
     * 删除合同操作日志信息
     *
     * @param id 合同操作日志ID
     * @return 结果
     */
    int deleteCustomerContractLogById(Long id);
}
