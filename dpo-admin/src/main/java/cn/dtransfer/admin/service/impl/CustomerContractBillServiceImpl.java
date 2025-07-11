package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.ICustomerContractBillService;
import cn.dtransfer.admin.vo.*;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import cn.dtransfer.admin.domain.CustomerContract;
import cn.dtransfer.admin.domain.CustomerContractBill;
import cn.dtransfer.admin.mapper.CustomerContractBillMapper;
import cn.dtransfer.admin.mapper.CustomerContractExpensesMapper;
import cn.dtransfer.admin.mapper.CustomerContractMapper;
import cn.dtransfer.admin.vo.*;
import cn.dtransfer.common.constant.Constants;
import cn.dtransfer.common.core.text.Convert;
import cn.dtransfer.common.enums.BillType;
import cn.dtransfer.common.enums.MoneyType;
import cn.dtransfer.common.enums.PaymentCycle;
import cn.dtransfer.common.exception.base.BaseException;
import cn.dtransfer.common.utils.DateUtils;
import cn.dtransfer.common.utils.RandomUtil;
import cn.dtransfer.common.utils.StringUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 客户合同账单Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-30
 */
@Service
public class CustomerContractBillServiceImpl extends ServiceImpl<CustomerContractBillMapper, CustomerContractBill> implements ICustomerContractBillService {
    @Autowired
    private CustomerContractBillMapper customerContractBillMapper;
    @Autowired
    private CustomerContractExpensesMapper customerContractExpensesMapper;

    @Autowired
    private CustomerContractMapper customerContractMapper;



    /**
     * 查询客户合同账单
     *
     * @param id 客户合同账单ID
     * @return 客户合同账单
     */
    @Override
    public CustomerContractBill selectCustomerContractBillById(Long id) {
        return customerContractBillMapper.selectCustomerContractBillById(id);
    }

    /**
     * 查询客户合同账单列表
     *
     * @param customerContractBill 客户合同账单
     * @return 客户合同账单
     */
    @Override
    public List<CustomerContractBill> selectCustomerContractBillList(CustomerContractBill customerContractBill) {
        return customerContractBillMapper.selectCustomerContractBillList(customerContractBill);
    }

