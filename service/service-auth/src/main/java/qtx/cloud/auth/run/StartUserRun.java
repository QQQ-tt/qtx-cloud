package qtx.cloud.auth.run;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import qtx.cloud.auth.mapper.SysUserMapper;
import qtx.cloud.java.constant.StaticConstant;
import qtx.cloud.model.bo.auth.UserBO;
import qtx.cloud.service.utils.RedisUtils;

/**
 * @author qtx
 * @since 2023/3/6 17:04
 */
@Component
public class StartUserRun {

  private final SysUserMapper userMapper;

  private final RedisUtils redisUtils;

  public StartUserRun(SysUserMapper userMapper, RedisUtils redisUtils) {
    this.userMapper = userMapper;
    this.redisUtils = redisUtils;
  }

  @PostConstruct
  public void initializeUserInfo() {
    List<UserBO> list = userMapper.selectAll();
    list.forEach(
        e ->
            redisUtils.setHashMsgAll(
                StaticConstant.SYS_USER + e.getUserCard() + StaticConstant.REDIS_INFO,
                JSONObject.parseObject(JSON.toJSONString(e), Map.class)));
  }
}
