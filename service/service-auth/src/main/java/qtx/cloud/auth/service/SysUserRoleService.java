package qtx.cloud.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import qtx.cloud.auth.entity.SysUserRole;
import qtx.cloud.model.dto.auth.UserRolesDTO;

/**
 * 用户角色关系表 服务类
 *
 * @author qtx
 * @since 2022-09-07
 */
public interface SysUserRoleService extends IService<SysUserRole> {

  /**
   * 赋予用户对应的角色
   *
   * @param dto 用户card和对应角色的id集合
   */
  void addRoleWithUser(UserRolesDTO dto);

  /**
   * 通过用户账户获取角色
   *
   * @param userCard 账户
   * @return 角色字符
   */
  String getRoleByUser(String userCard);

  /**
   * 通过用户账户获取角色id集合
   *
   * @param userCard 账户
   * @return 角色集合
   */
  List<SysUserRole> listRoleByUser(String userCard);
}
