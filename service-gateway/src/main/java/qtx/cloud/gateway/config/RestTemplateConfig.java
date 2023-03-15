package qtx.cloud.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author qtx
 * @since 2023/3/15 16:16
 */
@Component
public class RestTemplateConfig {
  @Bean
  public RestTemplate getRestTemplate() {
    // 配置HTTP超时时间
    HttpComponentsClientHttpRequestFactory httpRequestFactory =
        new HttpComponentsClientHttpRequestFactory();
    httpRequestFactory.setConnectionRequestTimeout(6000);
    httpRequestFactory.setConnectTimeout(6000);
    httpRequestFactory.setReadTimeout(6000);
    return new RestTemplate(httpRequestFactory);
  }
}
