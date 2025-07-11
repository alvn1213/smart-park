package cn.dtransfer.system.controller;

import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.system.domain.Dept;
import cn.dtransfer.system.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.Set;

/**
 * 部门 提供者
 *
 */
@RestController
@RequestMapping("system/dept")
public class DeptController extends BaseController {
    @Autowired
    private IDeptService deptService;

    /**
     * 查询部门
     */
    @GetMapping("get/{deptId}")
    public Dept get(@PathVariable("deptId") Long deptId) {
        return deptService.selectDeptById(deptId);
    }

    /**
     * 加载角色部门（数据权限）列表树
     */
    @RequiresPermissions("system:dept:list")
    @GetMapping("/role/{roleId}")
    public Set<String> deptTreeData(@PathVariable("roleId") Long roleId) {
        if (null == roleId || roleId <= 0) {
            return null;
        }
        return deptService.roleDeptIds(roleId);
    }

    /**
     * 查询所有可用部门
     */
    @RequiresPermissions("system:dept:list")
    @GetMapping("list/enable")
    public R listEnable(Dept dept) {
        dept.setStatus("0");
        return result(deptService.selectDeptList(dept));
    }

    /**
     * 查询部门列表
     */
    @RequiresPermissions("system:dept:list")
    @GetMapping("list")
    public R list(Dept dept) {
        startPage();
        return result(deptService.selectDeptList(dept));
    }

    /**
     * 新增保存部门
     */
    @RequiresPermissions("system:dept:add")
    @PostMapping("save")
    public R addSave(@RequestBody Dept dept) {
        dept.setCreateBy(getLoginName());
        return toAjax(deptService.insertDept(dept));
    }

    /**
     * 修改保存部门
     */
    @RequiresPermissions("system:dept:edit")
    @PostMapping("update")
    public R editSave(@RequestBody Dept dept) {
        dept.setUpdateBy(getLoginName());
        return toAjax(deptService.updateDept(dept));
    }

    /**
     * 删除部门
     */
    @RequiresPermissions("system:dept:remove")
    @PostMapping("remove/{deptId}")
    public R remove(@PathVariable("deptId") Long deptId) {
        return toAjax(deptService.deleteDeptById(deptId));
    }

}
