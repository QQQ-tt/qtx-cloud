package qtx.cloud.model.dto.activity;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author qtx
 * @since 2023/3/11 11:00
 */
@Data
public class ActivityNodeDTO {

  @NotNull
  @ApiModelProperty("节点名称")
  private String nodeName;

  @NotNull
  @ApiModelProperty("节点组编号")
  private Integer nodeGroup;

  @NotNull
  @ApiModelProperty("当前节点组所需通过数量")
  private Integer nodePassNum;

  @NotNull
  @ApiModelProperty("实际关联业务")
  private String businessInfo;
}
