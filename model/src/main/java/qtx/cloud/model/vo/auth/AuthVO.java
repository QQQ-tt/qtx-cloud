package qtx.cloud.model.vo.auth;

import qtx.cloud.java.enums.DataEnums;

/**
 * @author qtx
 * @since 2022/11/28 15:23
 */
public class AuthVO {

  private String userCode;

  private String dataEnums;

  public String getUserCode() {
    return userCode;
  }

  public void setUserCode(String userCode) {
    this.userCode = userCode;
  }

  public void setDataEnums(DataEnums dataEnums) {
    this.dataEnums = dataEnums.getMsg();
  }

  @Override
  public String toString() {
    return "AuthVO{" + "userCode='" + userCode + '\'' + ", dataEnums='" + dataEnums + '\'' + '}';
  }
}
