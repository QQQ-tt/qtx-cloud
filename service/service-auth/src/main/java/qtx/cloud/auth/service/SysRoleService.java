package qtx.cloud.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import qtx.cloud.auth.entity.SysRole;
import qtx.cloud.model.dto.auth.RoleDTO;
import qtx.cloud.model.dto.auth.SysRoleMenuDTO;
import qtx.cloud.model.vo.auth.RoleListVO;
import qtx.cloud.model.vo.auth.RoleVO;
import qtx.cloud.model.vo.auth.SysRoleMenuVo;

/**
 * 用户角色表 服务类
 *
 * @author qtx
 * @since 2022-09-13
 */
public interface SysRoleService extends IService<SysRole> {

  /**
   * 分页查询角色信息
   *
   * @param dto 查询条件
   * @return 分页合集
   */
  IPage<RoleVO> listRolePage(RoleDTO dto);

  /**
   * 新建或更新
   *
   * @param sysRole 角色实体
   * @return true or false
   */
  boolean saveOrUpdateNew(SysRole sysRole);

  /**
   * 更新为超级用户状态
   *
   * @param id 角色id
   * @param flag true or false
   * @return true or false
   */
  boolean updateSuper(Integer id, Boolean flag);

  /**
   * 查询角色
   *
   * @param name 查询条件
   * @return 集合
   */
  List<RoleListVO> listRole(String name);

  /**
   * 获取角色
   *
   * @param id 角色id
   * @return 角色
   */
  RoleVO getRoleById(Integer id);

  /**
   * 为角色赋予权限
   *
   * @param dto 权限
   * @return true or false
   */
  boolean addAuthMenu(SysRoleMenuDTO dto);

  /**
   * 通过角色获取权限
   *
   * @param id 角色id
   * @return 角色权限集合
   */
  List<SysRoleMenuVo> getMenuByRole(Integer id);

  /**
   * 获取登录人通过角色获取权限
   *
   * @return 角色权限集合
   */
  List<SysRoleMenuVo> getMenuByRole();
}
