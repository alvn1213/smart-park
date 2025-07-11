package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.CustomerContacts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 联系人Mapper接口
 *
 * @author dtransfer
 * @date 2024-03-29
 */
@Mapper
public interface CustomerContactsMapper extends BaseMapper<CustomerContacts> {
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


}
