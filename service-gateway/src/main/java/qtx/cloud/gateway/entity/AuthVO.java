package qtx.cloud.gateway.entity;

import lombok.Data;
import qtx.cloud.java.enums.DataEnums;

/**
 * @author qtx
 * @since 2022/11/28 15:23
 */
@Data
public class AuthVO {

  private String userCode;

  private DataEnums dataEnums;

  private Boolean flag;
}
