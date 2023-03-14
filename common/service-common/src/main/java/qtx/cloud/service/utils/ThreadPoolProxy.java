package qtx.cloud.service.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 创建线程池代理类,用于创建新的自定义线程
 *
 * @author xnd
 * @since 2022/11/17 14:16
 */
@Component
public class ThreadPoolProxy {
  @Bean
  public ThreadPoolExecutor threadPoolExecutor() {
    return new ThreadPoolExecutor(
        10,
        20,
        10,
        TimeUnit.SECONDS,
        new ArrayBlockingQueue<>(5),
        new ThreadPoolExecutor.CallerRunsPolicy());
  }
}
