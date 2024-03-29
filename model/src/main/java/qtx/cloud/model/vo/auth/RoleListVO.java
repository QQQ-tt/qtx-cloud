package qtx.cloud.model.vo.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qtx
 * @since 2022/11/18 10:20
 */
@Data
public class RoleListVO {

  private Integer id;

  @ApiModelProperty("角色名称")
  private String roleName;
}
