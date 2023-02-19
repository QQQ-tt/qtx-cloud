package qtx.cloud.oss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 文件管理
 * @author qtx
 * @since 2022/10/27 21:22
 */
@RefreshScope
@EnableFeignClients(basePackages = "qtx.cloud")
@ServletComponentScan(basePackages = "qtx.cloud")
@MapperScan(basePackages = {"qtx.cloud.oss.mapper"})
@SpringBootApplication(scanBasePackages = {"qtx.cloud"})
public class ServiceOssApplication {
    
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ServiceOssApplication.class, args);
        System.out.println(run.getEnvironment().getProperty("test.name"));
    }
}
