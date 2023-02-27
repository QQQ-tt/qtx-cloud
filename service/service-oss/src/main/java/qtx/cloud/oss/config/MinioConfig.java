package qtx.cloud.oss.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qtx
 * @since 2022/11/17 16:29
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "config.minio")
public class MinioConfig {

    private String url;

    private String accessKey;

    private String secretKey;
    /**
     * 默认Bucket
     */
    private String bucketName;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build();
    }
}
