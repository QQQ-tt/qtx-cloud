package qtx.cloud.model.dto.auth;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import qtx.cloud.model.base.PageDTO;

/**
 * @author qtx
 * @since 2022/8/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserDTO extends PageDTO {

    private String userName;

    private String userCode;

    @ApiModelProperty("角色")
    private String roleId;

    @ApiModelProperty("科室")
    private String departmentCode;

    private String status;
}
