package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.IApplyParkService;
import cn.dtransfer.admin.domain.ApplyPark;
import cn.dtransfer.admin.domain.ApplySettle;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 入园申请 提供者
 *
 * @author dtransfer
 * @date 2024-04-12
 */
@RestController
@RequestMapping("/admin/applyPark")
public class ApplyParkController extends BaseController {

    @Autowired
    private IApplyParkService applyParkService;

    /**
     * 查询入园申请
     */
    @RequiresPermissions("admin:applyPark:view")
    @GetMapping("get/{id}")
    public ApplyPark get(@PathVariable("id") Long id) {
        return applyParkService.selectApplyParkById(id);
    }

    /**
     * 查询入园申请列表
     */
    @RequiresPermissions("admin:applyPark:list")
    @GetMapping("list")
    public R list(ApplyPark applyPark) {
        startPage();
        return result(applyParkService.selectApplyParkList(applyPark));
    }


    /**
     * 修改保存入园申请
     */
    @RequiresPermissions("admin:applyPark:edit")
    @PostMapping("update")
    public R editSave(@RequestBody ApplyPark applyPark) {
        return toAjax(applyParkService.updateApplyPark(applyPark));
    }

    /**
     * 删除入园申请
     */
    @RequiresPermissions("admin:applyPark:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(applyParkService.deleteApplyParkByIds(ids));
    }


    /**
     * 批量审批
     * @param ids
     * @return
     */
    @RequiresPermissions("admin:applyPark:edit")
    @PostMapping("approve")
    public R approve(String ids, ApplySettle.Status status, String remark){
        return toAjax(applyParkService.approveApplyParkByIds(ids, status, remark));
    }

    /**
     * 根据id取消审批
     * @param id
     * @return
     */
    @RequiresPermissions("admin:applyPark:edit")
    @PostMapping("cancelApprove")
    public R cancelApprove(Long id){
        return toAjax(applyParkService.cancelApprove(id));
    }

}
