package cn.dtransfer.quartz.controller;


import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.core.text.Convert;
import cn.dtransfer.common.exception.job.TaskException;
import cn.dtransfer.common.utils.ValidatorUtils;
import cn.dtransfer.common.utils.poi.ExcelUtil;
import cn.dtransfer.quartz.domain.Job;
import cn.dtransfer.quartz.service.IJobService;
import cn.dtransfer.quartz.util.CronUtils;
import cn.dtransfer.system.log.annotation.OperLog;
import cn.dtransfer.system.log.enums.BusinessType;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.List;

/**
 * 调度任务信息操作处理
 *
 */
@RestController
@RequestMapping("/monitor/job")
public class JobController extends BaseController {

    @Autowired
    private IJobService jobService;

    /**
     * 查询定时任务列表
     */
    @RequiresPermissions("monitor:job:list")
    @GetMapping("/list")
    public R list(Job job) {
        startPage();
        List<Job> list = jobService.selectJobList(job);
        return result(list);
    }

    /**
     * 导出定时任务列表
     */
    @RequiresPermissions("monitor:job:export")
    @OperLog(title = "定时任务", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R export(Job job) {
        List<Job> list = jobService.selectJobList(job);
        ExcelUtil<Job> util = new ExcelUtil<Job>(Job.class);
        return util.exportExcel(list, "定时任务");
    }

    /**
     * 获取定时任务详细信息
     */
    @RequiresPermissions("monitor:job:query")
    @GetMapping(value = "/{jobId}")
    public R getInfo(@PathVariable("jobId") Long jobId) {
        return R.data(jobService.selectJobById(jobId));
    }

    /**
     * 新增定时任务
     */
    @RequiresPermissions("monitor:job:add")
    @OperLog(title = "定时任务", businessType = BusinessType.INSERT)
    @PostMapping("save")
    public R add(@RequestBody Job job) throws SchedulerException, TaskException {
        if (!CronUtils.isValid(job.getCronExpression())) {
            return R.error("cron表达式不正确");
        }
        ValidatorUtils.validateEntity(job);
        job.setCreateBy(getLoginName());
        return toAjax(jobService.insertJob(job));
    }

    /**
     * 修改定时任务
     */
    @RequiresPermissions("monitor:job:edit")
    @OperLog(title = "定时任务", businessType = BusinessType.UPDATE)
    @PostMapping("update")
    public R edit(@RequestBody Job job) throws SchedulerException, TaskException {
        if (!CronUtils.isValid(job.getCronExpression())) {
            return R.error("cron表达式不正确");
        }
        job.setUpdateBy(getLoginName());
        return toAjax(jobService.updateJob(job));
    }

    /**
     * 定时任务状态修改
     */
    @RequiresPermissions("monitor:job:changeStatus")
    @OperLog(title = "定时任务", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public R changeStatus(@RequestBody Job job) throws SchedulerException {
        Job newJob = jobService.selectJobById(job.getId());
        newJob.setStatus(job.getStatus());
        return toAjax(jobService.changeStatus(newJob));
    }

    /**
     * 定时任务立即执行一次
     */
    @RequiresPermissions("monitor:job:changeStatus")
    @OperLog(title = "定时任务", businessType = BusinessType.UPDATE)
    @PostMapping("/run")
    public R run(@RequestBody Job job) throws SchedulerException {
        jobService.run(job);
        return R.ok();
    }

    /**
     * 删除定时任务
     */
    @RequiresPermissions("monitor:job:remove")
    @OperLog(title = "定时任务", businessType = BusinessType.DELETE)
    @PostMapping("remove")
    public R remove(String ids) throws SchedulerException {
        jobService.deleteJobByIds(Convert.toLongArray(ids));
        return R.ok();
    }

}
