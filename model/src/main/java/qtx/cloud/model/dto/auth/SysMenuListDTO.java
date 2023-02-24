package qtx.cloud.model.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qtx
 * @since 2022/12/12 9:52
 */
@Data
public class SysMenuListDTO {

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("编码")
    private String code;
}
