package qtx.cloud.java.enums;
/**
 * @author qtx
 * @since 2023/4/1 23:55
 */
public enum ExcelMapAllEnum {
  /**
   * 空
   */
  NULL("NULL"),
  /** 用户表excel映射 */
  USER_ENUM("qtx.cloud.java.enums.UserEnum");

  /** 枚举类全限定地址 */
  private final String enumPath;

  ExcelMapAllEnum(String s) {
    enumPath = s;
  }

  public String getEnumPath() {
    return enumPath;
  }
}
