package qtx.cloud.model.dto.activity;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
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

  @ApiModelProperty("节点是否隐藏")
  private Boolean hidden;

  @ApiModelProperty("实际业务信息集合")
  private List<ActivityBusinessDTO> list;
}
