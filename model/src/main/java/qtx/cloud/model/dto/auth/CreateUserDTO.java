package qtx.cloud.model.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author qtx
 * @since 2022/11/21 16:18
 */
@Data
public class CreateUserDTO {

  @NotNull
  @ApiModelProperty("用户名")
  private String userName;

  @NotNull
  @ApiModelProperty("密码")
  private String password;

  /** 用户账户 */
  @JsonIgnore private String userCard;
}
