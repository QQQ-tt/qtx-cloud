package qtx.cloud.model.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author qtx
 * @since 2022/9/20
 */
@Data
public class CreateSysUserDTO {
    private Long userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户code")
    private String userCode;

    @ApiModelProperty("是否启用，1是，0否")
    private Integer status;

    @ApiModelProperty("角色")
    private List<Integer> roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("0:女 1:男")
    private Integer sex;

    @ApiModelProperty("联系方式")
    private String tel;
}