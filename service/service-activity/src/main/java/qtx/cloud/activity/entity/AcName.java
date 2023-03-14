package qtx.cloud.activity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import qtx.cloud.model.base.BaseEntity;

/**
 * <p>
 * 流程名称表
 * </p>
 *
 * @author qtx
 * @since 2023-03-11
 */
@Getter
@Setter
@TableName("ac_name")
@ApiModel(value = "AcName对象", description = "流程名称表")
public class AcName extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("流程名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("是否逐一初始化")
    @TableField("init_type")
    private Boolean initType;

    @ApiModelProperty("业务含义")
    @TableField("business_mean")
    private String businessMean;
}
