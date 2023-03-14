package qtx.cloud.activity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import qtx.cloud.model.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * <p>
 * 流程启动表
 * </p>
 *
 * @author qtx
 * @since 2023-03-11
 */
@Getter
@Setter
@TableName("ac_start")
@ApiModel(value = "AcStart对象", description = "流程启动表")
public class AcStart extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("ac_name_id")
    private Integer acNameId;

    @TableField("ac_node_id")
    private Integer acNodeId;

    @TableField("ac_business_id")
    private Integer acBusinessId;

    @ApiModelProperty("提交日期")
    @TableField("submission_time")
    private LocalDateTime submissionTime;

    @ApiModelProperty("审核进度")
    @TableField("review_progress")
    private Object reviewProgress;

    @ApiModelProperty("审核通过日期")
    @TableField("pass_time")
    private LocalDateTime passTime;

    @ApiModelProperty("当前节点结果")
    @TableField("flag")
    private String flag;

    @ApiModelProperty("当前节点的本次操作结果")
    @TableField("this_flag")
    private Boolean thisFlag;

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

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("文件uuid")
    @TableField("file_uuid")
    private String fileUuid;
}
