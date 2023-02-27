package qtx.cloud.auth.mapper;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import qtx.cloud.auth.entity.SysRole;
import qtx.cloud.model.vo.auth.RoleListVO;
import qtx.cloud.model.vo.auth.RoleVO;
import qtx.cloud.model.vo.auth.SysRoleVO;

import java.util.List;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author qtx
 * @since 2022-09-13
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRoleVO> listVo();

    /**
     * 分页查询角色
     * @param page 分页参数
     * @param queryWrapper 查询条件
     * @return 分页集合
     */
    IPage<RoleVO> selectPageNew(IPage<Object> page, @Param(Constants.WRAPPER) LambdaQueryWrapper<SysRole> queryWrapper);

    /**
     * 查询角色
     * @param queryWrapper 查询条件
     * @return 集合
     */
    List<RoleListVO> selectNew(@Param(Constants.WRAPPER) LambdaQueryWrapper<SysRole> queryWrapper);
}
