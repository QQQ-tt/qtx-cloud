package qtx.cloud.model.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author qtx
 * @since 2022/9/21
 */
@Data
public class SysRoleMenuDTO {
  @NotNull
  @ApiModelProperty("角色")
  private String roleId;

  @NotNull private List<String> menuIdList;
}
