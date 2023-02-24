package qtx.cloud.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import qtx.cloud.model.base.BaseEntity;

/**
 * <p>
 * 用户角色关系表
 * </p>
 *
 * @author qtx
 * @since 2022-09-07
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user_role")
public class SysUserRole extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户账号
     */
    @TableField("user_card")
    private String userCard;

    /**
     * 角色id
     */
    @TableField("role_id")
    private Integer roleId;
}


