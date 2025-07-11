package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.ICustomerContractLogService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.CustomerContractLog;
import cn.dtransfer.admin.mapper.CustomerContractLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 合同操作日志Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-30
 */
@Service
public class CustomerContractLogServiceImpl extends ServiceImpl<CustomerContractLogMapper, CustomerContractLog> implements ICustomerContractLogService {
    @Autowired
    private CustomerContractLogMapper customerContractLogMapper;

    /**
     * 查询合同操作日志
     *
     * @param id 合同操作日志ID
     * @return 合同操作日志
     */
    @Override
    public CustomerContractLog selectCustomerContractLogById(Long id) {
        return customerContractLogMapper.selectById(id);
    }

    /**
     * 查询合同操作日志列表
     *
     * @param customerContractLog 合同操作日志
     * @return 合同操作日志
     */
    @Override
    public List<CustomerContractLog> selectCustomerContractLogList(CustomerContractLog customerContractLog) {
        QueryWrapper queryWrapper = new QueryWrapper();
        return customerContractLogMapper.selectList(queryWrapper);
    }

    /**
     * 新增合同操作日志
     *
     * @param customerContractLog 合同操作日志
     * @return 结果
     */
    @Override
    public int insertCustomerContractLog(CustomerContractLog customerContractLog) {
        return customerContractLogMapper.insert(customerContractLog);
    }

    /**
     * 修改合同操作日志
     *
     * @param customerContractLog 合同操作日志
     * @return 结果
     */
    @Override
    public int updateCustomerContractLog(CustomerContractLog customerContractLog) {
        return customerContractLogMapper.updateById(customerContractLog);
    }

    /**
     * 删除合同操作日志对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCustomerContractLogByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return customerContractLogMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除合同操作日志信息
     *
     * @param id 合同操作日志ID
     * @return 结果
     */
    @Override
    public int deleteCustomerContractLogById(Long id) {
        return customerContractLogMapper.deleteById(id);
    }
}
