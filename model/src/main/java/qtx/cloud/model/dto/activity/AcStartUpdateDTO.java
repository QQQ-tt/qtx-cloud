package qtx.cloud.model.dto.activity;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qtx
 * @since 2023/3/17 11:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcStartUpdateDTO {
  @NotNull
  @ApiModelProperty("任务uuid")
  private String taskUuid;

  @NotNull
  @ApiModelProperty("本次选择结果")
  private Boolean thisFlag;

  @ApiModelProperty("状态")
  private String status;

  @ApiModelProperty("状态详情")
  private String statusInfo;

  @ApiModelProperty("初始化当前进度")
  private Boolean initializeNode;

  @ApiModelProperty("初始化当前流程")
  private Boolean initializeAc;

  @ApiModelProperty("文件uuid")
  private String fileUuid;

  @ApiModelProperty("备注")
  private String remark;
}
