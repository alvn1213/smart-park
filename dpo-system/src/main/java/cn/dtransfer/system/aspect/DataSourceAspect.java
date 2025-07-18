package cn.dtransfer.system.aspect;

import cn.dtransfer.common.datasource.DynamicDataSourceContextHolder;
import cn.dtransfer.common.enums.DataSourceType;
import cn.dtransfer.system.config.DruidProperties;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 多数据源处理
 *
 */
@Aspect
@Order(1)
@Component
public class DataSourceAspect {

    @Autowired
    private DruidProperties druidProperties;

    @Around("execution(* cn.dtransfer..*ServiceImpl.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        if (!druidProperties.slaveEnable) {
            return point.proceed();
        }
        // 获取到当前执行的方法名
        String methodName = point.getSignature().getName();
        if (isSlave(methodName)) {
            // 标记为读库,可以自定义选择数据源
            DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        } else {
            // 标记为写库
            DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.MASTER.name());
        }
        try {
            return point.proceed();
        } finally {
            // 销毁数据源 在执行方法之后
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
    }

    /**
     * 判断是否为读库
     *
     * @param methodName
     * @return
     */
    private boolean isSlave(String methodName) {
        // 方法名以query、find、get开头的方法名走从库
        return StringUtils.startsWithAny(methodName, new String[]{"query", "find", "get", "select"});
    }
}
