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

/**
 * <p>
 * 流程节点表
 * </p>
 *
 * @author qtx
 * @since 2023-03-11
 */
@Getter
@Setter
@TableName("ac_node")
@ApiModel(value = "AcNode对象", description = "流程节点表")
public class AcNode extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("ac_name_id")
    private Integer acNameId;

    @ApiModelProperty("节点名称")
    @TableField("node_name")
    private String nodeName;

    @ApiModelProperty("节点组合")
    @TableField("node_group")
    private Integer nodeGroup;

    @ApiModelProperty("当前节点组所需通过数量")
    @TableField("node_pass_num")
    private Integer nodePassNum;
}
