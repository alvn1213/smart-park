package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.listener.ImportRentListener;
import cn.dtransfer.admin.service.ICustomerContractBillService;
import cn.dtransfer.admin.service.ICustomerContractService;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.EasyExcel;
import com.google.common.collect.Maps;
import cn.dtransfer.admin.domain.CustomerContract;
import cn.dtransfer.admin.domain.CustomerContractBill;
import cn.dtransfer.admin.domain.Room;
import cn.dtransfer.admin.listener.ImportPowerWaterFeeListener;
import cn.dtransfer.admin.utils.ExcelView;
import cn.dtransfer.admin.vo.BillFormVO;
import cn.dtransfer.admin.vo.BillInitQueryFormVO;
import cn.dtransfer.admin.vo.ImportPowerWaterFeeVO;
import cn.dtransfer.admin.vo.ImportRentVO;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.enums.PaymentCycle;
import cn.dtransfer.common.utils.DateUtils;
import cn.dtransfer.common.utils.GuavaCacheUtil;
import cn.dtransfer.common.utils.StringUtils;
import cn.dtransfer.system.service.ICurrentUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.BooleanUtils;
import org.jxls.common.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 客户合同账单 提供者
 *
 * @author dtransfer
 * @date 2024-11-26
 */
@Slf4j
@RestController
@RequestMapping("admin/contract/bill")
public class CustomerContractBillController extends BaseController {

    @Autowired
    private ICustomerContractBillService customerContractBillService;

    @Autowired
    private ICurrentUserService currentUserService;

    @Autowired
    private ICustomerContractService customerContractService;


    /**
     * app的密钥值
     */
    @Value("${dfs.path}")
    private String path;


    /**
     * 查询客户合同账单
     */
    @RequiresPermissions("admin:bill:view")
    @GetMapping("get")
    public CustomerContractBill get(Long billId) {
        CustomerContractBill customerContractBill = customerContractBillService.selectCustomerContractBillById(billId);
        return customerContractBill;
    }

    /**
     * 确认客户合同账单查看
     */
    @RequiresPermissions("admin:bill:view")
    @GetMapping("confirm_detail")
    public R confirm_detail(Long billId) {
        CustomerContractBill customerContractBill = customerContractBillService.selectCustomerContractBillById(billId);
        Map<String, Object> result = Maps.newHashMap();
        result.put("name", customerContractBill.getName());
        result.put("billId", customerContractBill.getId());
        // 应收租金
        result.put("rent", customerContractBill.getRent());
        // 已收租金
        result.put("receiveRent", customerContractBill.getReceiveRent());
        // 待收租金
        result.put("waitRent", customerContractBill.getRent().subtract(customerContractBill.getReceiveRent()));
        // 应收物业管理费
        result.put("managementTotalFee", customerContractBill.getManagementTotalFee());
        // 已收物业管理费
        result.put("receiveManagementTotalFee", customerContractBill.getReceiveManagementTotalFee());
        // 待收物业管理费
        result.put("waitManagementTotalFee", customerContractBill.getManagementTotalFee().subtract(customerContractBill.getReceiveManagementTotalFee()));
        // 应收电费
        result.put("powerFee", customerContractBill.getPowerFee());
        // 已收电费
        result.put("receivePowerFee", customerContractBill.getReceivePowerFee());
        // 待收电费
        result.put("waitPowerFee", customerContractBill.getPowerFee().subtract(customerContractBill.getReceivePowerFee()));
        // 应收水费
        result.put("waterFee", customerContractBill.getWaterFee());
        // 已收水费
        result.put("receiveWaterFee", customerContractBill.getReceiveWaterFee());
        // 待收水费
        result.put("waitWaterFee", customerContractBill.getWaterFee().subtract(customerContractBill.getReceiveWaterFee()));
        // 应收押金
        result.put("deposit", customerContractBill.getDeposit());

        // 应收其他费用
        result.put("otherFee", customerContractBill.getOtherFee());

        // 已收其他费用
        result.put("receiveOtherFee", customerContractBill.getReceiveOtherFee());


        // 应退费用
        result.put("refundFee", customerContractBill.getRefundFee());
        // 合计应收
        result.put("totalFee", getTotalFee(customerContractBill));
        // 备注
        result.put("remark", customerContractBill.getRemark());

        return R.ok(result);
    }

    /**
     * 应收总金额
     */
    private BigDecimal getTotalFee(CustomerContractBill customerContractBill) {
        return customerContractBill.getRent()
                .add(customerContractBill.getManagementTotalFee())
                .add(customerContractBill.getPowerFee())
                .add(customerContractBill.getWaterFee())
                .add(customerContractBill.getDeposit())
                .add(customerContractBill.getOtherFee())
                .add(customerContractBill.getRefundFee());
    }

