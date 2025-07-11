package cn.dtransfer.system.controller.monitor;

import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.poi.ExcelUtil;
import cn.dtransfer.system.domain.OperLog;
import cn.dtransfer.system.log.enums.BusinessType;
import cn.dtransfer.system.service.IOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.List;

/**
 * 操作日志记录 提供者
 *
 */
@RestController
@RequestMapping("monitor/operLog")
public class OperLogController extends BaseController {

    @Autowired
    private IOperLogService operLogService;

    /**
     * 查询操作日志记录
     */
//    @GetMapping("get/{operId}")
//    public OperLog get(@PathVariable("operId") Long operId) {
//        return operLogService.selectOperLogById(operId);
//    }

    /**
     * 查询操作日志记录列表
     */
    @RequiresPermissions("monitor:operlog:list")
    @RequestMapping("list")
    public R list(OperLog operLog) {
        startPage();
        return result(operLogService.selectOperLogList(operLog));
    }

    @RequiresPermissions("monitor:operlog:export")
    @cn.dtransfer.system.log.annotation.OperLog(title = "操作日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public R export(OperLog operLog) {
        List<OperLog>      list = operLogService.selectOperLogList(operLog);
        ExcelUtil<OperLog> util = new ExcelUtil<OperLog>(OperLog.class);
        return util.exportExcel(list, "操作日志");
    }

    /**
     * 删除操作日志记录
     */
    @RequiresPermissions("monitor:operlog:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(operLogService.deleteOperLogByIds(ids));
    }

    /**
     * 清除操作日志记录
     */
    @RequiresPermissions("monitor:operlog:remove")
    @cn.dtransfer.system.log.annotation.OperLog(title = "操作日志", businessType = BusinessType.CLEAN)
    @PostMapping("/clean")
    public R clean() {
        operLogService.cleanOperLog();
        return R.ok();
    }
}
