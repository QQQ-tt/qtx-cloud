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
 * 用户表
 *
 * @author qtx
 * @since 2022-11-21
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
@ApiModel(value = "SysUser对象", description = "用户表")
public class SysUser extends BaseEntity {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  @ApiModelProperty("工号")
  @TableField("user_code")
  private String userCode;

  @ApiModelProperty("用户名称")
  @TableField("user_name")
  private String userName;

  @ApiModelProperty("密码")
  @TableField("password")
  private String password;

  @ApiModelProperty("启用状态")
  @TableField("status")
  private Boolean status;
}
