package qtx.cloud.model.vo.auth;

import lombok.Data;

/**
 * @author QTX
 * @since 2022/9/20
 */
@Data
public class SysRoleVO {
  private String id;
  /** 角色id */
  private String roleId;

  /** 角色名字 */
  private String roleName;
}
