package qtx.cloud.auth.mapper;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import qtx.cloud.auth.entity.SysRoleMenu;
import qtx.cloud.model.vo.auth.SysRoleMenuVo;

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

    /**
     * 查询菜单
     *
     * @param queryWrapper
     * @return
     */
    List<SysRoleMenuVo> selectByRoleId(@Param(Constants.WRAPPER) LambdaQueryWrapper<SysRoleMenu> queryWrapper);
}
