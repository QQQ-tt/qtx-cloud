package qtx.cloud.activity.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qtx.cloud.activity.service.AcNameService;
import qtx.cloud.java.Result;
import qtx.cloud.model.dto.activity.ActivityDTO;

/**
 * 流程名称表 前端控制器
 *
 * @author qtx
 * @since 2023-03-15
 */
@Api(tags = "流程管理")
@RestController
@RequestMapping("/acName")
public class AcNameController {

  private final AcNameService acNameService;

  public AcNameController(AcNameService acNameService) {
    this.acNameService = acNameService;
  }

  @ApiOperation("创建或更新流程")
  @PostMapping("/saveOrUpdateCa")
  public Result<String> saveOrUpdateCa(@RequestBody ActivityDTO dto) {
    return Result.success(acNameService.saveOrUpdateAc(dto));
  }
}
