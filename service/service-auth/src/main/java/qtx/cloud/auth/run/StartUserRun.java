package qtx.cloud.auth.run;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import qtx.cloud.auth.mapper.SysUserMapper;
import qtx.cloud.java.constant.StaticConstant;
import qtx.cloud.model.bo.auth.UserBO;
import qtx.cloud.service.utils.RedisUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    Map<String, String> map = list.stream().collect(Collectors.toMap(UserBO::getUserCard, UserBO::getUserName));
    redisUtils.setHashMsgAll(StaticConstant.SYS_USER + StaticConstant.REDIS_INFO_1, map);
    list.forEach(
        e ->
            redisUtils.setHashMsgAll(
                StaticConstant.SYS_USER + e.getUserCard() + StaticConstant.REDIS_INFO,
                JSONObject.parseObject(JSON.toJSONString(e), Map.class)));
  }
}
