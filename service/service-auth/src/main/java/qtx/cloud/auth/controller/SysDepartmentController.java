package qtx.cloud.auth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import qtx.cloud.auth.entity.SysDepartment;
import qtx.cloud.auth.service.SysDepartmentService;
import qtx.cloud.java.Result;
import qtx.cloud.model.dto.auth.DepartmentDTO;
import qtx.cloud.model.vo.auth.DepartmentListVO;
import qtx.cloud.model.vo.auth.DepartmentVO;

/**
 * 科室管理表 前端控制器
 *
 * @author qtx
 * @since 2022-11-17
 */
@Api(tags = "科室管理")
@RestController
@RequestMapping("/sysDepartment")
public class SysDepartmentController {

  private final SysDepartmentService service;

  public SysDepartmentController(SysDepartmentService service) {
    this.service = service;
  }

  @ApiOperation("分页查询")
  @PostMapping("/listDepartmentPage")
  public Result<IPage<DepartmentVO>> listDepartmentPage(@RequestBody DepartmentDTO dto) {
    return Result.success(service.listDepartmentPage(dto));
  }

  @ApiOperation("更新或新增")
  @PostMapping("/saveOrUpdate")
  public Result<Boolean> saveOrUpdate(@RequestBody SysDepartment entity) {
    return Result.success(service.saveOrUpdateNew(entity));
  }

  @ApiOperation("删除")
  @DeleteMapping("/removeById")
  public Result<Boolean> removeById(@RequestParam Long id) {
    return Result.success(service.removeById(id));
  }

  @ApiOperation("查询（不分页）")
  @GetMapping("/listDepartment")
  public Result<List<DepartmentListVO>> listDepartment(String name) {
    return Result.success(service.listDepartment(name));
  }

  @ApiOperation("导出")
  @GetMapping("/downloadExcel")
  public void downloadExcel(HttpServletResponse response) {
    service.downloadExcel(response);
  }
}