    /**
     * 新增客户合同账单
     *
     * @param billFormVO 客户合同账单
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertCustomerContractBill(BillFormVO billFormVO) {

        CustomerContract customerContract = customerContractMapper.selectCustomerContractById(billFormVO.getContractId());
        // 保存账单
        List<CustomerContractBill> customerContractBills = billFormVO.getCustomerContractBills();

        String manageSn = RandomUtil.generate_sn(Constants.BILL_MANAGE);
        if (cn.hutool.core.collection.CollectionUtil.isNotEmpty(customerContractBills)) {
            for (CustomerContractBill customerContractBill : customerContractBills){
                customerContractBill.setCustomerId(customerContract.getCustomerId());
                customerContractBill.setTenantId(customerContract.getTenantId());
                customerContractBill.setParkId(customerContract.getParkId());
                customerContractBill.setSn(RandomUtil.generate_sn(Constants.BILL_PREFIX));
                customerContractBill.setManageSn(manageSn);
                customerContractBill.setStatus(CustomerContractBill.Status.UNRECEIVED);
                customerContractBill.setType(customerContract.getType());
                customerContractBill.setContractId(customerContract.getId());
                customerContractBill.setCreateBy(billFormVO.getCreateBy());
                customerContractBill.setCreateTime(DateUtils.getNowDate());
                customerContractBill.setDeposit(customerContractBill.getDeposit() == null ? BigDecimal.ZERO : customerContractBill.getDeposit());
                customerContractBill.setRentOverdue(customerContractBill.getRentOverdue() == null ? BigDecimal.ZERO : customerContractBill.getRentOverdue());
                customerContractBill.setBillType(BillType.GENERAL);
                customerContractBill.setPaymentCycle(billFormVO.getPaymentCycle());
                customerContractBill.setType(CustomerContract.Type.OFFICIAL);
                // 保存总账期
                customerContractBill.setBillDate(
                        DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, billFormVO.getBillStartDate()) + " ~ "
                                + DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, billFormVO.getBillEndDate()));
                int row = customerContractBillMapper.insert(customerContractBill);
                if (row == 0) {
                    throw new BaseException("新增固定账单失败");
                }
            }
        }
        // 保存其他账单
        List<CustomerContractBill> customerContractOtherBills = billFormVO.getCustomerContractOtherBills();
        if (CollectionUtil.isNotEmpty(customerContractOtherBills)) {
            for (CustomerContractBill customerContractBill : customerContractOtherBills){
                customerContractBill.setCustomerId(customerContract.getCustomerId());
                customerContractBill.setTenantId(customerContract.getTenantId());
                customerContractBill.setParkId(customerContract.getParkId());
                customerContractBill.setSn(RandomUtil.generate_sn(Constants.BILL_PREFIX));
                customerContractBill.setManageSn(manageSn);
                customerContractBill.setStatus(CustomerContractBill.Status.UNRECEIVED);
                customerContractBill.setType(customerContract.getType());
                customerContractBill.setContractId(customerContract.getId());
                customerContractBill.setCreateBy(billFormVO.getCreateBy());
                customerContractBill.setCreateTime(DateUtils.getNowDate());
                customerContractBill.setBillType(BillType.OTHER);
                customerContractBill.setPaymentCycle(PaymentCycle.OTHER);
                customerContractBill.setType(CustomerContract.Type.OFFICIAL);
                customerContractBill.setBillDate(
                        DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, customerContractBill.getBillStartDate()) + " ~ "
                                + DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, customerContractBill.getBillEndDate()));
                int row = customerContractBillMapper.insert(customerContractBill);
                if (row == 0) {
                    throw new BaseException("新增其他账单失败");
                }
            }
        }
        return 1;
    }

    /**
     * 批量保存账单
     * @param billFormVO 客户合同账单列表
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchInsertCustomerContractBill(BillFormVO billFormVO) {
        CustomerContract customerContract = customerContractMapper.selectCustomerContractById(billFormVO.getContractId());
        if (customerContract == null) {
            throw new IllegalArgumentException("合同不能为空!");
        }
        if (billFormVO != null && !CollectionUtils.isEmpty(billFormVO.getCustomerContractBillFormVOList())) {
            for (CustomerContractBillFormVO customerContractBillFormVO : billFormVO.getCustomerContractBillFormVOList()) {
                CustomerContractBill customerContractBill = new CustomerContractBill();
                customerContractBill.setContractId(billFormVO.getContractId());
                customerContractBill.setBillType(BillType.OTHER);
                customerContractBill.setBillDate(
                        DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, customerContractBillFormVO.getBillStartDate()) + " ~ "
                                + DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, customerContractBillFormVO.getBillEndDate()));
                customerContractBill.setCreateTime(DateUtils.getNowDate());
                customerContractBill.setBillStartDate(customerContractBillFormVO.getBillStartDate());
                customerContractBill.setBillEndDate(customerContractBillFormVO.getBillEndDate());
                customerContractBill.setName(customerContractBillFormVO.getName());
                if (customerContractBillFormVO.getMoneyType().equals(MoneyType.IN)) {
                    customerContractBill.setOtherFee(customerContractBillFormVO.getOtherFee());
                }else {
                    customerContractBill.setRefundFee(customerContractBillFormVO.getOtherFee());
                }
                customerContractBill.setCustomerId(customerContract.getCustomerId());
                customerContractBill.setSn(RandomUtil.generate_sn(Constants.BILL_PREFIX));
                customerContractBill.setManageSn(customerContract.getManageSn());
                customerContractBill.setCreateBy(billFormVO.getCreateBy());
                customerContractBill.setReceiveRentDate(customerContractBillFormVO.getReceiveRentDate());
                customerContractBill.setStatus(CustomerContractBill.Status.UNRECEIVED);
                customerContractBill.setType(CustomerContract.Type.OFFICIAL);
                customerContractBillMapper.insert(customerContractBill);
            }
        }
        return 1;
    }


    /**
     * 修改客户合同账单
     *
     * @param customerContractBill 客户合同账单
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateCustomerContractBill(CustomerContractBill customerContractBill) {
        return customerContractBillMapper.updateById(customerContractBill);
    }

    /**
     * 删除客户合同账单对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCustomerContractBillByIds(String ids) {
        long[] arr = StrUtil.splitToLong(ids, ",");
        List<Long> list = new ArrayList<>();
        for (long l : arr) {
            list.add(l);
        }
        return customerContractBillMapper.deleteBatchIds(list);
    }


    /**
     * 批量确认客户合同账单
     *
     * @param ids 需要确认的数据ID
     * @return 结果
     */
    @Override
    public int batchConfirmReceiveAmount(String ids) {
        return customerContractBillMapper.batchConfirmReceiveAmount(Convert.toStrArray(ids));
    }

