package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.ICustomerService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.Customer;
import cn.dtransfer.admin.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 客户管理Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-29
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 查询客户管理
     *
     * @param id 客户管理ID
     * @return 客户管理
     */
    @Override
    public Customer selectCustomerById(Long id) {
        return customerMapper.selectCustomerById(id);
    }

    /**
     * 查询客户管理列表
     *
     * @param customer 客户管理
     * @return 客户管理
     */
    @Override
    public List<Customer> selectCustomerList(Customer customer) {
        return customerMapper.selectCustomerList(customer);
    }

    /**
     * 新增客户管理
     *
     * @param customer 客户管理
     * @return 结果
     */
    @Override
    public int insertCustomer(Customer customer) {
        return customerMapper.insert(customer);
    }

    /**
     * 修改客户管理
     *
     * @param customer 客户管理
     * @return 结果
     */
    @Override
    public int updateCustomer(Customer customer) {
        return customerMapper.updateById(customer);
    }

    /**
     * 删除客户管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCustomerByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return customerMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除客户管理信息
     *
     * @param id 客户管理ID
     * @return 结果
     */
    @Override
    public int deleteCustomerById(Long id) {
        return customerMapper.deleteById(id);
    }
}