    /**
     * 查询客户合同账单列表
     */
    @RequiresPermissions("admin:bill:list")
    @PostMapping("list")
    public R list(@RequestBody CustomerContractBill customerContractBill) {
        startPage();
        if (customerContractBill == null) {
            customerContractBill = new CustomerContractBill();
            customerContractBill.setType(CustomerContract.Type.OFFICIAL);
        }

        List<CustomerContractBill> customerContractBills = customerContractBillService.selectCustomerContractBillList(customerContractBill);

        for (CustomerContractBill item : customerContractBills) {
            // 是否逾期
            setIsOverdue(item);
            item.setTotalBill(getTotalFee(item));
            item.setReceiveTotalBill(getReceiveTotalFee(item));
            item.setWaitPay(item.getTotalBill().subtract(item.getReceiveTotalBill()));
        }
        return result(customerContractBills);
    }


    /**
     * 新增保存客户合同账单
     */
    @RequiresPermissions("admin:bill:add")
    @PostMapping("save")
    public R addSave(@Valid @RequestBody BillFormVO BillFormVO) {
        BillFormVO.setCreateBy(getLoginName());
        return toAjax(customerContractBillService.insertCustomerContractBill(BillFormVO));
    }


    /**
     * 新增保存客户合同账单
     */
    @RequiresPermissions("admin:bill:add")
    @PostMapping("batchSave")
    public R batchSave(@RequestBody BillFormVO billFormVO) {
        billFormVO.setCreateBy(getLoginName());
        return toAjax(customerContractBillService.batchInsertCustomerContractBill(billFormVO));
    }

    /**
     * 修改保存客户合同账单
     */
    @RequiresPermissions("admin:bill:edit")
    @PostMapping("update")
    public R editSave(@RequestBody CustomerContractBill customerContractBill) {
        customerContractBill.setUpdateBy(getLoginName());
        return toAjax(customerContractBillService.updateCustomerContractBill(customerContractBill));
    }

    /**
     * 确认收到租金
     */
    @RequiresPermissions("admin:bill:edit")
    @PostMapping("confirm_receive_amount")
    public R confirm_receive_amount(Long billId, BigDecimal receiveRent, BigDecimal receiveManagementTotalFee, BigDecimal receivePowerFee, BigDecimal receiveWaterFee, String remark) {
        CustomerContractBill customerContractBill = customerContractBillService.selectCustomerContractBillById(billId);
        if (receiveRent == null) {
            return R.error("收到租金不能为空！");
        }
        if (receiveManagementTotalFee == null) {
            return R.error("收到物业管理费不能为空！");
        }
        if (customerContractBill == null) {
            return R.error("账单不存在！");
        }
        customerContractBill.setReceiveRent(customerContractBill.getReceiveRent().add(receiveRent));
        customerContractBill.setReceiveManagementTotalFee(customerContractBill.getReceiveManagementTotalFee().add(receiveManagementTotalFee));
        customerContractBill.setReceivePowerFee(customerContractBill.getReceivePowerFee().add(receivePowerFee == null ? BigDecimal.ZERO : receivePowerFee));
        customerContractBill.setReceiveWaterFee(customerContractBill.getWaterFee().add(receiveWaterFee == null ? BigDecimal.ZERO : receiveWaterFee));
        customerContractBill.setRemark(remark);
        customerContractBill.setUpdateBy(getLoginName());
        BigDecimal receiveTotalFee = getReceiveTotalFee(customerContractBill);
        if (receiveTotalFee.equals(BigDecimal.ZERO)) {
            customerContractBill.setStatus(CustomerContractBill.Status.UNRECEIVED);
        } else if (receiveTotalFee.compareTo(getTotalFee(customerContractBill)) == -1) {
            customerContractBill.setStatus(CustomerContractBill.Status.PART);
        } else {
            customerContractBill.setStatus(CustomerContractBill.Status.RECEIVED);
        }
        return toAjax(customerContractBillService.updateCustomerContractBill(customerContractBill));
    }


