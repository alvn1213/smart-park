package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.Customer;

import java.util.List;

/**
 * 客户管理Service接口
 *
 * @author dtransfer
 * @date 2024-03-29
 */
public interface ICustomerService extends IService<Customer> {
    /**
     * 查询客户管理
     *
     * @param id 客户管理ID
     * @return 客户管理
     */
    Customer selectCustomerById(Long id);

    /**
     * 查询客户管理列表
     *
     * @param customer 客户管理
     * @return 客户管理集合
     */
    List<Customer> selectCustomerList(Customer customer);

    /**
     * 新增客户管理
     *
     * @param customer 客户管理
     * @return 结果
     */
    int insertCustomer(Customer customer);

    /**
     * 修改客户管理
     *
     * @param customer 客户管理
     * @return 结果
     */
    int updateCustomer(Customer customer);

    /**
     * 批量删除客户管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteCustomerByIds(String ids);

    /**
     * 删除客户管理信息
     *
     * @param id 客户管理ID
     * @return 结果
     */
    int deleteCustomerById(Long id);
}
