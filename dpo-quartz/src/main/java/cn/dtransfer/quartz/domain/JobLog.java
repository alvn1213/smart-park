package cn.dtransfer.quartz.domain;

import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 定时任务调度日志表 sys_job_log
 *
 */
@Data
@Table(name = "sys_job_log")
public class JobLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务组名
     */
    private String jobGroup;

    /**
     * 调用目标字符串
     */
    private String invokeTarget;

    /**
     * 日志信息
     */
    private String jobMessage;

    /**
     * 执行状态（0正常 1失败）
     */
    private String status;

    /**
     * 异常信息
     */
    private String exceptionInfo;

    /**
     * 开始时间
     */
    @Transient
    private Date startTime;

    /**
     * 停止时间
     */
    @Transient
    private Date stopTime;

}
