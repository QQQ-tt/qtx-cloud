package qtx.cloud.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import qtx.cloud.auth.entity.SysMenu;
import qtx.cloud.auth.service.SysMenuService;
import qtx.cloud.java.Result;
import qtx.cloud.model.dto.auth.SysMenuDTO;
import qtx.cloud.model.dto.auth.SysMenuListDTO;

/**
 * 菜单信息表 前端控制器
 *
 * @author qtx
 * @since 2022-11-29
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController {

  private final SysMenuService service;

  public SysMenuController(SysMenuService service) {
    this.service = service;
  }

  @ApiOperation("列表查询菜单（树结构）")
  @PostMapping("/listSysMenuTree")
  public Result<List<SysMenu>> listSysMenuTree(@RequestBody SysMenuListDTO dto) {
    return Result.success(service.listSysMenuTree(dto));
  }

  @ApiOperation("列表查询菜单")
  @PostMapping("/listSysMenu")
  public Result<List<SysMenu>> listSysMenu(String s) {
    return Result.success(service.listSysMenu(s));
  }

  @ApiOperation("新建或更新")
  @PostMapping("/saveOrUpdateMenu")
  public Result<Boolean> saveOrUpdateMenu(@RequestBody SysMenuDTO dto) {
    return Result.success(service.saveOrUpdateNew(dto));
  }

  @ApiOperation("删除")
  @DeleteMapping("/removeMenuById")
  public Result<Boolean> removeMenuById(Integer id) {
    return Result.success(service.removeByIdNew(id));
  }
}
