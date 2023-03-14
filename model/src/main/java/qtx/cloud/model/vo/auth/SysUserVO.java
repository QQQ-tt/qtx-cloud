package qtx.cloud.model.vo.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;

/**
 * @author qtx
 * @since 2022/9/20
 */
@Data
public class SysUserVO implements Serializable {

  @ApiModelProperty("id")
  private Long id;

  @ApiModelProperty("工号")
  private String userCode;

  @ApiModelProperty("用户名称")
  private String userName;

  @ApiModelProperty("启用状态")
  private Boolean status;

  @ApiModelProperty("创建人")
  private String createBy;

  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private LocalDate createOn;
}
