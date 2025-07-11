package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.CustomerContractRefund;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 客户合同退租Mapper接口
 *
 * @author dtransfer
 * @date 2024-03-31
 */
@Mapper
public interface CustomerContractRefundMapper extends BaseMapper<CustomerContractRefund> {
    /**
     * 查询客户合同退租
     *
     * @param id 客户合同退租ID
     * @return 客户合同退租
     */
    CustomerContractRefund selectCustomerContractRefundById(Long id);

    /**
     * 查询客户合同退租列表
     *
     * @param customerContractRefund 客户合同退租
     * @return 客户合同退租集合
     */
    List<CustomerContractRefund> selectContractRefundList(CustomerContractRefund customerContractRefund);

}
