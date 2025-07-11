package cn.dtransfer.system.log.event;

import cn.dtransfer.system.domain.OperLog;
import org.springframework.context.ApplicationEvent;

/**
 * 系统日志事件
 *
 */
public class OperLogEvent extends ApplicationEvent {
    private static final long serialVersionUID = 8905017895058642111L;

    public OperLogEvent(OperLog source) {
        super(source);
    }
}
