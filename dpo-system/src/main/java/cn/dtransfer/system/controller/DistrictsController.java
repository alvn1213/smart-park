package cn.dtransfer.system.controller;

import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.poi.ExcelUtil;
import cn.dtransfer.system.log.enums.BusinessType;
import cn.dtransfer.system.service.IDistrictsService;
import cn.dtransfer.system.domain.Districts;
import cn.dtransfer.system.log.annotation.OperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.Date;
import java.util.List;

/**
 * 地区 信息操作处理
 *
 */
@RestController
@RequestMapping("system/districts")
public class DistrictsController extends BaseController {

    @Autowired
    private IDistrictsService districtsService;

    /**
     * 查询地区树形列表
     */
    @RequiresPermissions("system:districts:tree")
    @RequestMapping("/tree")
    public R tree() {
        return R.data(districtsService.getDistrictsTree());
    }


    /**
     * 导出地区列表
     */
    @RequiresPermissions("system:districts:export")
    @OperLog(title = "地区", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R export(Districts districts) {
        List<Districts> list = districtsService.selectDistrictsList(districts);
        ExcelUtil<Districts> util = new ExcelUtil<Districts>(Districts.class);
        return util.exportExcel(list, "districts");
    }

    /**
     * 查询地区列表
     */
    @RequiresPermissions("system:districts:list")
    @RequestMapping("/list")
    public R list(Districts districts) {
        startPage();
        return result(districtsService.selectDistrictsList(districts));
    }

    /**
     * 新增保存地区
     */
    @RequiresPermissions("system:districts:add")
    @OperLog(title = "地区", businessType = BusinessType.INSERT)
    @PostMapping("save")
    public R addSave(@RequestBody Districts districts) {
        districts.setPid(districts.getId() / 100);
        districts.setCreateTime(new Date());
        districts.setUpdateTime(new Date());
        districts.setOperator(getLoginName());
        return toAjax(districtsService.insertDistricts(districts));
    }

    /**
     * /**
     * 修改保存地区
     */
    @RequiresPermissions("system:districts:edit")
    @OperLog(title = "地区", businessType = BusinessType.UPDATE)
    @PostMapping("update")
    public R editSave(@RequestBody Districts districts) {
        districts.setPid(districts.getId() / 100);
        districts.setOperator(getLoginName());
        districts.setUpdateTime(new Date());
        return toAjax(districtsService.updateDistricts(districts));
    }

    /**
     * 删除地区
     */
    @RequiresPermissions("system:districts:remove")
    @OperLog(title = "地区", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    public R remove(String ids) {
        return toAjax(districtsService.deleteDistrictsByIds(ids));
    }


}