    /**
     * 确认收到其他费用
     */
    @RequiresPermissions("admin:bill:edit")
    @PostMapping("confirm_receive_other_amount")
    public R confirm_receive_other_amount(Long billId, BigDecimal otherFee, BigDecimal receiveOtherFee, String remark) {
        if (receiveOtherFee == null) {
            return R.error("收到金额不能为空！");
        }
        CustomerContractBill customerContractBill = customerContractBillService.selectCustomerContractBillById(billId);
        if (customerContractBill == null) {
            return R.error("账单不存在！");
        }
        customerContractBill.setReceiveOtherFee(receiveOtherFee.add(customerContractBill.getReceiveOtherFee()));
        customerContractBill.setRemark(remark);
        customerContractBill.setUpdateBy(getLoginName());
        BigDecimal waitPay = otherFee.subtract(customerContractBill.getReceiveOtherFee());
        if (waitPay.equals(BigDecimal.ZERO)) {
            customerContractBill.setStatus(CustomerContractBill.Status.UNRECEIVED);
        } else if (receiveOtherFee.compareTo(waitPay) == -1) {
            customerContractBill.setStatus(CustomerContractBill.Status.PART);
        } else if(receiveOtherFee.equals(waitPay)){
            customerContractBill.setStatus(CustomerContractBill.Status.RECEIVED);
        }
        return toAjax(customerContractBillService.updateCustomerContractBill(customerContractBill));
    }

    /**
     * 已收金额
     */
    private BigDecimal getReceiveTotalFee(CustomerContractBill customerContractBill) {
        // 已收租金含押金
        return customerContractBill.getReceiveRent()
                .add(customerContractBill.getReceiveManagementTotalFee())
                .add(customerContractBill.getReceivePowerFee()
                        .add(customerContractBill.getReceiveWaterFee())
                        .add(customerContractBill.getReceiveOtherFee())
                        .add(customerContractBill.getRefundFee()));
    }


    /**
     * 批量确认收到租金
     */
    @RequiresPermissions("admin:bill:edit")
    @PostMapping("batch_confirm_receive_amount")
    public R batch_confirm_receive_amount(String billIds) {
        if (StringUtils.isEmpty(billIds)) {
            return R.error("请选择行!");
        }
        return toAjax(customerContractBillService.batchConfirmReceiveAmount(billIds));
    }

    /**
     * 批量取消确认收到租金
     */
    @RequiresPermissions("admin:bill:edit")
    @PostMapping("batch_cancel_receive_amount")
    public R batch_cancel_receive_amount(String billIds) {
        return toAjax(customerContractBillService.batchCancelReceiveAmount(billIds));
    }

    /**
     * 取消确认
     */
    @RequiresPermissions("admin:bill:edit")
    @PostMapping("cancelConfirm")
    public R cancelConfirm(Long billId) {
        CustomerContractBill customerContractBill = customerContractBillService.selectCustomerContractBillById(billId);
        if (customerContractBill == null) {
            return R.error("账单不存在！");
        }
        customerContractBill.setRemark("--");
        customerContractBill.setReceiveRent(BigDecimal.ZERO);
        customerContractBill.setReceiveManagementTotalFee(BigDecimal.ZERO);
        customerContractBill.setReceivePowerFee(BigDecimal.ZERO);
        customerContractBill.setReceiveWaterFee(BigDecimal.ZERO);
        customerContractBill.setUpdateBy(getLoginName());
        customerContractBill.setStatus(CustomerContractBill.Status.UNRECEIVED);
        return toAjax(customerContractBillService.updateCustomerContractBill(customerContractBill));
    }

    /**
     * 删除客户合同账单
     */
    @RequiresPermissions("admin:bill:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(customerContractBillService.deleteCustomerContractBillByIds(ids));
    }

    /**
     * @param deposit           押金
     * @param rentIncreaseMonth 月租金
     * @param rentIncreaseDay   日租金
     * @param receiveRentDay    收租固定日
     * @param startDate         合同开始时间
     * @param endDate           合同结束时间
     * @param updateDate        调整账单时间
     * @return
     */
    @RequiresPermissions("admin:bill:edit")
    @GetMapping("init_bill")
    public R init_bill(BigDecimal deposit, BigDecimal rentIncreaseMonth, BigDecimal rentIncreaseDay, Integer receiveRentDay,
                       BigDecimal managementFeeIncreaseMonth, BigDecimal managementFeeIncreaseDay, Integer paymentPeriod,
                       Date startDate, Date endDate, Date updateDate) {
        //paymentPeriod = PaymentCycle.YEAR.getValue();
        // 分段账单
        List<Map> times = Lists.newArrayList();
        if (updateDate == null) {
            // 按合同开始时间和结束时间划分每个月的账单日期
            DateUtils.getDateBetween(startDate, endDate, times, paymentPeriod);
        } else {
            // 编辑账单日期
            DateUtils.getDateBetween(startDate, updateDate, times, paymentPeriod);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(updateDate);
            calendar.add(Calendar.DATE, 1);
            DateUtils.getDateBetween(calendar.getTime(), endDate, times, paymentPeriod);
        }

        return R.data(create_bill(deposit, rentIncreaseMonth, rentIncreaseDay, managementFeeIncreaseMonth, managementFeeIncreaseDay, receiveRentDay, times, endDate, paymentPeriod));
    }


