package qtx.cloud.activity.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
import qtx.cloud.activity.service.AcStartService;
import qtx.cloud.java.Result;
import qtx.cloud.model.dto.activity.AcStartUpdateDTO;
import qtx.cloud.model.vo.activity.AcToDoVO;
import qtx.cloud.model.vo.activity.TaskVO;

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

  @ApiOperation("查询流程")
  @ApiImplicitParam(name = "taskUuid", value = "任务uuid")
  @GetMapping("/listTask")
  public Result<List<TaskVO>> listTask(@RequestParam String taskUuid) {
    return Result.success(acStartService.listTask(taskUuid));
  }

  @ApiOperation("更新节点")
  @PostMapping("/updateAc")
  public Result<Boolean> updateAc(@RequestBody @Valid AcStartUpdateDTO dto) {
    return Result.success(acStartService.updateAc(dto));
  }

  @ApiOperation("待办")
  @ApiImplicitParams(
      value = {
        @ApiImplicitParam(name = "acUuid", value = "流程uuid"),
        @ApiImplicitParam(name = "userCode", value = "用户工号")
      })
  @GetMapping("/toDo")
  public Result<List<AcToDoVO>> toDo(String acUuid, String userCode) {
    return Result.success(acStartService.toDo(acUuid, userCode));
  }
}
