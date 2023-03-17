package qtx.cloud.service.config.exception;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import feign.codec.DecodeException;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import qtx.cloud.java.Result;
import qtx.cloud.java.exception.DataException;
import qtx.cloud.java.exception.FeignException;

/**
 * 全局异常处理
 *
 * <p>方法顺序及捕获顺序
 *
 * @author qtx
 * @since 2022/10/29 0:05
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NullPointerException.class)
  public Result<String> nullException(NullPointerException e, HttpServletResponse response) {
    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    log.error("空指针异常");
    e.printStackTrace();
    return Result.failed("空指针异常");
  }

  @ExceptionHandler(FeignException.class)
  public Result<LocalDateTime> feignException(FeignException e, HttpServletResponse response) {
    response.setStatus(e.getCode());
    log.info(e.getMessage());
    if (StringUtils.isBlank(e.getMessage())) {
      return Result.failed(e.getDataEnums(), LocalDateTime.now());
    } else {
      return Result.failed(e.getMessage(), e.getCode(), LocalDateTime.now());
    }
  }

  @ExceptionHandler(DataException.class)
  public Result<String> dateException(DataException e) {
    log.info(e.getMessage());
    return Result.failed(e.getMessage(), e.getCode());
  }

  @ExceptionHandler(DecodeException.class)
  public Result<String> dateException(DecodeException e) {
    log.info(e.getMessage());
    return Result.failed(e.getMessage(), 406);
  }

  @ExceptionHandler(Exception.class)
  public Result<String> exception(Exception e, HttpServletResponse response) {
    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    log.error(e.getMessage());
    e.printStackTrace();
    return Result.failed(e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
  }
}
