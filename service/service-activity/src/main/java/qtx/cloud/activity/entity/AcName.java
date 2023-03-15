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
 * 单一流程名称表
 *
 * @author qtx
 * @since 2023-03-15
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ac_name")
@ApiModel(value = "AcName对象", description = "单一流程名称表")
public class AcName extends BaseEntity {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  @TableField("uuid")
  private String uuid;

  @ApiModelProperty("流程名称")
  @TableField("name")
  private String name;

  @ApiModelProperty("是否逐一初始化")
  @TableField("init_type")
  private Boolean initType;

  @ApiModelProperty("业务含义")
  @TableField("business_mean")
  private String businessMean;

  @ApiModelProperty("实际业务表名称")
  @TableField("table_name")
  private String tableName;

  @ApiModelProperty("是否为历史记录")
  @TableField("is_history")
  private Boolean history;
}
