package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.CustomerContacts;

import java.util.List;

/**
 * 联系人Service接口
 *
 * @author dtransfer
 * @date 2024-03-29
 */
public interface ICustomerContactsService extends IService<CustomerContacts> {
    /**
     * 查询联系人
     *
     * @param id 联系人ID
     * @return 联系人
     */
    CustomerContacts selectCustomerContactsById(Long id);

    /**
     * 查询联系人列表
     *
     * @param customerContacts 联系人
     * @return 联系人集合
     */
    List<CustomerContacts> selectCustomerContactsList(CustomerContacts customerContacts);

    /**
     * 新增联系人
     *
     * @param customerContacts 联系人
     * @return 结果
     */
    int insertCustomerContacts(CustomerContacts customerContacts);

    /**
     * 修改联系人
     *
     * @param customerContacts 联系人
     * @return 结果
     */
    int updateCustomerContacts(CustomerContacts customerContacts);

    /**
     * 批量删除联系人
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteCustomerContactsByIds(String ids);

    /**
     * 删除联系人信息
     *
     * @param id 联系人ID
     * @return 结果
     */
    int deleteCustomerContactsById(Long id);
}
