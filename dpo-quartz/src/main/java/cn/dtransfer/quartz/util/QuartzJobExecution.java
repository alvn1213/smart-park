package cn.dtransfer.quartz.util;

import cn.dtransfer.quartz.domain.Job;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（允许并发执行）
 *
 */
public class QuartzJobExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, Job sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
