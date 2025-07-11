package cn.dtransfer.common.redis.annotation;

import java.lang.annotation.*;

/**
 * <p>File：RedisEvict.java</p>
 * <p>Title: redis删除注解</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2018 2018年12月6日 下午4:33:31</p>
 * <p>Company:  </p>
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisEvict {
    String key();

    String fieldKey();
}
