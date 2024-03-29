package qtx.cloud.model.dto.activity;

import io.swagger.annotations.ApiModelProperty;
import java.util.Set;
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

  @ApiModelProperty("当前节点组所需通过数量")
  private Integer nodePassNum;

  @ApiModelProperty("节点是否隐藏")
  private Boolean hidden;

  @ApiModelProperty("节点类型，是否为基础节点")
  private Boolean nodeType;

  @ApiModelProperty("节点对应的流程uuid")
  private String acNameUuid;

  @ApiModelProperty("实际关联业务(处理人或角色等)")
  private Set<String> stringSet;
}
