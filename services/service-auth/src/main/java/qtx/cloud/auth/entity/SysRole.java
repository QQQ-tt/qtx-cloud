package qtx.cloud.auth.entity;

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
 * 角色管理
 * </p>
 *
 * @author qtx
 * @since 2022-11-17
 */
@Getter
@Setter
@TableName("sys_role")
@ApiModel(value = "SysRole对象", description = "角色管理")
public class SysRole extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("角色名称")
    @TableField("role_name")
    private String roleName;

    @ApiModelProperty("对应服务")
    @TableField("platform")
    private String platform;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("是否超级用户")
    @TableField("is_super_user")
    private Boolean superUser;
}
