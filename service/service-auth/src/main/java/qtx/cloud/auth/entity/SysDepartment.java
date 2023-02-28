package qtx.cloud.auth.entity;

import com.alibaba.excel.annotation.ExcelProperty;
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
 * 科室管理表
 * </p>
 *
 * @author qtx
 * @since 2022-11-17
 */
@Getter
@Setter
@TableName("sys_department")
@ApiModel(value = "SysDepartment对象", description = "科室管理表")
public class SysDepartment extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("科室编号")
    @ExcelProperty(value = {"科室", "科室名称"})
    @TableField("department_code")
    private String departmentCode;

    @ApiModelProperty("科室名称")
    @TableField("department_name")
    private String departmentName;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
