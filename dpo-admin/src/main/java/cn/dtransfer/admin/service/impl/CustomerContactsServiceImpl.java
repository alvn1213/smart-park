package cn.dtransfer.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.CustomerContacts;
import cn.dtransfer.admin.mapper.CustomerContactsMapper;
import cn.dtransfer.admin.service.ICustomerContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 联系人Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-29
 */
@Service
public class CustomerContactsServiceImpl extends ServiceImpl<CustomerContactsMapper, CustomerContacts> implements ICustomerContactsService {
    @Autowired
    private CustomerContactsMapper customerContactsMapper;

    /**
     * 查询联系人
     *
     * @param id 联系人ID
     * @return 联系人
     */
    @Override
    public CustomerContacts selectCustomerContactsById(Long id) {
        return customerContactsMapper.selectById(id);
    }

    /**
     * 查询联系人列表
     *
     * @param customerContacts 联系人
     * @return 联系人
     */
    @Override
    public List<CustomerContacts> selectCustomerContactsList(CustomerContacts customerContacts) {
        return customerContactsMapper.selectCustomerContactsList(customerContacts);
    }

    /**
     * 新增联系人
     *
     * @param customerContacts 联系人
     * @return 结果
     */
    @Override
    public int insertCustomerContacts(CustomerContacts customerContacts) {
        return customerContactsMapper.insert(customerContacts);
    }

    /**
     * 修改联系人
     *
     * @param customerContacts 联系人
     * @return 结果
     */
    @Override
    public int updateCustomerContacts(CustomerContacts customerContacts) {
        return customerContactsMapper.updateById(customerContacts);
    }

    /**
     * 删除联系人对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCustomerContactsByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return customerContactsMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除联系人信息
     *
     * @param id 联系人ID
     * @return 结果
     */
    @Override
    public int deleteCustomerContactsById(Long id) {
        return customerContactsMapper.deleteById(id);
    }
}
