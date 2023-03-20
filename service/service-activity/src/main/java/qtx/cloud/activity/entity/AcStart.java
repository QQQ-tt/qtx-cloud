package qtx.cloud.activity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;
import qtx.cloud.model.base.BaseEntity;

/**
 * 流程启动表
 *
 * @author qtx
 * @since 2023-03-16
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ac_start")
@ApiModel(value = "AcStart对象", description = "流程启动表")
public class AcStart extends BaseEntity {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  @ApiModelProperty("流程名称")
  @TableField("ac_name")
  private String acName;

  @ApiModelProperty("节点名称")
  @TableField("ac_node")
  private String acNode;

  @ApiModelProperty("节点组编号")
  @TableField("ac_node_group")
  private Integer acNodeGroup;

  @ApiModelProperty("实际关联业务(处理人或角色)")
  @TableField("ac_business")
  private String acBusiness;

  @ApiModelProperty("流程uuid")
  @TableField("ac_uuid")
  private String acUuid;

  @ApiModelProperty("流程任务uuid")
  @TableField("task_uuid")
  private String taskUuid;

  @ApiModelProperty("流程任务节点uuid")
  @TableField("task_node_uuid")
  private String taskNodeUuid;

  @ApiModelProperty("父流程任务节点uuid")
  @TableField("parent_task_node_uuid")
  private String parentTaskNodeUuid;

  @ApiModelProperty("提交日期")
  @TableField("submission_time")
  private LocalDateTime submissionTime;

  @ApiModelProperty("审核进度")
  @TableField("review_progress")
  private BigDecimal reviewProgress;

  @ApiModelProperty("审核通过日期")
  @TableField("pass_time")
  private LocalDateTime passTime;

  @ApiModelProperty("当前节点结果")
  @TableField("flag")
  private Boolean flag;

  @ApiModelProperty("当前节点的本次操作结果")
  @TableField("this_flag")
  private Boolean thisFlag;

  @ApiModelProperty("当前节点所需通过总数")
  @TableField("node_pass_num")
  private Integer nodePassNum;

  @ApiModelProperty("当前节点已通过数量")
  @TableField("this_node_pass_num")
  private Integer thisNodePassNum;

  @ApiModelProperty("审核状态")
  @TableField("status")
  private String status;

  @ApiModelProperty("审核状态详情")
  @TableField("status_info")
  private String statusInfo;

  @ApiModelProperty("节点是否开启")
  @TableField("is_node")
  private Boolean node;

  @ApiModelProperty("是否隐藏")
  @TableField("is_hidden")
  private Boolean hidden;

  @ApiModelProperty("是否为历史记录")
  @TableField("is_his")
  private Boolean his;

  @ApiModelProperty("文件uuid")
  @TableField("file_uuid")
  private String fileUuid;

  @ApiModelProperty("备注")
  @TableField("remark")
  private String remark;
}
