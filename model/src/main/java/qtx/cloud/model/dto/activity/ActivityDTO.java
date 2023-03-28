package qtx.cloud.model.dto.activity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import qtx.cloud.java.constant.StaticConstant;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author qtx
 * @since 2023/3/11 10:57
 */
@Data
public class ActivityDTO {

  private Integer id;

  @NotNull
  @Size(max = StaticConstant.STRING_MAX_SIZE, message = StaticConstant.STRING_SIZ_ERROR)
  @ApiModelProperty(value = "审批流名称", required = true)
  private String name;

  @ApiModelProperty(value = "初始化类型", required = true)
  private Boolean initType;

  @NotNull
  @Size(max = StaticConstant.STRING_MAX_SIZE, message = StaticConstant.STRING_SIZ_ERROR)
  @ApiModelProperty(value = "实际业务含义", required = true)
  private String businessMean;

  @NotNull
  @Size(max = StaticConstant.STRING_MAX_SIZE, message = StaticConstant.STRING_SIZ_ERROR)
  @ApiModelProperty("实际业务表名称")
  private String tableName;

  @ApiModelProperty("节点")
  private List<ActivityNodeDTO> list;
}
