package qtx.cloud.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import qtx.cloud.auth.entity.SysUserInfo;
import qtx.cloud.auth.mapper.SysUserInfoMapper;
import qtx.cloud.auth.service.SysUserInfoService;

/**
 * 用户信息扩展表 服务实现类
 *
 * @author qtx
 * @since 2023-02-27
 */
@Service
public class SysUserInfoServiceImpl extends ServiceImpl<SysUserInfoMapper, SysUserInfo>
    implements SysUserInfoService {}
