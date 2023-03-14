package qtx.cloud.auth.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qtx.cloud.auth.entity.SysRole;
import qtx.cloud.auth.mapper.SysRoleMapper;
import qtx.cloud.auth.service.SysRoleMenuService;
import qtx.cloud.auth.service.SysRoleService;
import qtx.cloud.java.enums.DataEnums;
import qtx.cloud.java.exception.DataException;
import qtx.cloud.model.base.BaseEntity;
import qtx.cloud.model.dto.auth.RoleDTO;
import qtx.cloud.model.dto.auth.SysRoleMenuDTO;
import qtx.cloud.model.vo.auth.RoleListVO;
import qtx.cloud.model.vo.auth.RoleVO;
import qtx.cloud.model.vo.auth.SysRoleMenuVo;
import qtx.cloud.model.vo.auth.SysRoleVO;

/**
 * 用户角色表 服务实现类
 *
 * @author qtx
 * @since 2022-09-13
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService {

  private final SysRoleMenuService roleMenuService;

  public SysRoleServiceImpl(SysRoleMenuService roleMenuService) {
    this.roleMenuService = roleMenuService;
  }

  @Override
  public IPage<RoleVO> listRolePage(RoleDTO dto) {
    return baseMapper.selectPageNew(
        dto.getPage(),
        Wrappers.lambdaQuery(SysRole.class)
            .eq(BaseEntity::getDeleteFlag, false)
            .likeRight(StringUtils.isNotBlank(dto.getName()), SysRole::getRoleName, dto.getName())
            .between(
                dto.getStarTime() != null && dto.getEndTime() != null,
                BaseEntity::getCreateOn,
                dto.getStarTime(),
                dto.getEndTime())
            .orderByDesc(BaseEntity::getCreateOn));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean saveOrUpdateNew(SysRole entity) {
    SysRole one =
        getOne(
            Wrappers.lambdaQuery(SysRole.class)
                .eq(
                    StringUtils.isNotBlank(entity.getRoleName()),
                    SysRole::getRoleName,
                    entity.getRoleName())
                .ne(entity.getId() != null, SysRole::getId, entity.getId()));
    if (Objects.isNull(one)) {
      saveOrUpdate(entity);
      return updateSuper(entity.getId(), entity.getSuperUser());
    } else {
      throw new DataException(DataEnums.DATA_INSERT_FAIL);
    }
  }

  @Override
  public boolean updateSuper(Integer id, boolean flag) {
    return update(
        Wrappers.lambdaUpdate(SysRole.class)
            .set(SysRole::getSuperUser, flag)
            .eq(SysRole::getId, id));
  }

  @Override
  public List<SysRoleVO> listAll() {
    return baseMapper.listVo();
  }

  @Override
  public List<RoleListVO> listRole(RoleDTO dto) {
    return baseMapper.selectNew(
        Wrappers.lambdaQuery(SysRole.class)
            .likeRight(StringUtils.isNotBlank(dto.getName()), SysRole::getRoleName, dto.getName()));
  }

  @Override
  public RoleVO getOneById(Integer id) {
    SysRole byId = getById(id);
    RoleVO vo = new RoleVO();
    BeanUtils.copyProperties(byId, vo);
    return vo;
  }

  @Override
  public boolean addAuthMenu(SysRoleMenuDTO dto) {
    return roleMenuService.saveOrUpdateNew(dto);
  }

  @Override
  public List<SysRoleMenuVo> getMenuByRole(Integer id) {
    return roleMenuService.getMenuByRole(id);
  }

  @Override
  public List<SysRoleMenuVo> getMenuByRole() {
    return roleMenuService.getMenuByRole();
  }
}
