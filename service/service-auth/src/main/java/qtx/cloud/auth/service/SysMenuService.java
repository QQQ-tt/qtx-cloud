package qtx.cloud.auth.service;


import com.baomidou.mybatisplus.extension.service.IService;
import qtx.cloud.auth.entity.SysMenu;
import qtx.cloud.model.dto.auth.SysMenuDTO;
import qtx.cloud.model.dto.auth.SysMenuListDTO;

import java.util.List;

/**
 * <p>
 * 菜单信息表 服务类
 * </p>
 *
 * @author qtx
 * @since 2022-09-21
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 查询菜单(树)
     *
     * @param dto 查询条件
     * @return 菜单集合
     */
    List<SysMenu> listSysMenuTree(SysMenuListDTO dto);

    /**
     * 查询菜单
     *
     * @param s 查询条件
     * @return 菜单集合
     */
    List<SysMenu> listSysMenu(String s);

    /**
     * 添加或编辑菜单
     *
     * @param dto
     * @return true or false
     */
    boolean saveOrUpdateNew(SysMenuDTO dto);

    /**
     * 根据id删除
     *
     * @param id 菜单id
     * @return true or false
     */
    boolean removeByIdNew(Integer id);
}
