package qtx.cloud.model.vo.auth;

import qtx.cloud.java.enums.DataEnums;

/**
 * @author qtx
 * @since 2022/11/28 15:23
 */
public class AuthVO {

  private String userCard;

  private DataEnums dataEnums;

  public String getUserCard() {
    return userCard;
  }

  public void setUserCard(String userCard) {
    this.userCard = userCard;
  }

  public void setDataEnums(DataEnums dataEnums) {
    this.dataEnums = dataEnums;
  }

  @Override
  public String toString() {
    return "AuthVO{" + "userCard='" + userCard + '\'' + ", dataEnums='" + dataEnums + '\'' + '}';
  }
}