    @RequiresPermissions("admin:bill:edit")
    @PostMapping("init_new_bill")
    public R init_new_bill(@Valid BillInitQueryFormVO billInitQueryFormVO) {
        CustomerContract customerContract = customerContractService.selectCustomerContractById(billInitQueryFormVO.getContractId());
        // 分段账单
        List<Map> times = Lists.newArrayList();
        DateUtils.getDateBetween(billInitQueryFormVO.getBillStartDate(), billInitQueryFormVO.getBillEndDate(), times, billInitQueryFormVO.getPaymentCycle().getValue());

        return R.data(create_new_bill(customerContract, times, billInitQueryFormVO.getPaymentCycle()));
    }

    /**
     * 初始化创建账单
     *
     * @param times
     * @return
     */
    private List<CustomerContractBill> create_bill(BigDecimal deposit, BigDecimal rentIncreaseMonth, BigDecimal rentIncreaseDay,
                                                   BigDecimal managementFeeIncreaseMonth, BigDecimal managementFeeIncreaseDay,
                                                   Integer receiveRentDay, List<Map> times, Date overDate, Integer paymentPeriod) {
        List<CustomerContractBill> customerContractBills = Lists.newArrayList();
        for (Map<String, Object> item : times) {
            Date startDate = DateUtils.parseDate(item.get("startDate").toString());
            Date endDate = DateUtils.parseDate(item.get("endDate").toString());
            if (startDate.getTime() <= endDate.getTime()) {
                CustomerContractBill customerContractBill = new CustomerContractBill();
                if ((Boolean) item.get("isDeposit")) {
                    customerContractBill.setDeposit(deposit);
                }
                customerContractBill.setBillStartDate(startDate);
                customerContractBill.setBillEndDate(endDate);
                customerContractBill.setBillDate(item.get("startDate").toString() + " ~ " + item.get("endDate").toString());
                // 获取账单的上个月20号作为账单收款日
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(DateUtils.parseDate(item.get("startDate")));
                customerContractBill.setReceiveRentDate(DateUtils.parseDate(calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-"
                        + (null == receiveRentDay ? 20 : receiveRentDay)));
                if ("M".equals(item.get("type").toString())) {
                    if (paymentPeriod != null) {
                        if (paymentPeriod.equals(PaymentCycle.YEAR.getValue())) {
                            customerContractBill.setRent(rentIncreaseMonth.multiply(BigDecimal.valueOf(12)));
                            customerContractBill.setManagementTotalFee(managementFeeIncreaseMonth.multiply(BigDecimal.valueOf(12)));
                        } else if (paymentPeriod.equals(PaymentCycle.MONTH.getValue())) {
                            customerContractBill.setRent(rentIncreaseMonth);
                            customerContractBill.setManagementTotalFee(managementFeeIncreaseMonth);
                        } else if (paymentPeriod.equals(PaymentCycle.HALF_YEAR.getValue())) {
                            customerContractBill.setRent(rentIncreaseMonth.multiply(BigDecimal.valueOf(6)));
                            customerContractBill.setManagementTotalFee(managementFeeIncreaseMonth.multiply(BigDecimal.valueOf(6)));
                        } else if (paymentPeriod.equals(PaymentCycle.SEASON.getValue())) {
                            customerContractBill.setRent(rentIncreaseMonth.multiply(BigDecimal.valueOf(3)));
                            customerContractBill.setManagementTotalFee(managementFeeIncreaseMonth.multiply(BigDecimal.valueOf(3)));
                        }
                    } else {
                        // 默认月付
                        customerContractBill.setRent(rentIncreaseMonth);
                        customerContractBill.setManagementTotalFee(managementFeeIncreaseMonth);
                    }
                } else if ("D".equals(item.get("type").toString())) {
                    // 如果最后租期不满一个月则按日统计租金
                    Calendar calendarStart = Calendar.getInstance();
                    calendarStart.setTime(DateUtils.parseDate(item.get("startDate")));
                    Calendar calendarEnd = Calendar.getInstance();
                    calendarEnd.setTime(DateUtils.parseDate(item.get("endDate")));
                    long day = (calendarEnd.getTimeInMillis() - calendarStart.getTimeInMillis()) / (1000 * 3600 * 24);
                    // 不足一天按一天计算
                    customerContractBill.setRent(rentIncreaseDay.multiply(BigDecimal.valueOf(day + 1)));
                    customerContractBill.setManagementTotalFee(managementFeeIncreaseDay.multiply(BigDecimal.valueOf(day + 1)));
                }
                //默认未收
                customerContractBill.setStatus(CustomerContractBill.Status.UNRECEIVED);
                customerContractBills.add(customerContractBill);
            }
        }
        return customerContractBills;
    }


