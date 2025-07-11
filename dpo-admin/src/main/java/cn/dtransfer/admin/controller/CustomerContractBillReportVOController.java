package cn.dtransfer.admin.controller;


import cn.dtransfer.admin.mapper.CustomerContractBillReportVOMapper;
import cn.dtransfer.admin.utils.ExcelView;
import cn.dtransfer.admin.vo.CustomerContractBillReportVO;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.DateUtils;
import cn.dtransfer.common.utils.StringUtils;
import org.jxls.common.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.math.BigDecimal;
import java.util.List;

/**
 * 客户合同账单报表 提供者
 *
 * @author dtransfer
 * @date 2024-12-15
 */
@RestController
@RequestMapping("admin/contract/bill/report")
public class CustomerContractBillReportVOController extends BaseController {


    @Autowired
    private CustomerContractBillReportVOMapper customerContractBillReportVOMapper;


    /**
     * 查询客户合同账单列表
     */
    @RequiresPermissions("admin:billReport:view")
    @GetMapping("list")
    public R list(String customerName, String queryStartMonth, String queryEndMonth) {
        startPage();
        List<CustomerContractBillReportVO> customerContractBillReportVOS = getReportList(customerName, queryStartMonth, queryEndMonth);
        return result(customerContractBillReportVOS);
    }

    /**
     * 导出账单报表功能
     */
    @RequiresPermissions("admin:billReport:view")
    @GetMapping("/export")
    public void export(String customerName, String queryStartMonth, String queryEndMonth) {
        Context context = new Context();
        CustomerContractBillReportVO allAmount = new CustomerContractBillReportVO();
        allAmount.setName("合计");
        allAmount.setTotalRent(BigDecimal.ZERO);
        allAmount.setReceiveTotalRent(BigDecimal.ZERO);
        allAmount.setManagementTotalFee(BigDecimal.ZERO);
        allAmount.setReceiveManagementTotalFee(BigDecimal.ZERO);
        allAmount.setPowerFee(BigDecimal.ZERO);
        allAmount.setReceivePowerFee(BigDecimal.ZERO);
        allAmount.setWaterFee(BigDecimal.ZERO);
        allAmount.setReceiveWaterFee(BigDecimal.ZERO);
        allAmount.setOtherFee(BigDecimal.ZERO);
        allAmount.setRefundFee(BigDecimal.ZERO);
        allAmount.setTotalBill(BigDecimal.ZERO);
        allAmount.setReceiveTotalBill(BigDecimal.ZERO);
        allAmount.setWaitPay(BigDecimal.ZERO);
        List<CustomerContractBillReportVO> customerContractBillReportVOS = getReportList(customerName, queryStartMonth, queryEndMonth);
        for (CustomerContractBillReportVO customerContractBillReportVO : customerContractBillReportVOS) {
            allAmount.setTotalRent(allAmount.getTotalRent().add(customerContractBillReportVO.getTotalRent()));
            allAmount.setPowerFee(allAmount.getPowerFee().add(customerContractBillReportVO.getPowerFee()));
            allAmount.setWaterFee(allAmount.getWaterFee().add(customerContractBillReportVO.getWaterFee()));
            allAmount.setOtherFee(allAmount.getOtherFee().add(customerContractBillReportVO.getOtherFee()));
            allAmount.setRefundFee(allAmount.getRefundFee().add(customerContractBillReportVO.getRefundFee()));
            allAmount.setTotalBill(allAmount.getTotalBill().add(customerContractBillReportVO.getTotalBill()));
            allAmount.setWaitPay(allAmount.getWaitPay().add(customerContractBillReportVO.getWaitPay()));
        }
        customerContractBillReportVOS.add(allAmount);
        String date = "";
        if (StringUtils.isNotEmpty(queryStartMonth)) {
            date = date + queryStartMonth;
        }
        if (StringUtils.isNotEmpty(queryEndMonth)) {
            if (StringUtils.isNotEmpty(date)) {
                date = date + " ~ " + queryEndMonth;
            } else {
                date = date + queryEndMonth;
            }
        }
        context.putVar("date", date);
        context.putVar("customerContractBillReportVOS", customerContractBillReportVOS);
        new ExcelView("excel/账单报表模版.xls", DateUtils.getNowDate() + "账单报表", context);
    }

    /**
     * 根据客户名称，起止月份进行查询
     *
     * @param customerName
     * @param queryStartMonth
     * @param queryEndMonth
     * @return
     */
    private List<CustomerContractBillReportVO> getReportList(String customerName, String queryStartMonth, String queryEndMonth) {
        if (StringUtils.isNotEmpty(queryStartMonth)) {
            queryStartMonth = queryStartMonth + "-01";
        }
        if (StringUtils.isNotEmpty(queryEndMonth)) {
            queryEndMonth = queryEndMonth + "-01";
        }
        List<CustomerContractBillReportVO> customerContractBillReportVOS = customerContractBillReportVOMapper.selectCustomerContractBillReportVOList(customerName, queryStartMonth, queryEndMonth);
        for (CustomerContractBillReportVO customerContractBillReportVO : customerContractBillReportVOS) {
            customerContractBillReportVO.setName(customerContractBillReportVO.getCustomer() == null ? "" : customerContractBillReportVO.getCustomer().getName());
            customerContractBillReportVO.setTotalBill(customerContractBillReportVO.getTotalRent()
                    .add(customerContractBillReportVO.getManagementTotalFee())
                    .add(customerContractBillReportVO.getPowerFee())
                    .add(customerContractBillReportVO.getWaterFee())
                    .add(customerContractBillReportVO.getDeposit())
                    .add(customerContractBillReportVO.getOtherFee())
                    .add(customerContractBillReportVO.getRefundFee()));
            customerContractBillReportVO.setReceiveTotalBill(customerContractBillReportVO.getReceiveTotalRent()
                    .add(customerContractBillReportVO.getReceiveManagementTotalFee())
                    .add(customerContractBillReportVO.getReceivePowerFee())
                    .add(customerContractBillReportVO.getReceiveWaterFee())
                    .add(customerContractBillReportVO.getOtherFee())
                    .add(customerContractBillReportVO.getRefundFee()));
            customerContractBillReportVO.setWaitPay(customerContractBillReportVO.getTotalBill().subtract(customerContractBillReportVO.getReceiveTotalBill()));
        }
        return customerContractBillReportVOS;
    }

}
