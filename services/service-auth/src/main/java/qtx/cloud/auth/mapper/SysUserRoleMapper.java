package qtx.cloud.auth.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import qtx.cloud.auth.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户角色关系表 Mapper 接口
 * </p>
 *
 * @author qtx
 * @since 2022-09-07
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    /**
     * 通过用户card获取角色
     *
     * @param userCode
     * @return
     */
    List<String> selectRoleByUserCard(String userCode);
}
