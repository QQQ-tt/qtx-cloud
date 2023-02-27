package qtx.cloud.auth.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qtx.cloud.auth.service.AuthUserService;
import qtx.cloud.model.vo.auth.AuthVO;

/**
 * @author qtx
 * @since 2022/11/24 16:52
 */
@RestController
@RequestMapping("/auth/user")
public class AuthUserApiController {

    private final AuthUserService authUserService;

    public AuthUserApiController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @GetMapping("/token")
    public AuthVO authToken(
            @RequestParam("token") String token,
            @RequestParam("ip") String ip, @RequestParam("userCode") String userCode, @RequestParam("url") String url) {
        return authUserService.authToken(token, ip, userCode, url);
    }

}
