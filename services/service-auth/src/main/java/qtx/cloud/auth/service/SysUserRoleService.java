package qtx.cloud.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import qtx.cloud.auth.entity.SysUserRole;
import qtx.cloud.model.dto.auth.UserRolesDTO;

import java.util.List;


/**
 * <p>
 * 用户角色关系表 服务类
 * </p>
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
     * 通过用户card获取角色
     *
     * @param card
     * @return
     */
    String getRoleByUser(String card);

    /**
     * 通过用户获取角色id集合
     * @param card
     * @return
     */
    List<SysUserRole> listRoleByUser(String card);
}
