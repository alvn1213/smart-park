package cn.dtransfer.system.log.event;

import cn.dtransfer.system.domain.vo.CurrentUserVO;
import org.springframework.context.ApplicationEvent;

/**
 * 系统日志事件
 *
 */
public class UserOnlineEvent extends ApplicationEvent {

    public UserOnlineEvent(CurrentUserVO source) {
        super(source);
    }
}