    /**
     * 初始化创建新账单
     *
     * @param times
     * @return
     */
    private List<CustomerContractBill> create_new_bill(CustomerContract customerContract, List<Map> times, PaymentCycle paymentCycle) {
        List<CustomerContractBill> customerContractBills = Lists.newArrayList();
        for (Map<String, Object> item : times) {
            Date startDate = DateUtils.parseDate(item.get("startDate").toString());
            Date endDate = DateUtils.parseDate(item.get("endDate").toString());
            if (startDate.getTime() <= endDate.getTime()) {
                CustomerContractBill customerContractBill = new CustomerContractBill();
                if ((Boolean) item.get("isDeposit")) {
                    customerContractBill.setDeposit(customerContract.getDeposit());
                }
                customerContractBill.setBillStartDate(startDate);
                customerContractBill.setBillEndDate(endDate);
                customerContractBill.setBillDate(item.get("startDate").toString() + " ~ " + item.get("endDate").toString());
                // 获取账单的上个月20号作为账单收款日
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(DateUtils.parseDate(item.get("startDate")));
                customerContractBill.setReceiveRentDate(DateUtils.parseDate(calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-"
                        + (null == customerContract.getReceiveRentDay() ? 20 : customerContract.getReceiveRentDay())));
                if ("M".equals(item.get("type").toString())) {
                    if(paymentCycle != null){
                        if (paymentCycle.equals(PaymentCycle.YEAR)) {
                            customerContractBill.setRent(customerContract.getRentIncreaseMonth().multiply(BigDecimal.valueOf(12)));
                            customerContractBill.setManagementTotalFee(customerContract.getManagementFeeIncreaseMonth().multiply(BigDecimal.valueOf(12)));
                        }
                        else if(paymentCycle.equals(PaymentCycle.MONTH)){
                            customerContractBill.setRent(customerContract.getRentIncreaseMonth());
                            customerContractBill.setManagementTotalFee(customerContract.getManagementFeeIncreaseMonth());
                        }
                        else if(paymentCycle.equals(PaymentCycle.HALF_YEAR)){
                            customerContractBill.setRent(customerContract.getRentIncreaseMonth().multiply(BigDecimal.valueOf(6)));
                            customerContractBill.setManagementTotalFee(customerContract.getManagementFeeIncreaseMonth().multiply(BigDecimal.valueOf(6)));
                        }
                        else if(paymentCycle.equals(PaymentCycle.SEASON)){
                            customerContractBill.setRent(customerContract.getRentIncreaseMonth().multiply(BigDecimal.valueOf(3)));
                            customerContractBill.setManagementTotalFee(customerContract.getManagementFeeIncreaseMonth().multiply(BigDecimal.valueOf(3)));
                        }
                    }else{
                        // 默认月付
                        customerContractBill.setRent(customerContract.getRentIncreaseMonth());
                        customerContractBill.setManagementTotalFee(customerContract.getManagementFeeIncreaseMonth());
                    }
                } else if ("D".equals(item.get("type").toString())) {
                    // 如果最后租期不满一个月则按日统计租金
                    Calendar calendarStart = Calendar.getInstance();
                    calendarStart.setTime(DateUtils.parseDate(item.get("startDate")));
                    Calendar calendarEnd = Calendar.getInstance();
                    calendarEnd.setTime(DateUtils.parseDate(item.get("endDate")));
                    long day = (calendarEnd.getTimeInMillis() - calendarStart.getTimeInMillis()) / (1000 * 3600 * 24);
                    // 不足一天按一天计算
                    customerContractBill.setRent(customerContract.getRentIncreaseDay().multiply(BigDecimal.valueOf(day + 1)));
                    customerContractBill.setManagementTotalFee(customerContract.getManagementFeeIncreaseDay().multiply(BigDecimal.valueOf(day + 1)));
                }
                //默认未收
                customerContractBill.setStatus(CustomerContractBill.Status.UNRECEIVED);
                customerContractBills.add(customerContractBill);
            }
        }
        return customerContractBills;
    }

