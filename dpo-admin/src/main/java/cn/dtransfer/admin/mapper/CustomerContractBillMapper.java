package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.CustomerContractBill;
import cn.dtransfer.admin.vo.CustomerContractBillBarChartVO;
import cn.dtransfer.admin.vo.CustomerContractBillRankListVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 客户合同账单Mapper接口
 *
 * @author dtransfer
 * @date 2024-03-30
 */
@Mapper
public interface CustomerContractBillMapper extends BaseMapper<CustomerContractBill> {
    /**
     * 查询客户合同账单
     *
     * @param id 客户合同账单ID
     * @return 客户合同账单
     */
    CustomerContractBill selectCustomerContractBillById(Long id);

    /**
     * 查询客户合同账单列表
     *
     * @param customerContractBill 客户合同账单
     * @return 客户合同账单集合
     */
    List<CustomerContractBill> selectCustomerContractBillList(CustomerContractBill customerContractBill);

    /**
     * 查询客户合同账单
     *
     * @param ids
     * @return 客户合同账单
     */
    List<CustomerContractBill> selectCustomerContractBillContractId(Long ids);

    /**
     * 批量确认客户合同账单
     *
     * @param billIds 需要确认的数据ID
     * @return 结果
     */
    int batchConfirmReceiveAmount(String[] billIds);


    /**
     * 批量取消客户合同账单
     *
     * @param billIds 需要取消确认的数据ID
     * @return 结果
     */
    int batchCancelReceiveAmount(String[] billIds);


    /**
     * 查询客户合同账单列表
     *
     * @param customerContractBill 客户合同账单
     * @return 客户合同账单集合
     */
    List<CustomerContractBill> selectCustomerContractBillExcelList(CustomerContractBill customerContractBill);


    /**
     * 更新账单的水电费
     *
     * @param sn 账单号
     * @param powerFee 电费
     * @param waterFee 水费
     * @return
     */
    int updatePowerWaterFeeBySn(@Param("sn")String sn, @Param("powerFee") BigDecimal powerFee, @Param("waterFee")BigDecimal waterFee);


    /**
     * 更新账单的水电费
     *
     * @param sn 账单号
     * @param rent 租金
     * @param managementTotalFee 物业管理费
     * @return
     */
    int updateRentBySn(@Param("sn")String sn, @Param("rent")BigDecimal rent, @Param("managementTotalFee")BigDecimal managementTotalFee,
                       @Param("receiveWaterFee")BigDecimal receiveWaterFee, @Param("receivePowerFee")BigDecimal receivePowerFee,
                       @Param("status") CustomerContractBill.Status status);


    /**
     * 批量作废未收款的客户合同账单
     *
     * @param contractIds 需要作废的数据ID
     * @return 结果
     */
    int voidedContractBillByContractIds(String[] contractIds);



    /**
     * 统计应收、已收账单
     *
     * @param queryMonth 当前月份
     * @return 统计账单集合
     */
    Map<String, Object> selectSumCustomerContractBill(@Param("queryMonth") String queryMonth);

    /**
     * 统计应收、已收租金
     *
     * @param queryMonth 当前月份
     * @return 统计账单集合
     */
    Map<String, Object> selectSumRentCustomerContractBill(@Param("queryMonth") String queryMonth);

    /**
     * 统计应收、已收物业费
     *
     * @param queryMonth 当前月份
     * @return 统计账单集合
     */
    Map<String, Object> selectSumManagementCustomerContractBill(@Param("queryMonth") String queryMonth);

    /**
     * 统计应收、已收水电费
     *
     * @param queryMonth 当前月份
     * @return 统计账单集合
     */
    Map<String, Object> selectSumPWCustomerContractBill(@Param("queryMonth") String queryMonth);


    /**
     * 统计当年每月应收
     *
     * @param queryYear 当前年份
     * @return 统计当年每月账单集合
     */
    List<CustomerContractBillBarChartVO> selectSumCustomerContractBillByMonth(@Param("queryYear") String queryYear);


    /**
     * 客户账单排行榜
     *
     * @param queryDate 今年 或 本月
     * @return 统计客户账单
     */
    List<CustomerContractBillRankListVO> selectCustomerRankList(@Param("queryDate") String queryDate);

    /**
     * 根据账单号查询客户合同账单
     *
     * @param sn 客户合同账单sn
     * @return 客户合同账单
     */
    CustomerContractBill selectCustomerContractBillBySn(String sn);
}
