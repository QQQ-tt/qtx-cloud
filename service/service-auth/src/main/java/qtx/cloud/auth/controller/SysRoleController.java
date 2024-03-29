package qtx.cloud.auth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
import qtx.cloud.auth.entity.SysRole;
import qtx.cloud.auth.service.SysRoleService;
import qtx.cloud.java.Result;
import qtx.cloud.model.dto.auth.RoleDTO;
import qtx.cloud.model.dto.auth.SysRoleMenuDTO;
import qtx.cloud.model.vo.auth.RoleListVO;
import qtx.cloud.model.vo.auth.RoleVO;
import qtx.cloud.model.vo.auth.SysRoleMenuVo;

/**
 * 角色管理 前端控制器
 *
 * @author qtx
 * @since 2022-11-17
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {

  private final SysRoleService service;

  public SysRoleController(SysRoleService service) {
    this.service = service;
  }

  @ApiOperation("分页查询")
  @PostMapping("/listRolePage")
  public Result<IPage<RoleVO>> listRolePage(@RequestBody RoleDTO dto) {
    return Result.success(service.listRolePage(dto));
  }

  @ApiOperation("更新或新增")
  @PostMapping("/saveOrUpdateRole")
  public Result<Boolean> saveOrUpdateRole(@RequestBody SysRole entity) {
    return Result.success(service.saveOrUpdateNew(entity));
  }

  @ApiOperation("更新超级用户状态")
  @GetMapping("/updateSuper")
  public Result<Boolean> updateSuper(@RequestParam Integer id, @RequestParam boolean flag) {
    return Result.success(service.updateSuper(id, flag));
  }

  @ApiOperation("删除")
  @DeleteMapping("/removeRoleById")
  public Result<Boolean> removeRoleById(@RequestParam Long id) {
    return Result.success(service.removeById(id));
  }

  @ApiOperation("列表查询角色")
  @GetMapping("/listRole")
  public Result<List<RoleListVO>> listRole(String roleName) {
    return Result.success(service.listRole(roleName));
  }

  @ApiOperation("获取角色")
  @GetMapping("/getRoleById")
  public Result<RoleVO> getRoleById(@RequestParam Integer id) {
    return Result.success(service.getRoleById(id));
  }

  @ApiOperation("权限配置")
  @PostMapping("/addAuthMenu")
  public Result<Boolean> addAuthMenu(@RequestBody @Valid SysRoleMenuDTO dto) {
    return Result.success(service.addAuthMenu(dto));
  }

  @ApiOperation("获取权限配置")
  @PostMapping("/getMenuByRoleId")
  public Result<List<SysRoleMenuVo>> getMenuByRoleId(@RequestParam Integer id) {
    return Result.success(service.getMenuByRole(id));
  }

  @ApiOperation("获取登录人权限配置")
  @PostMapping("/getMenuByOnline")
  public Result<List<SysRoleMenuVo>> getMenuByOnline() {
    return Result.success(service.getMenuByRole());
  }
}
