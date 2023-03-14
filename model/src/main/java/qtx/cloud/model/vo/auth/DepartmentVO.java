package qtx.cloud.model.vo.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import qtx.cloud.model.base.DateUserVO;

/**
 * @author qtx
 * @since 2022/11/18 9:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DepartmentVO extends DateUserVO {

  private Integer id;

  @ApiModelProperty("科室编号")
  private String departmentCode;

  @ApiModelProperty("科室名称")
  private String departmentName;

  @ApiModelProperty("备注")
  private String remark;
}
