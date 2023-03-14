package qtx.cloud.model.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 文件管理表
 *
 * @author qtx
 * @since 2022-11-21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo extends BaseEntity {

  @TableId(value = "file_id", type = IdType.AUTO)
  private Long fileId;

  @ApiModelProperty("其他信息主键")
  private String otherInfo;

  @ApiModelProperty("项目id")
  private String projectId;

  @ApiModelProperty("文件uuid")
  private String fileUuid;

  @ApiModelProperty("文件原始名称")
  private String fileNameOld;

  @ApiModelProperty("文件真实名称")
  private String fileNameNew;

  @ApiModelProperty("bucket名称")
  private String bucketName;

  @ApiModelProperty("bucket路径")
  private String path;

  @ApiModelProperty("版本号")
  private String fileVersion;

  @ApiModelProperty("是否为历史文件")
  private Boolean history;

  @ApiModelProperty("文件所属业务")
  private String fileType;
}