    /**
     * 批量取消客户合同账单
     *
     * @param ids 需要取消确认的数据ID
     * @return 结果
     */
    @Override
    public int batchCancelReceiveAmount(String ids) {
        return customerContractBillMapper.batchConfirmReceiveAmount(Convert.toStrArray(ids));
    }


    /**
     * 导出客户合同账单列表
     *
     * @param customerContractBill 客户合同账单
     * @return 客户合同账单
     */
    @Override
    public List<CustomerContractBill> selectCustomerContractBillExcelList(CustomerContractBill customerContractBill)
    {
        return customerContractBillMapper.selectCustomerContractBillExcelList(customerContractBill);
    }

    /**
     * 根据账单更新水电费
     *
     * @param sn 账单号
     * @param powerFee 电费
     * @param waterFee 水费
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePowerWaterFeeByBillSn(String sn, String contractSn, BigDecimal powerFee, BigDecimal waterFee, String userName) {
        return customerContractBillMapper.updatePowerWaterFeeBySn(sn, powerFee, waterFee);
    }

    /**
     * 根据单号查询账单
     * @param sn
     * @return
     */
    @Override
    public CustomerContractBill findBySn(String sn) {
        return customerContractBillMapper.selectCustomerContractBillBySn(sn);
    }


    /**
     * 根据账单更新租金和物业管理费
     *
     * @param sn 账单号
     * @param rent 租金
     * @param managementTotalFee 物业管理费
     * @return 结果
     */
    @Override
    public int updateRentByBillSn(String sn, BigDecimal rent, BigDecimal managementTotalFee, BigDecimal receiveWaterFee, BigDecimal receivePowerFee, CustomerContractBill.Status status) {
        return customerContractBillMapper.updateRentBySn(sn, rent, managementTotalFee, receiveWaterFee, receivePowerFee, status);
    }



    /**
     * 根据账单的租金，物业费，水电费等纬度进行统计显示
     * @param type
     * @return
     */
    @Override
    public Map analysisContractBill(String type) {
        Map<String, Object> result = Maps.newHashMap();
        Map<String, Object> queryResult = null;
        result.put("totalFee",BigDecimal.ZERO);
        result.put("receive",BigDecimal.ZERO);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int month = calendar.get(Calendar.MONTH)+1;
        String queryMonth = calendar.get(Calendar.YEAR) + "-" + (month < 10 ? "0" : "") + month;
        // 统计账单
        String total = "TOTAL";
        // 统计租金
        String rent = "RENT";
        // 统计物业费
        String management = "MANAGEMENT";
        // 统计水电费
        String pw = "PW";
        if (type.equals(total)) {
            queryResult =  customerContractBillMapper.selectSumCustomerContractBill(queryMonth);
        }else if(type.equals(rent)) {
            queryResult = customerContractBillMapper.selectSumRentCustomerContractBill(queryMonth);
        }else if(type.equals(management)){
            queryResult = customerContractBillMapper.selectSumManagementCustomerContractBill(queryMonth);
        }else if(type.equals(pw)){
            queryResult = customerContractBillMapper.selectSumPWCustomerContractBill(queryMonth);
        }
        if (queryResult != null) {
            result = queryResult;
        }
        result.put("unReceive",new BigDecimal(result.get("totalFee").toString()).subtract(new BigDecimal(result.get("receive").toString())));
        return result;
    }

