package qtx.cloud.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import qtx.cloud.model.base.BaseEntity;

/**
 * 用户信息扩展表
 *
 * @author qtx
 * @since 2023-02-27
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user_info")
@ApiModel(value = "SysUserInfo对象", description = "用户信息扩展表")
public class SysUserInfo extends BaseEntity {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  @ApiModelProperty("用户账户")
  @TableField("user_card")
  private String userCard;

  @ApiModelProperty("科室id")
  @TableField("department_id")
  private Integer departmentId;
}
