package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.IBuildingService;
import cn.dtransfer.admin.domain.Building;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 楼宇管理 提供者
 *
 * @author dtransfer
 * @date 2024-03-24
 */
@RestController
@RequestMapping("admin/building")
public class BuildingController extends BaseController {

    @Autowired
    private IBuildingService buildingService;


    /**
     * 查询楼宇管理
     */
    @RequiresPermissions("admin:building:view")
    @GetMapping("get/{id}")
    public Building get(@PathVariable("id") Long id) {
        return buildingService.selectBuildingById(id);
    }

    /**
     * 查询楼宇管理列表
     */
    @RequiresPermissions("admin:building:list")
    @GetMapping("list")
    public R list(Building building) {
        startPage();
        building.setDeleteFlag(0);
        return result(buildingService.selectBuildingList(building));
    }


    /**
     * 新增保存楼宇管理
     */
    @RequiresPermissions("admin:building:add")
    @PostMapping("save")
    public R addSave(@RequestBody Building building) {
        return toAjax(buildingService.insertBuilding(building));
    }

    /**
     * 修改保存楼宇管理
     */
    @RequiresPermissions("admin:building:edit")
    @PostMapping("update")
    public R editSave(@RequestBody Building building) {
        return toAjax(buildingService.updateBuilding(building));
    }

    /**
     * 删除楼宇管理
     */
    @RequiresPermissions("admin:building:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(buildingService.deleteBuildingByIds(ids));
    }

}
