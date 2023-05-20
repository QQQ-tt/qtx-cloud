package qtx.cloud.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import qtx.cloud.model.base.BaseEntity;

/**
 * <p>
 *
 * </p>
 *
 * @author qtx
 * @since 2023-05-20
 */
@Getter
@Setter
@TableName("test_left")
@ApiModel(value = "TestLeft对象", description = "")
public class TestLeft extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_code1")
    private String userCode1;

    @TableField(exist = false)
    private String name1;

    @TableField("user_code2")
    private String userCode2;

    @TableField(exist = false)
    private String name2;

    @TableField("user_code3")
    private String userCode3;

    @TableField(exist = false)
    private String name3;
}
