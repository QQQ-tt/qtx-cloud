package qtx.cloud.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import qtx.cloud.model.base.BaseEntity;

import java.util.List;

/**
 * <p>
 * 菜单信息表
 * </p>
 *
 * @author qtx
 * @since 2022-11-29
 */
@Getter
@Setter
@TableName("sys_menu")
@ApiModel(value = "SysMenu对象", description = "菜单信息表")
public class SysMenu extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("菜单名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("编号")
    @TableField("code")
    private String code;

    @ApiModelProperty("菜单地址")
    @TableField("path")
    private String path;

    @ApiModelProperty("父id")
    @TableField("parent_id")
    private Integer parentId;

    @ApiModelProperty("是否为菜单")
    @TableField("is_menu")
    private Boolean menu;

    @TableField(exist = false)
    private List<SysMenu> list;
}
