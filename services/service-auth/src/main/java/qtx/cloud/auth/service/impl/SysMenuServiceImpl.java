package qtx.cloud.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import qtx.cloud.auth.entity.SysMenu;
import qtx.cloud.auth.mapper.SysMenuMapper;
import qtx.cloud.auth.service.SysMenuService;
import qtx.cloud.java.enums.DataEnums;
import qtx.cloud.java.exception.DataException;
import qtx.cloud.model.dto.auth.SysMenuDTO;
import qtx.cloud.model.dto.auth.SysMenuListDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单信息表 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2022-09-21
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public List<SysMenu> listSysMenuTree(SysMenuListDTO dto) {
        List<SysMenu> list = list(Wrappers.lambdaQuery(SysMenu.class)
                .like(StringUtils.isNotBlank(dto.getName()), SysMenu::getName, dto.getName())
                .like(StringUtils.isNotBlank(dto.getCode()), SysMenu::getCode, dto.getCode()));
        return list.stream()
                .filter(f -> f.getParentId().equals(0))
                .peek(e -> e.setList(child(list, e)))
                .collect(Collectors.toList());
    }

    @Override
    public List<SysMenu> listSysMenu(String s) {
        return list(Wrappers.lambdaQuery(SysMenu.class)
                .and(StringUtils.isNotBlank(s), w -> w.like(SysMenu::getName, s).or().like(SysMenu::getCode, s)));
    }

    @Override
    public boolean saveOrUpdateNew(SysMenuDTO dto) {
        List<SysMenu> list = list(Wrappers.lambdaQuery(SysMenu.class)
                .eq(SysMenu::getCode, dto.getCode())
                .ne(dto.getMenuId() != null, SysMenu::getId, dto.getMenuId()));
        if (list.isEmpty()) {
            SysMenu menu = new SysMenu();
            BeanUtils.copyProperties(dto, menu);
            return saveOrUpdate(menu);
        }
        throw new DataException(DataEnums.DATA_IS_REPEAT);
    }

    @Override
    public boolean removeByIdNew(Integer id) {
        List<SysMenu> list = list();
        List<SysMenu> collect = list.stream()
                .filter(f -> f.getParentId().equals(id))
                .peek(e -> e.setList(child(list, e)))
                .collect(Collectors.toList());
        List<Integer> longs = new ArrayList<>();
        ids(collect, longs);
        return removeByIds(longs);
    }

    private List<SysMenu> child(List<SysMenu> list, SysMenu e) {
        return list.stream()
                .filter(f -> f.getParentId() != null)
                .filter(f -> f.getParentId().equals(e.getId()))
                .peek(p -> p.setList(child(list, p)))
                .collect(Collectors.toList());
    }

    private void ids(List<SysMenu> entity, List<Integer> ids) {
        entity.forEach(e -> {
            Integer id = e.getId();
            ids.add(id);
            if (e.getList() != null) {
                ids(e.getList(), ids);
            }
        });
    }
}
