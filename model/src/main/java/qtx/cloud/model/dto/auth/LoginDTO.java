package qtx.cloud.model.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author qtx
 * @since 2022/11/21 16:13
 */
@Data
public class LoginDTO {

  @NotNull
  @ApiModelProperty("工号")
  private String userCode;

  @NotNull
  @ApiModelProperty("密码")
  private String password;

  @NotNull
  @ApiModelProperty("验证码")
  private String authCode;

  @NotNull
  @ApiModelProperty("uuid")
  private String session;
}
