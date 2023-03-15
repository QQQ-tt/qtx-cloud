package qtx.cloud.activity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import qtx.cloud.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 复合流程名称表
 * </p>
 *
 * @author qtx
 * @since 2023-03-15
 */
@Getter
@Setter
@TableName("ac_complex_name")
@ApiModel(value = "AcComplexName对象", description = "复合流程名称表")
public class AcComplexName extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("复合流程名称")
    @TableField("name")
    private String name;

    @TableField("ac_name_id")
    private Integer acNameId;
}
