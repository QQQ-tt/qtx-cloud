package qtx.cloud.model.vo.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import qtx.cloud.model.base.DateUserVO;

/**
 * @author qtx
 * @since 2022/11/18 10:20
 */
@Data
public class RoleVO extends DateUserVO {

    private Integer id;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("是否超级用户")
    private Boolean superUser;
}
