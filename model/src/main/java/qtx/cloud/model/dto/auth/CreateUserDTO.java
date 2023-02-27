package qtx.cloud.model.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qtx
 * @since 2022/11/21 16:18
 */
@Data
public class CreateUserDTO {

    @ApiModelProperty("用户名")
    private String userName;

    /**
     * 用户工号
     */
    @JsonIgnore
    private String userCode;

    @ApiModelProperty("密码")
    private String password;
}
