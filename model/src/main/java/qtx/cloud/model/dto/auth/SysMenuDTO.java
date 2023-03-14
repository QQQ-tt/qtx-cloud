package qtx.cloud.model.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qtx
 * @since 2022/9/21
 */
@Data
public class SysMenuDTO {

  private Integer menuId;

  @ApiModelProperty("菜单名称")
  private String name;

  @ApiModelProperty("菜单地址")
  private String path;

  @ApiModelProperty("菜单编码")
  private String code;

  @ApiModelProperty("父id")
  private Long parentId;

  @ApiModelProperty("是否为菜单")
  private Boolean menu;
}
