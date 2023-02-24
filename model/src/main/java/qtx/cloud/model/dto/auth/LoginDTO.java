package qtx.cloud.model.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qtx
 * @since 2022/11/21 16:13
 */
@Data
public class LoginDTO {

    @ApiModelProperty("工号")
    private String userCode;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("验证码")
    private String authCode;

    @ApiModelProperty("uuid")
    private String session;
}
