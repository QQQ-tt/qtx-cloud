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
 * 实际业务关联表
 * </p>
 *
 * @author qtx
 * @since 2023-03-11
 */
@Getter
@Setter
@TableName("ac_business")
@ApiModel(value = "AcBusiness对象", description = "实际业务关联表")
public class AcBusiness extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("ac_node_id")
    private Integer acNodeId;

    @TableField("ac_name_id")
    private Integer acNameId;

    @ApiModelProperty("实际关联业务(处理人或角色)")
    @TableField("business_info")
    private String businessInfo;
}
