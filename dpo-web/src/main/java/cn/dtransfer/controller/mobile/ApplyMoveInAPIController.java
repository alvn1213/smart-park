package cn.dtransfer.controller.mobile;

import cn.dtransfer.admin.domain.ApplyMoveIn;
import cn.dtransfer.admin.domain.ApplySettle;
import cn.dtransfer.common.utils.ValidatorUtils;
import cn.dtransfer.service.IApplyMoveInService;
import cn.dtransfer.admin.service.IParkService;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 注册迁入申请 提供者
 *
 * @author dtransfer
 * @date 2024-04-13
 */
@RestController
@RequestMapping("/applyMoveIn")
public class ApplyMoveInAPIController extends BaseController {

    @Autowired
    private IApplyMoveInService applyMoveInService;

    @Autowired
    private IParkService parkService;

    @RequiresPermissions("member:center:view")
    @GetMapping("get/{id}")
    public R get(@PathVariable("id") Long id) {
        return R.data(applyMoveInService.selectApplyMoveInById(id));
    }

    /**
     * 查询注册迁入申请
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("getByCurrentUser")
    public R getByCurrentUser() {
        return R.data(applyMoveInService.selectApplyMoveInByCreateUserId(getCurrentUserId()));
    }



    /**
     * 新增保存注册迁入申请
     */
    @RequiresPermissions("member:center:view")
    @PostMapping("save")
    public R addSave(@RequestBody ApplyMoveIn applyMoveIn) {
        ValidatorUtils.validateEntity(applyMoveIn);
        if (applyMoveIn.getParkId() == null) {
            return R.error("请选择园区！");
        }
        applyMoveIn.setStatus(ApplySettle.Status.APPROVING);
        applyMoveIn.setCreateUserId(getCurrentUserId());
        applyMoveIn.setTenantId(parkService.selectParkById(applyMoveIn.getParkId()).getTenantId());
        return toAjax(applyMoveInService.insertApplyMoveIn(applyMoveIn));
    }

    /**
     * 修改保存注册迁入申请
     */
    @RequiresPermissions("member:center:view")
    @PostMapping("update")
    public R editSave(@RequestBody ApplyMoveIn applyMoveIn) {
        return toAjax(applyMoveInService.updateApplyMoveIn(applyMoveIn));
    }

}
