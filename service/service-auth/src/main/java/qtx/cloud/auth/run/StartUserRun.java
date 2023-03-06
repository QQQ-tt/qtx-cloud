package qtx.cloud.auth.run;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qtx.cloud.auth.mapper.SysUserMapper;
import qtx.cloud.java.constant.StaticConstant;
import qtx.cloud.model.bo.auth.UserBO;
import qtx.cloud.service.utils.RedisUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author qtx
 * @since 2023/3/6 17:04
 */
@Component
public class StartUserRun {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private RedisUtils redisUtils;

    @PostConstruct
    public void initializeUserInfo() {
        List<UserBO> list = userMapper.selectAll();
        list.forEach(e -> redisUtils.setHashMsgAll(StaticConstant.SYS_USER + e.getUserCode() + StaticConstant.REDIS_INFO,
                JSONObject.parseObject(JSON.toJSONString(e), Map.class)));
    }
}
