package qtx.cloud.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qtx.cloud.auth.entity.SysMenu;

/**
 * 菜单信息表 Mapper 接口
 *
 * @author qtx
 * @since 2022-09-21
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {}
