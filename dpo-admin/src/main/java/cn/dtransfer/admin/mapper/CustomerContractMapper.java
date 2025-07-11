package cn.dtransfer.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.dtransfer.admin.domain.CustomerContract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户合同Mapper接口
 *
 * @author dtransfer
 * @date 2024-03-29
 */
@Mapper
public interface CustomerContractMapper extends BaseMapper<CustomerContract> {
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
     * 提交合同，更改合同状态
     *
     * @param ids 合同ids,
     * @param status 合同状态
     * @return
     */
    int updateContractsStatus(@Param("ids") String[] ids, @Param("status") CustomerContract.Status status);
}
