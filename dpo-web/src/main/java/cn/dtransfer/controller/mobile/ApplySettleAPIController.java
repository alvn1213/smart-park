package cn.dtransfer.controller.mobile;

import cn.hutool.core.util.ObjectUtil;
import cn.dtransfer.admin.domain.ApplySettle;
import cn.dtransfer.admin.domain.Park;
import cn.dtransfer.admin.service.IParkService;
import cn.dtransfer.common.utils.ValidatorUtils;
import cn.dtransfer.service.IApplySettleService;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 入驻申请 提供者
 *
 * @author dtransfer
 * @date 2024-04-12
 */
@RestController
@RequestMapping("/settle")
public class ApplySettleAPIController extends BaseController {

    @Autowired
    private IApplySettleService applySettleService;

    @Autowired
    private IParkService parkService;


    @RequiresPermissions("member:center:view")
    @GetMapping("get/{id}")
    public R get(@PathVariable("id") Long id) {
        return R.data(applySettleService.selectApplySettleById(id));
    }

    /**
     * 查询入驻申请
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("getByCurrentUser")
    public R getByCurrentUser() {
        return R.data(applySettleService.selectApplySettleByCurrentUser(getCurrentUserId()));
    }


    /**
     * 新增保存入驻申请
     */
    @RequiresPermissions("member:center:view")
    @PostMapping("save")
    public R addSave(@RequestBody ApplySettle applySettle) {
        ValidatorUtils.validateEntity(applySettle);
        if (applySettle.getParkId() == null) {
            return R.error("请选择园区!");
        }
        applySettle.setStatus(ApplySettle.Status.APPROVING);
        applySettle.setCreateUserId(getCurrentUserId());
        Park park = parkService.selectParkById(applySettle.getParkId());
        if (ObjectUtil.isNotEmpty(park)) {
            applySettle.setTenantId(park.getTenantId());
        }
        return toAjax(applySettleService.insertApplySettle(applySettle));
    }


    /**
     * 修改保存入驻申请
     */
    @RequiresPermissions("member:center:view")
    @PostMapping("update")
    public R editSave(@RequestBody ApplySettle applySettle) {
        return toAjax(applySettleService.updateApplySettle(applySettle));
    }
}
