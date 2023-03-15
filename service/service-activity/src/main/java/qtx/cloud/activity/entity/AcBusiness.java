package qtx.cloud.activity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import qtx.cloud.model.base.BaseEntity;

/**
 * 流程节点表
 *
 * @author qtx
 * @since 2023-03-15
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ac_business")
@ApiModel(value = "AcBusiness对象", description = "流程节点表")
public class AcBusiness extends BaseEntity {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  @TableField("ac_node_id")
  private Integer acNodeId;

  @TableField("ac_name_id")
  private Integer acNameId;

  @ApiModelProperty("实际关联业务(处理人或角色)")
  @TableField("business_info")
  private String businessInfo;
}
