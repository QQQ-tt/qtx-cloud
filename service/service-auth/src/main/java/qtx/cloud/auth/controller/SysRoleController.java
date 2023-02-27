package qtx.cloud.auth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import qtx.cloud.auth.entity.SysRole;
import qtx.cloud.auth.service.SysRoleService;
import qtx.cloud.java.Result;
import qtx.cloud.model.dto.auth.RoleDTO;
import qtx.cloud.model.dto.auth.SysRoleMenuDTO;
import qtx.cloud.model.vo.auth.RoleListVO;
import qtx.cloud.model.vo.auth.RoleVO;
import qtx.cloud.model.vo.auth.SysRoleMenuVo;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 角色管理 前端控制器
 * </p>
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
    @PostMapping("/saveOrUpdate")
    public Result<Boolean> saveOrUpdate(@RequestBody SysRole entity) {
        return Result.success(service.saveOrUpdateNew(entity));
    }

    @ApiOperation("更新超级用户状态")
    @GetMapping("/updateSuper")
    public Result<Boolean> updateSuper(@RequestParam Integer id, @RequestParam boolean flag) {
        return Result.success(service.updateSuper(id, flag));
    }

    @ApiOperation("删除")
    @DeleteMapping("/removeById")
    public Result<Boolean> removeById(@RequestParam Long id) {
        return Result.success(service.removeById(id));
    }

    @ApiOperation("列表查询角色（不分页）")
    @PostMapping("/listRole")
    public Result<List<RoleListVO>> listRole(@RequestBody RoleDTO dto) {
        return Result.success(service.listRole(dto));
    }

    @ApiOperation("权限配置")
    @PostMapping("/addAuthMenu")
    public Result<Boolean> addAuthMenu(@RequestBody @Valid SysRoleMenuDTO dto) {
        return Result.success(service.addAuthMenu(dto));
    }

    @ApiOperation("获取权限配置")
    @PostMapping("/getMenuByRoleId")
    public Result<List<SysRoleMenuVo>> getMenuByRole(@RequestParam Integer id) {
        return Result.success(service.getMenuByRole(id));
    }

    @ApiOperation("获取登录人权限配置")
    @PostMapping("/getMenuByOnline")
    public Result<List<SysRoleMenuVo>> getMenuByRole() {
        return Result.success(service.getMenuByRole());
    }
}
