package qtx.cloud.model.dto.activity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qtx
 * @since 2023/3/15 13:59
 */
@Data
public class ActivityBusinessDTO {

  @ApiModelProperty("实际关联业务(处理人或角色)")
  private String businessInfo;
}
