package qtx.cloud.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import qtx.cloud.auth.entity.SysDepartment;
import qtx.cloud.model.dto.auth.DepartmentDTO;
import qtx.cloud.model.vo.auth.DepartmentListVO;
import qtx.cloud.model.vo.auth.DepartmentVO;

/**
 * 科室管理表 服务类
 *
 * @author qtx
 * @since 2022-11-17
 */
public interface SysDepartmentService extends IService<SysDepartment> {

  /**
   * 分页查询科室信息
   *
   * @param dto 查询条件
   * @return 分页合集
   */
  IPage<DepartmentVO> listDepartmentPage(DepartmentDTO dto);

  /**
   * 更新或新增
   *
   * @param entity 实体
   * @return true or false
   */
  boolean saveOrUpdateNew(SysDepartment entity);

  /**
   * 科室查询
   *
   * @param name 科室名称
   * @return 集合
   */
  List<DepartmentListVO> listDepartment(String name);

  /**
   * 导出
   *
   * @param response
   */
  void downloadExcel(HttpServletResponse response);
}
