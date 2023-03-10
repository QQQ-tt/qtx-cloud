package qtx.cloud.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import qtx.cloud.auth.service.SysUserService;
import qtx.cloud.java.Result;
import qtx.cloud.model.dto.auth.*;
import qtx.cloud.model.vo.auth.CreateVO;
import qtx.cloud.model.vo.auth.LoginVO;
import qtx.cloud.model.vo.auth.SysUserVO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author qtx
 * @since 2022-11-21
 */
@Api(tags = "用户")
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    private final SysUserService service;

    public SysUserController(SysUserService service) {
        this.service = service;
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO dto) {
        return Result.success(service.login(dto));
    }

    @ApiOperation("验证码")
    @GetMapping("/getAuthCode")
    public void getAuthCode(HttpServletResponse response, @RequestParam("session") String session) throws IOException {
        service.getAuthCode(response, session);
    }

    @ApiOperation("注册用户")
    @PostMapping("/createUser")
    public Result<CreateVO> createUser(@RequestBody CreateUserDTO dto) {
        return Result.success(service.createUser(dto));
    }

    @ApiOperation("添加或修改用户")
    @PostMapping("/saveOrUpdate")
    public Result<Boolean> saveOrUpdate(@RequestBody CreateSysUserDTO dto) {
        return Result.success(service.saveOrUpdateNew(dto));
    }

    @ApiOperation("列表查询")
    @PostMapping("/listUserStorehousePage")
    public Result<Page<SysUserVO>> listUserStorehousePage(@RequestBody SysUserDTO dto) {
        return Result.success(service.listUserPage(dto));
    }

    @ApiOperation("修改密码,userCode 为空修改等钱登录人密码，否则修改对应工号用户的密码")
    @PostMapping("/changePassword")
    public Result<Boolean> changePassword(@RequestBody SysUserPasswordDTO dto) {
        return Result.success(service.changePassword(dto));
    }

    @ApiOperation("修改状态")
    @GetMapping("/changeStatus")
    public Result<Boolean> changeStatus(@RequestParam String userCode, @RequestParam Boolean status) {
        return Result.success(service.changeStatus(userCode, status));
    }

    @ApiOperation("登出")
    @GetMapping("/logout")
    public Result<Boolean> logout() {
        return Result.success(service.logout());
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/removeById")
    public Result<Boolean> removeById(@RequestParam Long id) {
        return Result.success(service.removeByIdNew(id));
    }

    @ApiOperation("刷新token")
    @GetMapping("/token")
    public Result<LoginVO> refreshToken(@RequestParam String refreshToken, @RequestParam String userCode) {
        return Result.success(service.refreshToken(refreshToken, userCode));
    }

}
