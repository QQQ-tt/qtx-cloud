package qtx.cloud.model.dto.oss;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qtx
 * @since 2023/2/7 10:40
 */
@Data
public class OssBatchDTO {

    @ApiModelProperty(value = "文件对象", required = true)
    private String fileObject;

    @ApiModelProperty("下载后的文件名称，需要文件后缀")
    private String name;
}
