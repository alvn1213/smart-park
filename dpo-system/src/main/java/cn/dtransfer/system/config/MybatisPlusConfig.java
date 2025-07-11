package cn.dtransfer.system.config;

import cn.dtransfer.system.handlers.MyMetaObjectHandler;
import cn.dtransfer.system.handlers.MyParkLineHandler;
import cn.dtransfer.system.handlers.MyTenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus 配置
 *
 */
@Slf4j
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(blockAttackInnerInterceptor());
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor());
        interceptor.addInnerInterceptor(tenantLineInnerInterceptor());
        interceptor.addInnerInterceptor(parkLineInnerInterceptor());
        return interceptor;
    }

    /**
     * 多租户过滤
     */
    @Bean
    public MyTenantLineHandler myTenantLineHandler() {
        return new MyTenantLineHandler();
    }

    /**
     * 多部门过滤
     */
    @Bean
    public MyParkLineHandler myParkLineHandler() {
        return new MyParkLineHandler();
    }

    /**
     * 多租户
     */
    @Bean
    public TenantLineInnerInterceptor tenantLineInnerInterceptor() {
        return new TenantLineInnerInterceptor(myTenantLineHandler());
    }

    /**
     * 多部门
     */
    @Bean
    public TenantLineInnerInterceptor parkLineInnerInterceptor() {
        return new TenantLineInnerInterceptor(myParkLineHandler());
    }

    /**
     * 防止全表更新与删除
     */
    @Bean
    public BlockAttackInnerInterceptor blockAttackInnerInterceptor() {
        return new BlockAttackInnerInterceptor();
    }

    /**
     * 乐观锁
     */
    @Bean
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    /**
     * 自动填充
     */
    @Bean
    public MyMetaObjectHandler myMetaObjectHandler() {
        return new MyMetaObjectHandler();
    }


}
