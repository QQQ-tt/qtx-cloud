package qtx.cloud.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qtx.cloud.auth.entity.SysUserInfo;

/**
 * 用户信息扩展表 Mapper 接口
 *
 * @author qtx
 * @since 2023-02-27
 */
@Mapper
public interface SysUserInfoMapper extends BaseMapper<SysUserInfo> {}
