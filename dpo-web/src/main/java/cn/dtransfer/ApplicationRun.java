package cn.dtransfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.wf.jwtp.configuration.EnableJwtPermission;

/**
 * 启动程序
 *
 */
@EnableJwtPermission
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ApplicationRun {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRun.class, args);
    }
}
