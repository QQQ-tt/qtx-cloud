package qtx.cloud.oss.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import qtx.cloud.model.base.BaseEntity;

/**
 * 文件管理表
 *
 * @author qtx
 * @since 2022-11-21
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("file_management")
@ApiModel(value = "FileManagement对象", description = "文件管理表")
public class FileManagement extends BaseEntity {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  @ApiModelProperty("与其他表信息关联字段")
  @TableField("other_info")
  private String otherInfo;

  @ApiModelProperty("文件uuid")
  @TableField("file_uuid")
  private String fileUuid;

  @ApiModelProperty("文件原始名称")
  @TableField("file_name_old")
  private String fileNameOld;

  @ApiModelProperty("文件真实名称")
  @TableField("file_name_new")
  private String fileNameNew;

  @ApiModelProperty("bucket名称")
  @TableField("bucket_name")
  private String bucketName;

  @ApiModelProperty("文件对象")
  @TableField("file_object")
  private String fileObject;

  @ApiModelProperty("版本号")
  @TableField("file_version")
  private String fileVersion;

  @ApiModelProperty("是否为历史版本")
  @TableField("is_history")
  private Boolean history;

  @ApiModelProperty("文件所属业务")
  @TableField("file_type")
  private String fileType;
}
