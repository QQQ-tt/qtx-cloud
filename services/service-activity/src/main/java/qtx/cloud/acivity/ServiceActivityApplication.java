package qtx.cloud.acivity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author qtx
 * @since 2022/10/27 21:22
 */
@RefreshScope
@EnableFeignClients(basePackages = "com.gcp")
@ServletComponentScan(basePackages = "com.gcp")
@SpringBootApplication(scanBasePackages = {"com.gcp"})
public class ServiceActivityApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ServiceActivityApplication.class, args);
        System.out.println(run.getEnvironment().getProperty("test.name"));
    }
}
