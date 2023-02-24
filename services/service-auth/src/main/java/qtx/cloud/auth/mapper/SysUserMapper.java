package qtx.cloud.auth.mapper;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import qtx.cloud.auth.entity.SysUser;
import qtx.cloud.model.dto.auth.SysUserDepartmentDTO;
import qtx.cloud.model.vo.auth.SysUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author qtx
 * @since 2022-08-30
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 分页查询
     *
     * @param page         分页参数
     * @param queryWrapper 查询条件
     * @return 分页集合
     */
    Page<SysUserVO> selectPageNew(IPage<Object> page,
                                  @Param(Constants.WRAPPER) LambdaQueryWrapper<SysUser> queryWrapper);

    /**
     * 根据科室提前排序
     *
     * @param page 分页参数
     * @param dto  科室
     * @return
     */
    Page<SysUserVO> selectPageByDepartment(IPage<Object> page, @Param("dto") SysUserDepartmentDTO dto);


}
