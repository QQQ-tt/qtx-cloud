package qtx.cloud.model.dto.auth;

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author qtx
 * @since 2022/8/31
 */
@Data
public class SysUserPasswordDTO {
  private String userCard;

  @NotNull private String newPassword;

  @NotNull private String oldPassword;
}
