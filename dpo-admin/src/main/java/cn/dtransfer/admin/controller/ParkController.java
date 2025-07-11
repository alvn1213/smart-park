package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.IParkService;
import cn.dtransfer.admin.domain.Park;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.DateUtils;
import cn.dtransfer.common.utils.ValidatorUtils;
import cn.dtransfer.system.domain.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.Logical;
import org.wf.jwtp.annotation.RequiresPermissions;


/**
 * 园区管理 提供者
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@RestController
@RequestMapping("admin/park")
public class ParkController extends BaseController {

    @Autowired
    private IParkService parkService;


    /**
     * 查询园区管理
     */
    @RequiresPermissions("admin:park:view")
    @GetMapping("get/{id}")
    public Park get(@PathVariable("id") Long id) {
        return parkService.selectParkById(id);
    }

    /**
     * 查询当前园区
     */
    @RequiresPermissions("admin:park:setting")
    @GetMapping("getCurrentPark")
    public Park getCurrentPark(Park park) {
        return parkService.selectCurrentParkByDeptId(park);
    }

    /**
     * 查询园区管理列表
     */
    @RequiresPermissions(value = {"admin:park:list", "system:user:list"}, logical = Logical.OR)
    @GetMapping("list")
    public R list(Park park) {
        startPage();
        return result(parkService.selectParkList(park));
    }


    /**
     * 新增保存园区管理
     */
    @RequiresPermissions("admin:park:add")
    @PostMapping("save")
    public R addSave(@RequestBody Park park) {
        ValidatorUtils.validateEntity(park);
        if (parkService.exists("name", park.getName())) {
            return R.error("园区名称重复!");
        }
        park.setIsMarketable(false);
        return toAjax(parkService.insertPark(park));
    }

    /**
     * 修改保存园区管理
     */
    @RequiresPermissions("admin:park:edit")
    @PostMapping("update")
    public R editSave(@RequestBody Park park) {
        ValidatorUtils.validateEntity(park);
        if (parkService.unique(park.getId(), "name", park.getName())) {
            return R.error("园区名称重复!");
        }
        return toAjax(parkService.updatePark(park));

    }


    /**
     * 园区状态修改
     */
    @RequiresPermissions("admin:park:edit")
    @PostMapping("/changeMarketable")
    public R changeMarketable(@RequestBody Park park) {
        Park newPark = parkService.selectParkById(park.getId());
        if (newPark == null) {
            return R.error("园区不存在!");
        }
        park.setMarketableTime(DateUtils.getNowDate());
        return toAjax(parkService.updatePark(park));
    }

    /**
     * 删除园区管理
     */
    @RequiresPermissions("admin:park:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(parkService.deleteParkByIds(ids));
    }


    /**
     * 初始化园区信息
     */
    @RequiresPermissions("admin:park:add")
    @PostMapping("initSavePark")
    public R initSavePark(@RequestBody Dept dept) {
        dept.setCreateBy(getLoginName());
        if (parkService.exists("name", dept.getDeptName())) {
            return R.error("园区名称重复!");
        }
        return toAjax(parkService.initPark(dept));
    }

}
