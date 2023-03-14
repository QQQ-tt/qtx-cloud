package qtx.cloud.service.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import feign.optionals.OptionalDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import qtx.cloud.java.constant.StaticConstant;
import qtx.cloud.service.utils.CommonMethod;

/**
 * @author qtx
 * @since 2022/11/30 18:53
 */
@Configuration
public class MyFeignConfig implements RequestInterceptor {

  private final CommonMethod commonMethod;

  public MyFeignConfig(CommonMethod commonMethod) {
    this.commonMethod = commonMethod;
  }

  /**
   * 设置feign请求头参数
   *
   * @param template
   */
  @Override
  public void apply(RequestTemplate template) {
    template.header(StaticConstant.AUTH, "123");
    template.header(StaticConstant.USER, commonMethod.getUser());
  }

  /**
   * feign 接口异常捕获 状态码大于3xx
   *
   * @return
   */
  @Bean
  public ErrorDecoder exceptionErrorDecoder() {
    return new ExceptionErrorDecoder();
  }

  /**
   * feign接口异常捕获 状态码大于2xx小于3xx
   *
   * @param messageConverters
   * @param customizers
   * @return
   */
  @Bean
  public Decoder exceptionDecoder(
      ObjectFactory<HttpMessageConverters> messageConverters,
      ObjectProvider<HttpMessageConverterCustomizer> customizers) {
    return new OptionalDecoder(
        new ResponseEntityDecoder(new ExceptionDecoder(messageConverters, customizers)));
  }
}
