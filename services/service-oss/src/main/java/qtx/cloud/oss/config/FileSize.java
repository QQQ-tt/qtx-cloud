package qtx.cloud.oss.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

/**
 * @author qtx
 * @since 2022/11/18 17:33
 */
@Configuration
public class FileSize {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.of(1, DataUnit.GIGABYTES));
        factory.setMaxRequestSize(DataSize.of(1, DataUnit.GIGABYTES));
        return factory.createMultipartConfig();
    }
}
