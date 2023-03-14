package qtx.cloud.model.vo.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qtx
 * @since 2022/11/18 9:55
 */
@Data
public class DepartmentListVO {

  private Integer id;

  @ApiModelProperty("科室编号")
  private String departmentCode;

  @ApiModelProperty("科室名称")
  private String departmentName;
}
