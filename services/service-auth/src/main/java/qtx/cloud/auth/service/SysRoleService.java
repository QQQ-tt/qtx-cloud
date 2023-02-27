package qtx.cloud.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import qtx.cloud.auth.entity.SysRole;
import qtx.cloud.model.dto.auth.RoleDTO;
import qtx.cloud.model.dto.auth.SysRoleMenuDTO;
import qtx.cloud.model.vo.auth.RoleListVO;
import qtx.cloud.model.vo.auth.RoleVO;
import qtx.cloud.model.vo.auth.SysRoleMenuVo;
import qtx.cloud.model.vo.auth.SysRoleVO;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
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
     * @param id   角色id
     * @param flag
     * @return true or false
     */
    boolean updateSuper(Integer id, boolean flag);

    /**
     * 获取全部角色
     *
     * @return 角色集合
     */
    List<SysRoleVO> listAll();

    /**
     * 查询角色
     *
     * @param dto 查询条件
     * @return 集合
     */
    List<RoleListVO> listRole(RoleDTO dto);

    /**
     * 通过角色id获取角色
     * @param id
     * @return
     */
    RoleVO getOneById(Integer id);

    /**
     * 为角色赋予权限
     * @param dto 权限
     * @return true or false
     */
    boolean addAuthMenu(SysRoleMenuDTO dto);

    /**
     * 通过角色获取权限
     * @param id
     * @return
     */
    List<SysRoleMenuVo> getMenuByRole(Integer id);

    /**
     * 获取登录人通过角色获取权限
     * @return
     */
    List<SysRoleMenuVo> getMenuByRole();
}
