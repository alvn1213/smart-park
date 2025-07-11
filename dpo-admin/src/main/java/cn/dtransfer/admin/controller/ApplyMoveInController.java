package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.IApplyMoveInService;
import cn.dtransfer.admin.domain.ApplyMoveIn;
import cn.dtransfer.admin.domain.ApplySettle;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 注册迁入申请 提供者
 *
 * @author dtransfer
 * @date 2024-04-14
 */
@RestController
@RequestMapping("/admin/applyMoveIn")
public class ApplyMoveInController extends BaseController {

    @Autowired
    private IApplyMoveInService applyMoveInService;

    /**
     * 查询注册迁入申请
     */
    @RequiresPermissions("admin:applyMoveIn:view")
    @GetMapping("get/{id}")
    public ApplyMoveIn get(@PathVariable("id") Long id) {
        return applyMoveInService.selectApplyMoveInById(id);
    }

    /**
     * 查询注册迁入申请列表
     */
    @RequiresPermissions("admin:applyMoveIn:list")
    @GetMapping("list")
    public R list(ApplyMoveIn applyMoveIn) {
        startPage();
        return result(applyMoveInService.selectApplyMoveInList(applyMoveIn));
    }


    /**
     * 修改保存注册迁入申请
     */
    @RequiresPermissions("admin:applyMoveIn:edit")
    @PostMapping("update")
    public R editSave(@RequestBody ApplyMoveIn applyMoveIn) {
        return toAjax(applyMoveInService.updateApplyMoveIn(applyMoveIn));
    }

    /**
     * 删除注册迁入申请
     */
    @RequiresPermissions("admin:applyMoveIn:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(applyMoveInService.deleteApplyMoveInByIds(ids));
    }


    /**
     * 批量审批
     * @param ids
     * @return
     */
    @RequiresPermissions("admin:applyMoveIn:edit")
    @PostMapping("approve")
    public R approve(String ids, ApplySettle.Status status, String remark){
        return toAjax(applyMoveInService.approveApplyMoveInByIds(ids, status, remark));
    }

    /**
     * 根据id取消审批
     * @param id
     * @return
     */
    @RequiresPermissions("admin:applyMoveIn:edit")
    @PostMapping("cancelApprove")
    public R cancelApprove(Long id){
        return toAjax(applyMoveInService.cancelApprove(id));
    }

}