    /**
     * 统计当年每月应收款,已收款，未收款
     * @return
     */
    @Override
    public Map analysisYearContractBill() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String queryYear = calendar.get(Calendar.YEAR) +"";
        // 应收
        List<ChartVO> totalList = Lists.newArrayList();

        // 已收
        List<ChartVO> receiveList = Lists.newArrayList();

        // 未收
        List<ChartVO> unReceiveList = Lists.newArrayList();

        // 构造柱状图的12个月数据项
        Integer TOTAL_MONTH = 12;
        for (int i = 1; i <= TOTAL_MONTH; i++){
            // 每月应收
            ChartVO total = new ChartVO();
            total.setX((i < 10 ? "0" : "") + i + "月");
            total.setY(BigDecimal.ZERO);
            totalList.add(total);

            // 每月已收
            ChartVO receive = new ChartVO();
            receive.setX((i < 10 ? "0" : "") + i + "月");
            receive.setY(BigDecimal.ZERO);
            receiveList.add(receive);

            // 每月未收
            ChartVO unReceive = new ChartVO();
            unReceive.setX((i < 10 ? "0" : "") + i + "月");
            unReceive.setY(BigDecimal.ZERO);
            unReceiveList.add(unReceive);
        }
        List<CustomerContractBillBarChartVO> customerContractBillBarChartVOS =  customerContractBillMapper.selectSumCustomerContractBillByMonth(queryYear);
        // 将返回有月份数据的重新设置在构建柱状图集合
        for(CustomerContractBillBarChartVO customerContractBillBarChartVO : customerContractBillBarChartVOS){

            for(ChartVO item : totalList){
                if (item.getX().equals(customerContractBillBarChartVO.getMonthData())) {
                    item.setY(customerContractBillBarChartVO.getTotalFee());
                }
            }

            for(ChartVO item : receiveList){
                if (item.getX().equals(customerContractBillBarChartVO.getMonthData())) {
                    item.setY(customerContractBillBarChartVO.getReceiveFee());
                }
            }

            for(ChartVO item : unReceiveList){
                if (item.getX().equals(customerContractBillBarChartVO.getMonthData())) {
                    BigDecimal unReceiveFee = customerContractBillBarChartVO.getTotalFee().subtract(customerContractBillBarChartVO.getReceiveFee());
                    item.setY(unReceiveFee);
                }
            }
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put("totalList",totalList);
        result.put("receiveList", receiveList);
        result.put("unReceiveList", unReceiveList);
        return result;
    }


    /**
     * 企业账单排行榜
     */
    @Override
    public List<CustomerContractBillRankListVO> rankList(String dateType) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String queryDate = calendar.get(Calendar.YEAR) +"";
        String MONTH_RANK = "1";
        // 如果是按月统计
        if (StringUtils.isEmpty(dateType) || MONTH_RANK.equals(dateType)) {
            queryDate = queryDate + "-" + (calendar.get(Calendar.MONTH)+1);
        }
        List<CustomerContractBillRankListVO> customerContractBillRankListVOS =  customerContractBillMapper.selectCustomerRankList(queryDate);
        for (CustomerContractBillRankListVO customerContractBillRankListVO : customerContractBillRankListVOS){
            customerContractBillRankListVO.setTotal(customerContractBillRankListVO.getTotal() == null ? BigDecimal.ZERO :
                    customerContractBillRankListVO.getTotal());
        }
        return customerContractBillRankListVOS;
    }

}
