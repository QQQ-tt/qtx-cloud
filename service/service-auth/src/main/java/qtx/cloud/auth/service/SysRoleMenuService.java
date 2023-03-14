package qtx.cloud.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import qtx.cloud.auth.entity.SysRoleMenu;
import qtx.cloud.model.dto.auth.SysRoleMenuDTO;
import qtx.cloud.model.vo.auth.SysRoleMenuVo;

/**
 * 角色菜单关系表 服务类
 *
 * @author qtx
 * @since 2022-09-21
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {
  /**
   * 获取登录角色菜单
   *
   * @return 菜单集合（树）
   */
  List<SysRoleMenuVo> getMenuByRole();

  /**
   * 通过角色id获取权限
   *
   * @param role 角色id
   * @return 菜单集合（树）
   */
  List<SysRoleMenuVo> getMenuByRole(Integer role);

  /**
   * 为角色添加菜单
   *
   * @param dto 角色菜单
   * @return true or false
   */
  boolean saveOrUpdateNew(SysRoleMenuDTO dto);
}
