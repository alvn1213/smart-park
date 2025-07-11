package cn.dtransfer.system.log;

import cn.dtransfer.common.redis.util.RedisUtils;
import cn.dtransfer.system.log.aspect.OperLogAspect;
import cn.dtransfer.system.service.ILoginInfoService;
import cn.dtransfer.system.service.IOperLogService;
import cn.dtransfer.system.log.listen.LogListener;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 日志配置
 *
 */
@EnableAsync
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
public class LogAutoConfiguration {

    private final IOperLogService logService;
    private final ILoginInfoService sysLoginInforService;
    private final RedisUtils        redis;

    @Bean
    public LogListener sysOperLogListener() {
        return new LogListener(logService, sysLoginInforService, redis);
    }

    @Bean
    public OperLogAspect operLogAspect() {
        return new OperLogAspect();
    }
}
