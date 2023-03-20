package qtx.cloud.model.vo.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 * @author qtx
 * @since 2023/3/17 20:40
 */
@Data
public class TaskVO {
  @JsonIgnore private String acUuid;
  @JsonIgnore private String acNameUuid;
  @JsonIgnore private Boolean pFlag;

  @ApiModelProperty("节点名称")
  private String nodeName;

  @ApiModelProperty("节点组编号")
  private Integer nodeGroup;

  @ApiModelProperty("业务名称")
  private String business;

  @ApiModelProperty("提交时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime submissionTime;

  @ApiModelProperty("通过时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime passTime;

  @ApiModelProperty("本节点结果")
  private Boolean flag;

  @ApiModelProperty("本次操作结果")
  private Boolean thisFlag;

  @ApiModelProperty("状态")
  private String status;

  @ApiModelProperty("状态详情")
  private String statusInfo;

  private List<TaskVO> list;
}
