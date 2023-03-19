package qtx.cloud.auth.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import qtx.cloud.auth.entity.SysUser;
import qtx.cloud.model.bo.auth.UserBO;
import qtx.cloud.model.dto.auth.SysUserDepartmentDTO;
import qtx.cloud.model.vo.auth.SysUserVO;

/**
 * Mapper 接口
 *
 * @author qtx
 * @since 2022-08-30
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
  /**
   * 分页查询
   *
   * @param page 分页参数
   * @param queryWrapper 查询条件
   * @return 分页集合
   */
  Page<SysUserVO> selectPageNew(
      IPage<Object> page, @Param(Constants.WRAPPER) LambdaQueryWrapper<SysUser> queryWrapper);

  /**
   * 根据科室提前排序
   *
   * @param page 分页参数
   * @param dto 科室
   * @return 用户信息
   */
  Page<SysUserVO> selectPageByDepartment(
      IPage<Object> page, @Param("dto") SysUserDepartmentDTO dto);

  /**
   * 项目启动初始化redis数据
   *
   * @return 用户基本信息集合
   */
  List<UserBO> selectAll();

  /**
   * 获取用户全部信息
   *
   * @param userCode 用户工号
   * @return 用户信息
   */
  SysUserVO selectUserByUserCode(String userCode);
}