    /**
     * 根据月份导出账单报表功能
     *
     * @param customerContractBill
     */
    @RequiresPermissions("admin:bill:view")
    @GetMapping("/export")
    public void export(CustomerContractBill customerContractBill) {
        Context context = new Context();
        List<CustomerContractBill> customerContractBills = customerContractBillService.selectCustomerContractBillExcelList(customerContractBill);
        int i = 1;
        for (CustomerContractBill item : customerContractBills) {
            item.setId(Long.valueOf(i));
            // 是否逾期
            setIsOverdue(item);
            i++;
        }
        // 合计
        CustomerContractBill customerContractBillVOTotal = new CustomerContractBill();
        customerContractBillVOTotal.setName("合计(单位:元)");
        List<Room> totalRooms = Lists.newArrayList();
        Room room = new Room();
        BigDecimal totalRentArea = BigDecimal.ZERO;
        BigDecimal totalArea = BigDecimal.ZERO;
        BigDecimal totalCommonArea = BigDecimal.ZERO;
        BigDecimal totalRent = BigDecimal.ZERO;
        BigDecimal receiveTotalRent = BigDecimal.ZERO;
        BigDecimal totalPowerFee = BigDecimal.ZERO;
        BigDecimal receiveTotalPowerFee = BigDecimal.ZERO;
        BigDecimal totalWaterFee = BigDecimal.ZERO;
        BigDecimal receiveTotalWaterFee = BigDecimal.ZERO;
        BigDecimal totalOtherFee = BigDecimal.ZERO;
        BigDecimal totalRefundFee = BigDecimal.ZERO;
        BigDecimal totalManagementFee = BigDecimal.ZERO;
        BigDecimal receiveTotalManagementFee = BigDecimal.ZERO;
        BigDecimal allTotalFee = BigDecimal.ZERO;
        BigDecimal allTotalWaitPay = BigDecimal.ZERO;
        BigDecimal allReceiveTotalFee = BigDecimal.ZERO;

        customerContractBillVOTotal.setRooms(totalRooms);
        if (CollectionUtil.isNotEmpty(customerContractBills)) {
            for (CustomerContractBill item : customerContractBills) {
                item.setName(item.getCustomerContract().getSn());
                item.setInvoiceType("0".equals(item.getInvoiceType()) ? "普通发票" : "专用发票");

                item.setTotalBill(getTotalFee(item));
                item.setReceiveTotalBill(getReceiveTotalFee(item));
                item.setWaitPay(item.getTotalBill().subtract(item.getReceiveTotalBill()));

                item.setTotalRentMonth(DateUtils.getDifMonth(item.getCustomerContract().getStartDate(), item.getCustomerContract().getEndDate()));
                item.setRentDate(DateUtils.dateTime(item.getCustomerContract().getStartDate()) + " - " + DateUtils.dateTime(item.getCustomerContract().getEndDate()));
                for (Room r : item.getRooms()) {
                    totalRentArea = totalRentArea.add(r.getRentArea() == null ? BigDecimal.ZERO : r.getRentArea());
                    totalArea = totalArea.add(r.getArea() == null ? BigDecimal.ZERO : r.getArea());
                    totalCommonArea = totalCommonArea.add(r.getCommonArea() == null ? BigDecimal.ZERO : r.getCommonArea());
                }
                totalRent = totalRent.add(item.getRent());
                receiveTotalRent = receiveTotalRent.add(item.getReceiveRent());
                totalPowerFee = totalPowerFee.add(item.getPowerFee());
                receiveTotalPowerFee = receiveTotalPowerFee.add(item.getReceivePowerFee());
                totalWaterFee = totalWaterFee.add(item.getWaterFee());
                receiveTotalWaterFee = receiveTotalWaterFee.add(item.getReceiveWaterFee());
                totalOtherFee = totalOtherFee.add(item.getOtherFee());
                totalRefundFee = totalRefundFee.add(item.getRefundFee());
                totalManagementFee = totalManagementFee.add(item.getManagementTotalFee());
                receiveTotalManagementFee = receiveTotalManagementFee.add(item.getReceiveManagementTotalFee());
                allTotalWaitPay = allTotalWaitPay.add(item.getWaitPay());
                allTotalFee = allTotalFee.add(item.getTotalBill());
                allReceiveTotalFee = allReceiveTotalFee.add(item.getReceiveTotalBill());
            }
        }
        room.setRentArea(totalRentArea);
        room.setArea(totalArea);
        room.setCommonArea(totalCommonArea);
        totalRooms.add(room);
        customerContractBillVOTotal.setRent(totalRent);
        customerContractBillVOTotal.setReceiveRent(receiveTotalRent);
        customerContractBillVOTotal.setPowerFee(totalPowerFee);
        customerContractBillVOTotal.setReceivePowerFee(receiveTotalPowerFee);
        customerContractBillVOTotal.setWaterFee(totalWaterFee);
        customerContractBillVOTotal.setReceiveWaterFee(receiveTotalWaterFee);
        customerContractBillVOTotal.setOtherFee(totalOtherFee);
        customerContractBillVOTotal.setRefundFee(totalRefundFee);
        customerContractBillVOTotal.setTotalBill(allTotalFee);
        customerContractBillVOTotal.setManagementTotalFee(totalManagementFee);
        customerContractBillVOTotal.setReceiveManagementTotalFee(receiveTotalManagementFee);
        customerContractBillVOTotal.setWaitPay(allTotalWaitPay);
        customerContractBillVOTotal.setReceiveTotalBill(allReceiveTotalFee);
        customerContractBillVOTotal.setRooms(totalRooms);
        customerContractBills.add(customerContractBillVOTotal);
        context.putVar("customerContractBills", customerContractBills);
        if (StringUtils.isNotEmpty(customerContractBill.getQueryMonth())) {
            context.putVar("date", DateUtils.parseDateToStr("yyyy年MM月", DateUtils.parseDate(customerContractBill.getQueryMonth())));
        }
        new ExcelView("excel/租金报表模版.xls", DateUtils.getNowDate() + "租金报表", context);
    }


