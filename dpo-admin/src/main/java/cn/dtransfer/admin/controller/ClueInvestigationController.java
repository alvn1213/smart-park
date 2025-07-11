package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.IClueInvestigationService;
import cn.dtransfer.admin.domain.ClueInvestigation;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 线索跟进 提供者
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@RestController
@RequestMapping("/admin/clueInvestigation")
public class ClueInvestigationController extends BaseController {

    @Autowired
    private IClueInvestigationService clueInvestigationService;

    /**
     * 查询线索跟进
     */
    @RequiresPermissions("admin:investigation:view")
    @GetMapping("get/{id}")
    public ClueInvestigation get(@PathVariable("id") Long id) {
        return clueInvestigationService.selectClueInvestigationById(id);
    }

    /**
     * 查询线索跟进列表
     */
    @RequiresPermissions("admin:investigation:list")
    @GetMapping("list")
    public R list(ClueInvestigation clueInvestigation) {
        startPage();
        return result(clueInvestigationService.selectClueInvestigationList(clueInvestigation));
    }

    /**
     * 查询我的线索跟进列表
     */
    @RequiresPermissions("admin:investigation:list")
    @GetMapping("myList")
    public R myList(ClueInvestigation clueInvestigation) {
        startPage();
        clueInvestigation.setUserId(getCurrentUserId());
        return result(clueInvestigationService.selectClueInvestigationList(clueInvestigation));
    }

    /**
     * 新增保存线索跟进
     */
    @RequiresPermissions("admin:investigation:add")
    @PostMapping("save")
    public R addSave(@RequestBody ClueInvestigation clueInvestigation) {
        ValidatorUtils.validateEntity(clueInvestigation);
        clueInvestigation.setClueId(clueInvestigation.getId());
        return toAjax(clueInvestigationService.insertClueInvestigation(clueInvestigation));
    }

    /**
     * 修改保存线索跟进
     */
    @RequiresPermissions("admin:investigation:edit")
    @PostMapping("update")
    public R editSave(@RequestBody ClueInvestigation clueInvestigation) {
        return toAjax(clueInvestigationService.updateClueInvestigation(clueInvestigation));
    }

    /**
     * 删除线索跟进
     */
    @RequiresPermissions("admin:investigation:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(clueInvestigationService.deleteClueInvestigationByIds(ids));
    }

}
