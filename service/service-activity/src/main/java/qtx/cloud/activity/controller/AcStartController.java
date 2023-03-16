package qtx.cloud.activity.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qtx.cloud.activity.service.AcStartService;
import qtx.cloud.java.Result;

/**
 * 流程启动表 前端控制器
 *
 * @author qtx
 * @since 2023-03-15
 */
@Api(tags = "流程操作中心")
@RestController
@RequestMapping("/acStart")
public class AcStartController {

  private final AcStartService acStartService;

  public AcStartController(AcStartService acStartService) {
    this.acStartService = acStartService;
  }

  @ApiOperation("开启流程")
  @ApiImplicitParam(name = "acUuid", value = "流程uuid")
  @GetMapping("/acStart")
  public Result<String> acStart(@RequestParam String acUuid) {
    return Result.success(acStartService.startAc(acUuid));
  }
}
