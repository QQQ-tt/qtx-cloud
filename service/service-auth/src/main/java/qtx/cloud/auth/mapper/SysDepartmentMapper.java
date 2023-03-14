package qtx.cloud.auth.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import qtx.cloud.auth.entity.SysDepartment;
import qtx.cloud.model.vo.auth.DepartmentListVO;
import qtx.cloud.model.vo.auth.DepartmentVO;

/**
 * 科室管理表 Mapper 接口
 *
 * @author qtx
 * @since 2022-11-17
 */
@Mapper
public interface SysDepartmentMapper extends BaseMapper<SysDepartment> {

  /**
   * 分页查询科室
   *
   * @param page 分页参数
   * @param queryWrapper 查询条件
   * @return 分页集合
   */
  IPage<DepartmentVO> selectPageNew(
      IPage<Object> page, @Param(Constants.WRAPPER) LambdaQueryWrapper<SysDepartment> queryWrapper);

  /**
   * 查询科室
   *
   * @param queryWrapper 查询条件
   * @return 集合
   */
  List<DepartmentListVO> selectNew(
      @Param(Constants.WRAPPER) LambdaQueryWrapper<SysDepartment> queryWrapper);
}
