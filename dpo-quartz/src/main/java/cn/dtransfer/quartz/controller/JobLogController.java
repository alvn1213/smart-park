package cn.dtransfer.quartz.controller;


import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.poi.ExcelUtil;
import cn.dtransfer.quartz.domain.JobLog;
import cn.dtransfer.quartz.service.IJobLogService;
import cn.dtransfer.system.log.annotation.OperLog;
import cn.dtransfer.system.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.List;

/**
 * 调度日志操作处理
 *
 */
@RestController
@RequestMapping("/monitor/jobLog")
public class JobLogController extends BaseController {

    @Autowired
    private IJobLogService jobLogService;

    /**
     * 查询定时任务调度日志列表
     */
    @RequiresPermissions("monitor:job:list")
    @GetMapping("/list")
    public R list(JobLog jobLog) {
        startPage();
        List<JobLog> list = jobLogService.selectJobLogList(jobLog);
        return result(list);
    }

    /**
     * 导出定时任务调度日志列表
     */
    @RequiresPermissions("monitor:job:export")
    @OperLog(title = "任务调度日志", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R export(JobLog jobLog) {
        List<JobLog> list = jobLogService.selectJobLogList(jobLog);
        ExcelUtil<JobLog> util = new ExcelUtil<JobLog>(JobLog.class);
        return util.exportExcel(list, "调度日志");
    }

    /**
     * 根据调度编号获取详细信息
     */
    @RequiresPermissions("monitor:job:query")
    @GetMapping(value = "/{configId}")
    public R getInfo(@PathVariable Long jobLogId) {
        return R.data(jobLogService.selectJobLogById(jobLogId));
    }


    /**
     * 删除定时任务调度日志
     */
    @RequiresPermissions("monitor:job:remove")
    @OperLog(title = "定时任务调度日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{jobLogIds}")
    public R remove(@PathVariable Long[] jobLogIds) {
        return toAjax(jobLogService.deleteJobLogByIds(jobLogIds));
    }

    /**
     * 清空定时任务调度日志
     */
    @RequiresPermissions("monitor:job:remove")
    @OperLog(title = "调度日志", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    public R clean() {
        jobLogService.cleanJobLog();
        return R.ok();
    }
}
