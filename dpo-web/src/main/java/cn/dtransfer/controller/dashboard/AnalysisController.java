package cn.dtransfer.controller.dashboard;



import cn.dtransfer.admin.service.ICustomerContractBillService;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wf.jwtp.annotation.RequiresPermissions;


/**
 * @author dtransfer
 * 大屏数据展示
 */
@RestController
@RequestMapping("/dashboard/analysis")
public class AnalysisController extends BaseController {


    @Autowired
    private ICustomerContractBillService customerContractBillService;



    /**
     * 驾驶舱本月账单总应收，已收，未收数字
     */
    @RequiresPermissions("system:dashboard:analysis")
    @GetMapping("bill_data")
    public R bill_data() {
        return R.ok(customerContractBillService.analysisContractBill("TOTAL"));
    }

    /**
     * 驾驶舱本月账单租金总应收，已收，未收数字
     */
    @RequiresPermissions("system:dashboard:analysis")
    @GetMapping("bill_rent_data")
    public R bill_rent_data() {
        return R.ok(customerContractBillService.analysisContractBill("RENT"));
    }

    /**
     * 驾驶舱本月账单物业管理费总应收，已收，未收数字
     */
    @RequiresPermissions("system:dashboard:analysis")
    @GetMapping("bill_management_data")
    public R bill_management_data() {
        return R.ok(customerContractBillService.analysisContractBill("MANAGEMENT"));
    }

    /**
     * 驾驶舱本月账单物业管理费总应收，已收，未收数字
     */
    @RequiresPermissions("system:dashboard:analysis")
    @GetMapping("bill_pw_data")
    public R bill_pw_data() {
        return R.ok(customerContractBillService.analysisContractBill("PW"));
    }

    /**
     * 统计当年每月应收款,已收款，未收款
     * @return
     */
    @RequiresPermissions("system:dashboard:analysis")
    @GetMapping("bill_year_data")
    public R bill_year_data() {
        return R.ok(customerContractBillService.analysisYearContractBill());
    }

    /**
     * 企业账单排行榜
     */
    @RequiresPermissions("system:dashboard:analysis")
    @GetMapping("rank_list")
    public R rank_list(String dateType) {
        return R.data(customerContractBillService.rankList(dateType));
    }


}
