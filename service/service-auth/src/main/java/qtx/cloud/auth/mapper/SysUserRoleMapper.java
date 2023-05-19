package qtx.cloud.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import qtx.cloud.auth.entity.SysUserRole;

/**
 * 用户角色关系表 Mapper 接口
 *
 * @author qtx
 * @since 2022-09-07
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
  /**
   * 通过用户card获取角色
   *
   * @param userCode 用户账户
   * @return 角色名称集合
   */
  List<String> selectRoleByUserCard(String userCode);
}
