package qtx.cloud.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import qtx.cloud.model.base.BaseEntity;

/**
 * 角色菜单关系表
 *
 * @author qtx
 * @since 2022-09-21
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role_menu")
public class SysRoleMenu extends BaseEntity {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /** 角色id */
  @TableField("role_id")
  private String roleId;

  /** 菜单id */
  @TableField("menu_id")
  private String menuId;

  /** 状态 */
  @TableField("status")
  private Boolean status;
}
