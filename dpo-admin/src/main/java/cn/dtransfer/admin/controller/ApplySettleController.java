package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.IApplySettleService;
import cn.dtransfer.admin.domain.ApplySettle;
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
@RequestMapping("/admin/settle")
public class ApplySettleController extends BaseController {

    @Autowired
    private IApplySettleService applySettleService;

    /**
     * 查询入驻申请
     */
    @RequiresPermissions("admin:settle:view")
    @GetMapping("get/{id}")
    public ApplySettle get(@PathVariable("id") Long id) {
        return applySettleService.selectApplySettleById(id);
    }

    /**
     * 查询入驻申请列表
     */
    @RequiresPermissions("admin:settle:list")
    @GetMapping("list")
    public R list(ApplySettle applySettle) {
        startPage();
        return result(applySettleService.selectApplySettleList(applySettle));
    }



    /**
     * 修改保存入驻申请
     */
    @RequiresPermissions("admin:settle:edit")
    @PostMapping("update")
    public R editSave(@RequestBody ApplySettle applySettle) {
        return toAjax(applySettleService.updateApplySettle(applySettle));
    }

    /**
     * 删除入驻申请
     */
    @RequiresPermissions("admin:settle:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(applySettleService.deleteApplySettleByIds(ids));
    }


    /**
     * 批量审批
     * @param ids
     * @return
     */
    @RequiresPermissions("admin:settle:edit")
    @PostMapping("approve")
    public R approve(String ids, ApplySettle.Status status, String remark){
        return toAjax(applySettleService.approveApplySettleByIds(ids, status, remark));
    }

    /**
     * 根据id取消审批
     * @param id
     * @return
     */
    @RequiresPermissions("admin:settle:edit")
    @PostMapping("cancelApprove")
    public R cancelApprove(Long id){
        return toAjax(applySettleService.cancelApprove(id));
    }

}
