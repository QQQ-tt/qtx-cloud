package qtx.cloud.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qtx.cloud.auth.entity.SysMenu;
import qtx.cloud.auth.entity.SysRoleMenu;
import qtx.cloud.auth.entity.SysUserRole;
import qtx.cloud.auth.mapper.SysRoleMenuMapper;
import qtx.cloud.auth.service.SysMenuService;
import qtx.cloud.auth.service.SysRoleMenuService;
import qtx.cloud.auth.service.SysUserRoleService;
import qtx.cloud.java.enums.DataEnums;
import qtx.cloud.java.exception.DataException;
import qtx.cloud.model.base.BaseEntity;
import qtx.cloud.model.dto.auth.SysRoleMenuDTO;
import qtx.cloud.model.vo.auth.SysRoleMenuVo;
import qtx.cloud.service.utils.CommonMethod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色菜单关系表 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2022-09-21
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    private final SysMenuService sysMenuService;

    private final CommonMethod commonMethod;

    private final SysUserRoleService sysUserRoleService;

    public SysRoleMenuServiceImpl(SysMenuService sysMenuService, CommonMethod commonMethod,
                                  SysUserRoleService sysUserRoleService) {
        this.sysMenuService = sysMenuService;
        this.commonMethod = commonMethod;
        this.sysUserRoleService = sysUserRoleService;
    }

    @Override
    public List<SysRoleMenuVo> getMenuByRole() {
        String user = commonMethod.getUser();
        List<Integer> roleByUser = sysUserRoleService.listRoleByUser(user)
                .stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toList());
        if (roleByUser.isEmpty()) {
            return new ArrayList<>();
        }
        List<SysRoleMenuVo> sysRoleMenuVos = baseMapper.selectByRoleId(Wrappers.lambdaQuery(SysRoleMenu.class)
                .eq(BaseEntity::getDeleteFlag, false)
                .in(SysRoleMenu::getRoleId, roleByUser));
        return sysRoleMenuVos.stream()
                .filter(f -> f.getParentId().equals(0))
                .peek(e -> e.setList(child(sysRoleMenuVos, e)))
                .collect(Collectors.toList());
    }

    @Override
    public List<SysRoleMenuVo> getMenuByRole(Integer role) {
        List<SysRoleMenuVo> sysRoleMenuVos = baseMapper.selectByRoleId(Wrappers.lambdaQuery(SysRoleMenu.class)
                .eq(BaseEntity::getDeleteFlag, false)
                .eq(SysRoleMenu::getRoleId, role));
        return sysRoleMenuVos.stream()
                .filter(f -> f.getParentId().equals(0))
                .peek(e -> e.setList(child(sysRoleMenuVos, e)))
                .collect(Collectors.toList());
    }

    private List<SysRoleMenuVo> child(List<SysRoleMenuVo> list, SysRoleMenuVo e) {
        return list.stream()
                .filter(f -> f.getParentId() != null)
                .filter(f -> f.getParentId().equals(e.getId()))
                .peek(p -> p.setList(child(list, p)))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdateNew(SysRoleMenuDTO dto) {
        remove(Wrappers.lambdaUpdate(SysRoleMenu.class).eq(SysRoleMenu::getRoleId, dto.getRoleId()));
        List<SysRoleMenu> list = new ArrayList<>();
        List<String> menuIdList = dto.getMenuIdList();
        if (menuIdList == null || menuIdList.isEmpty()) {
            throw new DataException(DataEnums.DATA_IS_ABNORMAL);
        }
        Set<String> set = new HashSet<>(menuIdList);
        List<SysMenu> sysMenus = sysMenuService.listSysMenu(null);
        List<Integer> collect = sysMenus.stream()
                .map(SysMenu::getId)
                .filter(menuId -> !set.contains(String.valueOf(menuId)))
                .collect(Collectors.toList());
        menuIdList.forEach(e -> list.add(SysRoleMenu.builder()
                .menuId(e)
                .roleId(dto.getRoleId())
                .status(Boolean.TRUE)
                .build()));
        collect.forEach(e -> list.add(SysRoleMenu.builder()
                .menuId(String.valueOf(e))
                .roleId(dto.getRoleId())
                .status(Boolean.FALSE)
                .build()));
        return saveBatch(list);
    }
}
