package qtx.cloud.model.vo.activity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qtx
 * @since 2023/3/17 20:20
 */
@Data
public class AcToDoVO {

  @ApiModelProperty("流程名称")
  private String name;

  @ApiModelProperty("待办数量")
  private Integer num;
}
