package qtx.cloud.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qtx.cloud.auth.entity.TestLeft;
import qtx.cloud.auth.service.TestLeftService;
import qtx.cloud.java.Result;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qtx
 * @since 2023-05-20
 */
@Api(tags = "test")
@RestController
@RequestMapping("/testLeft")
public class TestLeftController {

  private final TestLeftService service;

  public TestLeftController(TestLeftService service) {
    this.service = service;
  }

  @ApiOperation("测试")
  @GetMapping("/testList")
  public Result<List<TestLeft>> testList() {
    return Result.success(service.listNew());
  }

  @ApiOperation("测试2")
  @GetMapping("/testList2")
  public Result<List<TestLeft>> testList2() {
    return Result.success(service.listNew2());
  }

  @ApiOperation("测试3")
  @GetMapping("/testList3")
  public Result<List<TestLeft>> testList3() {
    return Result.success(service.listNew3());
  }
}
