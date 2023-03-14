package qtx.cloud.auth.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import qtx.cloud.auth.entity.SysRoleMenu;
import qtx.cloud.model.vo.auth.SysRoleMenuVo;

/**
 * 角色菜单关系表 Mapper 接口
 *
 * @author qtx
 * @since 2022-09-21
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

  /**
   * 查询菜单
   *
   * @param queryWrapper 查询条件
   * @return 菜单集合
   */
  List<SysRoleMenuVo> selectByRoleId(
      @Param(Constants.WRAPPER) LambdaQueryWrapper<SysRoleMenu> queryWrapper);
}
