package qtx.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author qtx
 * @since 2022/10/27 21:52
 */
@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
public class GatewayApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(GatewayApplication.class, args);
        System.out.println(run.getEnvironment().getProperty("service-name"));
    }
}
