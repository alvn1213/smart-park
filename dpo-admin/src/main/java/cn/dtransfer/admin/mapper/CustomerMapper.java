package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 客户管理Mapper接口
 *
 * @author dtransfer
 * @date 2024-03-29
 */
@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {
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


}
