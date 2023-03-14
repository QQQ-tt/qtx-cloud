package qtx.cloud.model.dto.activity;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import lombok.Data;
import qtx.cloud.java.constant.StaticConstant;

/**
 * @author qtx
 * @since 2023/3/11 10:57
 */
@Data
public class ActivityDTO {

  private Integer id;

  @NotNull
  @Max(value = StaticConstant.STRING_MAX_SIZE, message = StaticConstant.STRING_SIZ_ERROR)
  @ApiModelProperty(value = "审批流名称", required = true)
  private String name;

  @ApiModelProperty(value = "初始化类型", required = true)
  private Boolean initType;

  @NotNull
  @Max(value = StaticConstant.STRING_MAX_SIZE, message = StaticConstant.STRING_SIZ_ERROR)
  @ApiModelProperty(value = "实际业务含义", required = true)
  private String businessMean;

  @ApiModelProperty("节点")
  private List<ActivityNodeDTO> list;
}
