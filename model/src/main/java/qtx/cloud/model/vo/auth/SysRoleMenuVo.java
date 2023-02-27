package qtx.cloud.model.vo.auth;

import lombok.Data;

import java.util.List;

/**
 * @author qtx
 * @since 2022/9/21
 */
@Data
public class SysRoleMenuVo {

    private Integer id;
    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单地址
     */
    private String path;

    /**
     * 图标地址
     */
    private String status;

    private Integer parentId;

    private List<SysRoleMenuVo> list;
}
