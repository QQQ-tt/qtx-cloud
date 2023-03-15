package qtx.cloud.gateway.utils;

import lombok.Data;
import qtx.cloud.java.enums.DataEnums;

/**
 * @author qtx
 * @since 2022/10/29 0:11
 */
@Data
public class Result<T> {
  private String msg;
  private int code;
  private T data;

  private Result(String msg, DataEnums enums) {
    this.msg = msg;
    this.code = enums.getCode();
  }

  private Result(String msg, int code) {
    this.msg = msg;
    this.code = code;
  }

  public static <T> Result<T> failed(String msg) {
    return new Result<>(msg, DataEnums.FAILED);
  }

  public static <T> Result<T> failed(String msg, int code) {
    return new Result<>(msg, code);
  }
}
