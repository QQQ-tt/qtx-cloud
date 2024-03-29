package qtx.cloud.java.enums;

/**
 * @author qtx
 * @since 2022/10/29 0:00
 */
public enum DataEnums {
  /** 成功 */
  SUCCESS("成功", 200),
  /** 失败 */
  FAILED("失败", 500),
  /** 服务调用失败 */
  SERVICE_FAILED("服务调用失败", 503),
  /** 密码错误 */
  WRONG_PASSWORD("密码错误", 205),
  /** 无法访问 */
  ACCESS_DENIED("无法访问", 403),
  /** 通过网关访问 */
  GATEWAY_TRANSBOUNDARY("通过网关访问", 407),
  /** 登录已过期 */
  USER_LOGIN_EXPIRED("登录已过期", 401),
  /** 验证码已过期 */
  AUTH_CODE_EXPIRED("验证码已过期", 201),
  /** token不存在 */
  TOKEN_IS_NULL("token不存在", 201),
  /** 账户已锁定5分钟 */
  USER_LOGIN_LOCKING("账户已锁定5分钟", 403),
  /** 用户未登录 */
  USER_NOT_LOGIN("用户未登录", 201),
  /** 用户验证失败 */
  USER_IS_FAIL("用户验证失败", 201),
  /** 用户不存在 */
  USER_IS_NULL("用户不存在", 201),
  /** 验证码错误 */
  USER_CODE_FAIL("验证码错误", 205),
  /** 用户角色为空 */
  USER_ROLE_NULL("用户角色为空", 201),
  /** 用户删除失败 */
  USER_REMOVE_FAIL("用户删除失败", 201),
  /** 服务编号不匹配 */
  PLATFORM_IS_FAIL("服务编号不匹配", 205),
  /** 入参数据异常 */
  DATA_IS_ABNORMAL("入参数据异常", 205),
  /** 流程不存在 */
  DATA_AC_NULL("流程不存在", 205),
  /** 数据插入失败 */
  DATA_INSERT_FAIL("数据插入失败", 500),
  /** 数据更新失败 */
  DATA_UPDATE_FAIL("数据更新失败", 500),
  /** 数据重复 */
  DATA_IS_REPEAT("数据重复", 201);

  /** 提示 */
  private final String msg;
  /** 错误编码 */
  private final int code;

  DataEnums(String msg, int code) {
    this.msg = msg;
    this.code = code;
  }

  @Override
  public String toString() {
    return this.msg;
  }

  public int getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }
}
