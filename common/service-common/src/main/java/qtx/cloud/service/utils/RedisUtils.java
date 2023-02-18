package qtx.cloud.service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 *
 * @author qtx
 * @date 2022/8/14 14:54
 */
@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final Integer timeOut = 60 * 10;

    /**
     * 添加object类型
     * 默认过期时间600分钟
     *
     * @param msg string
     */
    public void setMsg(String key, Object msg) {
        redisTemplate.opsForValue().set(key, msg, timeOut, TimeUnit.MINUTES);
    }

    /**
     * 添加object类型
     * 自定义过期时间
     *
     * @param msg string
     */
    public void setMsgDiyTimeOut(String key, Object msg, int timeOut, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, msg, timeOut, timeUnit);
    }

    /**
     * 通过key获取对象
     *
     * @param key key
     * @return object
     */
    public Object getMsg(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 通过key删除
     *
     * @param key key
     */
    public boolean deleteByKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 删除所有缓存
     */
    public void delAll() {
        Set<String> keys = redisTemplate.keys("*");
        assert keys != null;
        keys.forEach(this::deleteByKey);
    }
}
