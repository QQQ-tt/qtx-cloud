package qtx.cloud.auth.service;


import com.baomidou.mybatisplus.extension.service.IService;
import qtx.cloud.auth.entity.SysRoleMenu;
import qtx.cloud.model.dto.auth.SysRoleMenuDTO;
import qtx.cloud.model.vo.auth.SysRoleMenuVo;

import java.util.List;

/**
 * <p>
 * 角色菜单关系表 服务类
 * </p>
 *
 * @author qtx
 * @since 2022-09-21
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {
    /**
     * 获取登录角色菜单
     *
     * @return
     */
    List<SysRoleMenuVo> getMenuByRole();

    /**
     * 通过角色id获取权限
     * @param role
     * @return
     */
    List<SysRoleMenuVo> getMenuByRole(Long role);

    /**
     * 为角色添加菜单
     *
     * @param dto
     * @return
     */
    boolean saveOrUpdateNew(SysRoleMenuDTO dto);
}
