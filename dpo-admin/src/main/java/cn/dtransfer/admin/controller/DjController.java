package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.IDjService;
import cn.dtransfer.admin.domain.Dj;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.Date;

/**
 * 党建管理 提供者
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@RestController
@RequestMapping("/admin/dj")
public class DjController extends BaseController {

    @Autowired
    private IDjService djService;

    /**
     * 查询党建管理
     */
    @RequiresPermissions("admin:dj:edit")
    @GetMapping("get/{id}")
    public Dj get(@PathVariable("id") Long id) {
        return djService.selectDjById(id);
    }

    /**
     * 查询党建管理列表
     */
    @RequiresPermissions("admin:dj:list")
    @GetMapping("list")
    public R list(Dj dj) {
        startPage();
        return result(djService.selectDjList(dj));
    }


    /**
     * 新增保存党建管理
     */
    @RequiresPermissions("admin:dj:add")
    @PostMapping("save")
    public R addSave(@RequestBody Dj dj) {
        ValidatorUtils.validateEntity(dj);
        return toAjax(djService.insertDj(dj));
    }

    /**
     * 修改保存党建管理
     */
    @RequiresPermissions("admin:dj:edit")
    @PostMapping("update")
    public R editSave(@RequestBody Dj dj) {
        return toAjax(djService.updateDj(dj));
    }

    /**
     * 删除党建管理
     */
    @RequiresPermissions("admin:dj:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(djService.deleteDjByIds(ids));
    }

    /**
     * 上架、下架
     */
    @RequiresPermissions("admin:dj:edit")
    @PostMapping("changeMarketable")
    public R changeMarketable(@RequestBody Dj dj) {
        Dj newDj = djService.selectDjById(dj.getId());
        if (newDj == null) {
            return R.error("党建管理不存在!");
        }
        dj.setMarketableTime(new Date());
        return toAjax(djService.updateDj(dj));
    }
}
