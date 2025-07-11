package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.IExpenseSettingsService;
import cn.dtransfer.admin.domain.ExpenseSettings;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 费项配置 提供者
 *
 * @author dtransfer
 * @date 2024-03-24
 */
@RestController
@RequestMapping("/admin/expenseSettings")
public class ExpenseSettingsController extends BaseController {

    @Autowired
    private IExpenseSettingsService expenseSettingsService;

    /**
     * 查询费项配置
     */
    @GetMapping("get/{id}")
    public ExpenseSettings get(@PathVariable("id") Long id) {
        return expenseSettingsService.selectExpenseSettingsById(id);
    }

    /**
     * 查询费项配置列表
     */
    @RequiresPermissions("admin:ExpenseSettings:list")
    @GetMapping("list")
    public R list(ExpenseSettings expenseSettings) {
        startPage();
        return result(expenseSettingsService.selectExpenseSettingsList(expenseSettings));
    }


    /**
     * 查询费项配置列表
     */
    @RequiresPermissions("admin:ExpenseSettings:list")
    @GetMapping("allList")
    public R allList(ExpenseSettings expenseSettings) {
        return result(expenseSettingsService.selectExpenseSettingsList(expenseSettings));
    }


    /**
     * 新增保存费项配置
     */
    @RequiresPermissions("admin:ExpenseSettings:add")
    @PostMapping("save")
    public R addSave(@RequestBody ExpenseSettings expenseSettings) {
        ValidatorUtils.validateEntity(expenseSettings);
        return toAjax(expenseSettingsService.insertExpenseSettings(expenseSettings));
    }

    /**
     * 修改保存费项配置
     */
    @RequiresPermissions("admin:ExpenseSettings:edit")
    @PostMapping("update")
    public R editSave(@RequestBody ExpenseSettings expenseSettings) {
        return toAjax(expenseSettingsService.updateExpenseSettings(expenseSettings));
    }

    /**
     * 启动 ，停用
     */
    @RequiresPermissions("admin:ExpenseSettings:edit")
    @PostMapping("enabled")
    public R changeMarketable(@RequestBody ExpenseSettings expenseSettings) {
        ExpenseSettings newExpenseSettings = expenseSettingsService.selectExpenseSettingsById(expenseSettings.getId());
        if (newExpenseSettings == null) {
            return R.error("数据不存在!");
        }
        return toAjax(expenseSettingsService.updateExpenseSettings(expenseSettings));
    }


    /**
     * 删除费项配置
     */
    @RequiresPermissions("admin:ExpenseSettings:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(expenseSettingsService.deleteExpenseSettingsByIds(ids));
    }

}
