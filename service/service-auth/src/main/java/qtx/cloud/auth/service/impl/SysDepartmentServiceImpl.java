package qtx.cloud.auth.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import qtx.cloud.auth.entity.SysDepartment;
import qtx.cloud.auth.mapper.SysDepartmentMapper;
import qtx.cloud.auth.service.SysDepartmentService;
import qtx.cloud.java.enums.DataEnums;
import qtx.cloud.java.exception.DataException;
import qtx.cloud.model.base.BaseEntity;
import qtx.cloud.model.dto.auth.DepartmentDTO;
import qtx.cloud.model.vo.auth.DepartmentListVO;
import qtx.cloud.model.vo.auth.DepartmentVO;
import qtx.cloud.service.utils.ExcelTransfer;

/**
 * 科室管理表 服务实现类
 *
 * @author qtx
 * @since 2022-11-17
 */
@Service
public class SysDepartmentServiceImpl extends ServiceImpl<SysDepartmentMapper, SysDepartment>
    implements SysDepartmentService {

  private final ExcelTransfer<SysDepartment> excelTransfer;

  public SysDepartmentServiceImpl(ExcelTransfer<SysDepartment> excelTransfer) {
    this.excelTransfer = excelTransfer;
  }

  @Override
  public IPage<DepartmentVO> listDepartmentPage(DepartmentDTO dto) {
    return baseMapper.selectPageNew(
        dto.getPage(),
        Wrappers.lambdaQuery(SysDepartment.class)
            .eq(BaseEntity::getDeleteFlag, false)
            .likeRight(
                StringUtils.isNotBlank(dto.getName()),
                SysDepartment::getDepartmentName,
                dto.getName())
            .between(
                dto.getStarTime() != null && dto.getEndTime() != null,
                BaseEntity::getCreateOn,
                dto.getStarTime(),
                dto.getEndTime())
            .orderByDesc(BaseEntity::getCreateOn));
  }

  @Override
  public boolean saveOrUpdateNew(SysDepartment entity) {
    List<SysDepartment> list;
    if (StringUtils.isNotBlank(entity.getDepartmentName())
        || StringUtils.isNotBlank(entity.getDepartmentCode())
        || entity.getId() != null) {
      list =
          list(
              Wrappers.lambdaQuery(SysDepartment.class)
                  .and(
                      e ->
                          e.eq(
                                  StringUtils.isNotBlank(entity.getDepartmentName()),
                                  SysDepartment::getDepartmentName,
                                  entity.getDepartmentName())
                              .or()
                              .eq(
                                  StringUtils.isNotBlank(entity.getDepartmentCode()),
                                  SysDepartment::getDepartmentCode,
                                  entity.getDepartmentCode()))
                  .ne(entity.getId() != null, SysDepartment::getId, entity.getId()));
    } else {
      throw new DataException(DataEnums.DATA_IS_ABNORMAL);
    }
    if (list.isEmpty()) {
      return saveOrUpdate(entity);
    } else {
      throw new DataException(DataEnums.DATA_INSERT_FAIL);
    }
  }

  @Override
  public List<DepartmentListVO> listDepartment(String name) {
    return baseMapper.selectNew(
        Wrappers.lambdaQuery(SysDepartment.class)
            .eq(BaseEntity::getDeleteFlag, false)
            .likeRight(StringUtils.isNotBlank(name), SysDepartment::getDepartmentName, name));
  }

  @SneakyThrows
  @Override
  public void downloadExcel(HttpServletResponse response) {
    excelTransfer.exportExcel(response, list(), "科室", "sheet", this);
  }
}
