package qtx.cloud.auth.mapper;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import qtx.cloud.auth.entity.SysRoleMenu;
import qtx.cloud.model.vo.auth.SysRoleMenuVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色菜单关系表 Mapper 接口
 * </p>
 *
 * @author qtx
 * @since 2022-09-21
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    List<SysRoleMenuVo> selectByRoleId(@Param(Constants.WRAPPER) LambdaQueryWrapper<SysRoleMenu> queryWrapper);
}