    /**
     * 根据月份下载租金模版功能
     *
     * @param customerContractBill
     */
    @RequiresPermissions("admin:bill:view")
    @GetMapping("/export_rent")
    public void export_rent(CustomerContractBill customerContractBill) {
        Context context = new Context();
        List<CustomerContractBill> customerContractBills = customerContractBillService.selectCustomerContractBillExcelList(customerContractBill);
        int i = 1;
        for (CustomerContractBill item : customerContractBills) {
            item.setId(Long.valueOf(i));
            // 是否逾期
            setIsOverdue(item);
            i++;
        }
        if (CollectionUtil.isNotEmpty(customerContractBills)) {
            for (CustomerContractBill item : customerContractBills) {
                item.setName(item.getCustomerContract().getSn());
                item.setInvoiceType("0".equals(item.getInvoiceType()) ? "普通发票" : "专用发票");
                item.setTotalRentMonth(DateUtils.getDifMonth(item.getCustomerContract().getStartDate(), item.getCustomerContract().getEndDate()));
                item.setRentDate(DateUtils.dateTime(item.getCustomerContract().getStartDate()) + " - " + DateUtils.dateTime(item.getCustomerContract().getEndDate()));
            }
        }
        context.putVar("customerContractBills", customerContractBills);
        if (StringUtils.isNotEmpty(customerContractBill.getQueryMonth())) {
            context.putVar("date", DateUtils.parseDateToStr("yyyy年MM月", DateUtils.parseDate(customerContractBill.getQueryMonth())));
        }
        new ExcelView("excel/租金导入模版.xls", DateUtils.getNowDate() + "租金导入模版", context);
    }

    /**
     * 根据月份导出账单水电报表功能 exportPowerWaterFee
     *
     * @param customerContractBill
     */
    @RequiresPermissions("admin:bill:view")
    @GetMapping("/exportPowerWaterFee")
    public void exportPowerWaterFee(CustomerContractBill customerContractBill) {
        Context context = new Context();
        List<CustomerContractBill> customerContractBills = customerContractBillService.selectCustomerContractBillExcelList(customerContractBill);

        int i = 1;
        for (CustomerContractBill item : customerContractBills) {
            item.setId(Long.valueOf(i));
            i++;
        }
        if (CollectionUtil.isNotEmpty(customerContractBills)) {
            for (CustomerContractBill item : customerContractBills) {
                item.setRentDate(DateUtils.dateTime(item.getCustomerContract().getStartDate()) + " - " + DateUtils.dateTime(item.getCustomerContract().getEndDate()));
            }
        }
        context.putVar("customerContractBills", customerContractBills);
        if (StringUtils.isNotEmpty(customerContractBill.getQueryMonth())) {
            context.putVar("date", DateUtils.parseDateToStr("yyyy年MM月", DateUtils.parseDate(customerContractBill.getQueryMonth())));
        }
        new ExcelView("excel/水电费导入模版.xls", DateUtils.getNowDate() + "水电费导入表格", context);
    }

