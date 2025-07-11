package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.vo.CustomerContractBillReportVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户合同账单报表Mapper接口
 *
 * @author dtransfer
 * @date 2024-11-26
 */
@Mapper
public interface CustomerContractBillReportVOMapper
{


    /**
     * 查询客户合同账单报表列表
     *
     * @param customerName 客户名称
     * @param queryStartMonth 起始月份
     * @param queryEndMonth 结束月份
     * @return 客户合同账单集合
     */
    List<CustomerContractBillReportVO> selectCustomerContractBillReportVOList(@Param("customerName") String customerName, @Param("queryStartMonth")String queryStartMonth, @Param("queryEndMonth")String queryEndMonth);




}
