package qtx.cloud.model.bo.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qtx
 * @since 2023/2/26 19:10
 */
@Data
public class UserBO {

    private Long id;

    @ApiModelProperty("工号")
    private String userCode;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("密码")
    private String password;
}
