package qtx.cloud.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import qtx.cloud.auth.entity.SysUserRole;
import qtx.cloud.auth.mapper.SysUserRoleMapper;
import qtx.cloud.auth.service.SysUserRoleService;
import qtx.cloud.model.dto.auth.UserRolesDTO;

/**
 * 用户角色关系表 服务实现类
 *
 * @author qtx
 * @since 2022-09-07
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole>
    implements SysUserRoleService {

  @Override
  public void addRoleWithUser(UserRolesDTO dto) {
    remove(
        Wrappers.lambdaUpdate(SysUserRole.class).eq(SysUserRole::getUserCard, dto.getUserCode()));
    List<SysUserRole> list = new ArrayList<>();
    dto.getRoleIds()
        .forEach(
            e -> list.add(SysUserRole.builder().userCard(dto.getUserCode()).roleId(e).build()));
    saveOrUpdateBatch(list);
  }

  @Override
  public String getRoleByUser(String card) {
    List<String> strings = baseMapper.selectRoleByUserCard(card);
    StringBuilder builder = new StringBuilder();
    if (strings.size() == 0) {
      return "";
    }
    for (int i = 0; ; i++) {
      builder.append(strings.get(i));
      if (i == strings.size() - 1) {
        return builder.toString();
      }
      builder.append(",");
    }
  }

  @Override
  public List<SysUserRole> listRoleByUser(String card) {
    return list(Wrappers.lambdaQuery(SysUserRole.class).eq(SysUserRole::getUserCard, card));
  }
}
