package cn.dtransfer.system.log.listen;

import cn.dtransfer.common.constant.Constants;
import cn.dtransfer.common.redis.util.RedisUtils;
import cn.dtransfer.system.domain.OperLog;
import cn.dtransfer.system.domain.vo.CurrentUserVO;
import cn.dtransfer.system.log.event.LoginInfoEvent;
import cn.dtransfer.system.log.event.OperLogEvent;
import cn.dtransfer.system.log.event.UserOnlineEvent;
import cn.dtransfer.system.service.ILoginInfoService;
import cn.dtransfer.system.service.IOperLogService;
import cn.dtransfer.system.domain.LoginInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * 异步监听日志事件
 *
 */
@Slf4j
@AllArgsConstructor
public class LogListener {

    @Autowired
    private IOperLogService operLogService;
    @Autowired
    private ILoginInfoService loginInfoService;
    @Autowired
    private RedisUtils        redis;

    private final static String ACCESS_USERID = Constants.ACCESS_USERID;

    @Async
    @Order
    @EventListener(OperLogEvent.class)
    public void listenOperLog(OperLogEvent event) {
        OperLog operLog = (OperLog) event.getSource();
        operLogService.insertOperlog(operLog);
        log.info("操作日志记录成功：{}", operLog);
    }

    @Async
    @Order
    @EventListener(LoginInfoEvent.class)
    public void listenLoginInfo(LoginInfoEvent event) {
        LoginInfo loginInfo = (LoginInfo) event.getSource();
        loginInfoService.insertLogininfor(loginInfo);
        log.info("访问日志记录成功：{}", loginInfo);
    }

    @Async
    @Order
    @EventListener(UserOnlineEvent.class)
    public void listenUserOnline(UserOnlineEvent event) {
        CurrentUserVO userOnlineVO = (CurrentUserVO) event.getSource();
        redis.set(ACCESS_USERID + ":" + userOnlineVO.getUserId(), userOnlineVO, RedisUtils.DEFAULT_EXPIRE);
        log.info("保存在线记录成功：{}", userOnlineVO);
    }

}
