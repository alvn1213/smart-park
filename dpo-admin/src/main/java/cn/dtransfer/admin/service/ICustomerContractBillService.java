package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.CustomerContractBill;
import cn.dtransfer.admin.vo.BillFormVO;
import cn.dtransfer.admin.vo.CustomerContractBillRankListVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 客户合同账单Service接口
 *
 * @author dtransfer
 * @date 2024-03-30
 */
public interface ICustomerContractBillService extends IService<CustomerContractBill> {
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
     * 新增客户合同账单
     *
     * @param billFormVO 客户合同账单
     * @return 结果
     */
    int insertCustomerContractBill(BillFormVO billFormVO);




    /**
     * 批量新增客户合同账单
     *
     * @param billFormVO 客户合同账单列表
     * @return 结果
     */
    int batchInsertCustomerContractBill(BillFormVO billFormVO);

    /**
     * 修改客户合同账单
     *
     * @param customerContractBill 客户合同账单
     * @return 结果
     */
    int updateCustomerContractBill(CustomerContractBill customerContractBill);

    /**
     * 批量删除客户合同账单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteCustomerContractBillByIds(String ids);

    /**
     * 批量确认客户合同账单
     *
     * @param ids 需要确认的数据ID
     * @return 结果
     */
    int batchConfirmReceiveAmount(String ids);

    /**
     * 批量取消确认客户合同账单
     *
     * @param ids 需要确认的数据ID
     * @return 结果
     */
    int batchCancelReceiveAmount(String ids);


    /**
     * 导出客户合同账单列表
     *
     * @param customerContractBill 客户合同账单
     * @return 客户合同账单集合
     */
    List<CustomerContractBill> selectCustomerContractBillExcelList(CustomerContractBill customerContractBill);


    /**
     * 根据账单更新水电费
     *
     * @param sn 账单号
     * @param powerFee 电费
     * @param contractSn 合同号
     * @param waterFee 水费
     * @return 结果
     */
    int updatePowerWaterFeeByBillSn(String sn, String contractSn, BigDecimal powerFee, BigDecimal waterFee, String userName);


    /**
     * 根据单号查询账单
     * @param sn
     * @return
     */
    CustomerContractBill findBySn(String sn);


    /**
     * 根据账单更新租金和物业管理费
     *
     * @param sn 账单号
     * @param rent 租金
     * @param managementTotalFee 物业管理费
     * @return 结果
     */
    int updateRentByBillSn(String sn, BigDecimal rent, BigDecimal managementTotalFee,BigDecimal receiveWaterFee, BigDecimal receivePowerFee, CustomerContractBill.Status status);


    /**
     * 根据账单的租金，物业费，水电费等纬度进行统计显示
     * @param type
     * @return
     */
    Map analysisContractBill(String type);


    /**
     * 统计当年每月应收款,已收款，未收款
     * @return
     */
    Map analysisYearContractBill();


    /**
     * 企业账单排行榜
     */
    List<CustomerContractBillRankListVO> rankList(String dateType);

}
