package qtx.cloud.model.bo.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qtx
 * @since 2023/2/26 19:10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBO {

  private Integer id;

  @ApiModelProperty("账户")
  private String userCard;

  @ApiModelProperty("用户名称")
  private String userName;

  @ApiModelProperty("密码")
  private String password;
}
