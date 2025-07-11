package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.IBuildingDetailService;
import cn.dtransfer.admin.domain.BuildingDetail;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 楼层管理 提供者
 *
 * @author dtransfer
 * @date 2024-03-24
 */
@RestController
@RequestMapping("/admin/buildingDetail")
public class BuildingDetailController extends BaseController {

    @Autowired
    private IBuildingDetailService buildingDetailService;

    /**
     * 查询楼层管理
     */
    @RequiresPermissions("admin:buildingDetail:view")
    @GetMapping("get/{id}")
    public BuildingDetail get(@PathVariable("id") Long id) {
        return buildingDetailService.selectBuildingDetailById(id);
    }

    /**
     * 查询楼层管理列表
     */
    @RequiresPermissions("admin:buildingDetail:list")
    @GetMapping("list")
    public R list(BuildingDetail buildingDetail) {
        startPage();
        return result(buildingDetailService.selectBuildingDetailList(buildingDetail));
    }


    /**
     * 新增保存楼层管理
     */
    @RequiresPermissions("admin:buildingDetail:add")
    @PostMapping("save")
    public R addSave(@RequestBody BuildingDetail buildingDetail) {
        return toAjax(buildingDetailService.insertBuildingDetail(buildingDetail));
    }

    /**
     * 修改保存楼层管理
     */
    @RequiresPermissions("admin:buildingDetail:edit")
    @PostMapping("update")
    public R editSave(@RequestBody BuildingDetail buildingDetail) {
        return toAjax(buildingDetailService.updateBuildingDetail(buildingDetail));
    }

    /**
     * 删除楼层管理
     */
    @RequiresPermissions("admin:buildingDetail:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(buildingDetailService.deleteBuildingDetailByIds(ids));
    }

}