    /**
     * 导入水电费
     *
     * @return
     */
    @RequiresPermissions("admin:bill:edit")
    @PostMapping("/importPowerWaterFee")
    public R importPowerWaterFee(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return R.error("导入数据为空！");
        }
        // 用户名+园区ID+时间戳+模块名字
        String cacheKey = getLoginName() + "_" + currentUserService.getCurrentUser().getTenantId() + "_" + DateUtils.getNowDate().getTime() + "_" + "ImportPowerWaterFee";

        BufferedInputStream bufferedInputStream = getBufferedInputStream(filePath);

        EasyExcel.read(bufferedInputStream, ImportPowerWaterFeeVO.class, new ImportPowerWaterFeeListener(cacheKey)).sheet().headRowNumber(3).doRead();

        List<ImportPowerWaterFeeVO> importPowerWaterFeeVOS = (List<ImportPowerWaterFeeVO>) GuavaCacheUtil.get(cacheKey);
        // 移除key
        GuavaCacheUtil.remove(cacheKey);

        for (ImportPowerWaterFeeVO item : importPowerWaterFeeVOS) {
            customerContractBillService.updatePowerWaterFeeByBillSn(item.getSn(), item.getContractSn(), item.getPowerFee(), item.getWaterFee(), getLoginName());
        }
        return R.ok("导入成功!");
    }

    /**
     * 导入租金
     *
     * @return
     */
    @RequiresPermissions("admin:bill:edit")
    @PostMapping("/import_rent")
    public R import_rent(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return R.error("导入数据为空！");
        }
        // 用户名+园区ID+时间戳+模块名字
        String cacheKey = getLoginName() + "_" + currentUserService.getCurrentUser().getTenantId() + "_" + DateUtils.getNowDate().getTime() + "_" + "ImportRent";

        BufferedInputStream bufferedInputStream = getBufferedInputStream(filePath);

        EasyExcel.read(bufferedInputStream, ImportRentVO.class, new ImportRentListener(cacheKey)).sheet().headRowNumber(3).doRead();

        List<ImportRentVO> importRentVOS = (List<ImportRentVO>) GuavaCacheUtil.get(cacheKey);
        // 移除key
        GuavaCacheUtil.remove(cacheKey);

        for (ImportRentVO item : importRentVOS) {
            CustomerContractBill customerContractBill = customerContractBillService.findBySn(item.getSn());
            if (customerContractBill == null) {
                return R.error("账单号：" + item.getSn() + " - 不存在!");
            }
            BigDecimal receiveTotalFee = getReceiveTotalFee(customerContractBill);
            if (receiveTotalFee.compareTo(BigDecimal.ZERO) == 0) {
                customerContractBill.setStatus(CustomerContractBill.Status.UNRECEIVED);
            } else if (receiveTotalFee.compareTo(getTotalFee(customerContractBill)) == -1) {
                customerContractBill.setStatus(CustomerContractBill.Status.PART);
            } else {
                customerContractBill.setStatus(CustomerContractBill.Status.RECEIVED);
            }
            customerContractBillService.updateRentByBillSn(item.getSn(), item.getReceiveRent(), item.getReceiveManagementTotalFee(), item.getReceiveWaterFee(), item.getReceivePowerFee(), customerContractBill.getStatus());
        }
        return R.ok("导入成功!");
    }

    /**
     * 设置账单是否逾期
     *
     * @param customerContractBill
     */
    private void setIsOverdue(CustomerContractBill customerContractBill) {
        if (CustomerContractBill.Status.UNRECEIVED.equals(customerContractBill.getStatus())
                || CustomerContractBill.Status.PART.equals(customerContractBill.getStatus())) {
            Long nowTimeStamp = DateUtils.parseDate(DateUtils.getDate()).getTime();
            if (customerContractBill.getReceiveRentDate() != null && nowTimeStamp > customerContractBill.getReceiveRentDate().getTime()) {
                customerContractBill.setIsOverdue(BooleanUtils.toInteger(Boolean.TRUE));
            } else {
                customerContractBill.setIsOverdue(BooleanUtils.toInteger(Boolean.FALSE));
            }
        } else {
            customerContractBill.setIsOverdue(BooleanUtils.toInteger(Boolean.FALSE));
        }
    }

    /**
     * @param filePath
     * @return
     * @throws IOException
     */
    private BufferedInputStream getBufferedInputStream(String filePath) {
        BufferedInputStream bf = null;
        try {
            // 统一资源
            FileInputStream fs = new FileInputStream(path + filePath.replace("profile", ""));
            bf = new BufferedInputStream(fs);

        } catch (Exception e) {
            log.error("获取文件失败", e);
        }
        return bf;
    }

}
