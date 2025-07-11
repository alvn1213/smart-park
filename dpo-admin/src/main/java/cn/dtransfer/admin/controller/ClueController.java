package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.IClueService;
import cn.dtransfer.admin.domain.Clue;
import cn.dtransfer.admin.utils.ExcelView;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.ValidatorUtils;
import org.jxls.common.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.Date;
import java.util.List;

/**
 * 线索管理 提供者
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@RestController
@RequestMapping("/admin/clue")
public class ClueController extends BaseController {

    @Autowired
    private IClueService clueService;

    /**
     * 查询线索管理
     */
    @RequiresPermissions("admin:clue:view")
    @GetMapping("get/{id}")
    public Clue get(@PathVariable("id") Long id) {
        return clueService.selectClueById(id);
    }

    /**
     * 查询线索管理列表
     */
    @RequiresPermissions("admin:clue:list")
    @GetMapping("list")
    public R list(Clue clue) {
        startPage();
        return result(clueService.selectClueList(clue));
    }

    /**
     * 查询线索分派的已经激活的列表
     */
    @RequiresPermissions("admin:clue:list")
    @GetMapping("ClueActivationList")
    public R ActivationList(Clue clue) {
        clue.setCustomerStatus("0");
        startPage();
        return result(clueService.selectClueList(clue));
    }

    /**
     * 分派对接人
     */
    @RequiresPermissions("admin:clue:edit")
    @PostMapping("/review")
    public R review(@RequestBody Clue clue) {
        Clue newClue = clueService.selectClueById(clue.getId());
        if (newClue == null) {
            return R.error("分派失败");
        }
        clue.setAssignmentStatus("1");
        clue.setAssignmentTime(new Date());
        return toAjax(clueService.updateClue(clue));
    }

    /**
     * 关闭线索
     */
    @RequiresPermissions("admin:clue:edit")
    @PostMapping("/closeClue")
    public R closeClue(@RequestBody Clue clue) {
        clue.setCustomerStatus("1");
        return toAjax(clueService.updateClue(clue));
    }

    /**
     * 新增保存线索管理
     */
    @RequiresPermissions("admin:clue:add")
    @PostMapping("save")
    public R addSave(@RequestBody Clue clue) {
        ValidatorUtils.validateEntity(clue);
        return toAjax(clueService.insertClue(clue));
    }

    /**
     * 修改保存线索管理
     */
    @RequiresPermissions("admin:clue:edit")
    @PostMapping("update")
    public R editSave(@RequestBody Clue clue) {
        return toAjax(clueService.updateClue(clue));
    }

    /**
     * 删除线索管理
     */
    @RequiresPermissions("admin:clue:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(clueService.deleteClueByIds(ids));
    }


    /**
     * 根据条件导出线索报表功能 exportPowerWaterFee
     *
     * @param clue
     */
    @RequiresPermissions("admin:clue:view")
    @GetMapping("/excelClue")
    public void excelClue(Clue clue) {
        Context context = new Context();
        List<Clue> clueList = clueService.selectClueList(clue);

        int i = 1;
        for (Clue item : clueList) {
            item.setId(Long.valueOf(i));
            i++;
        }
        context.putVar("date", "2031-04-15");
        context.putVar("clueList", clueList);
        new ExcelView("excel/招商线索导出模板.xls", "招商线索导出", context);
    }
}
