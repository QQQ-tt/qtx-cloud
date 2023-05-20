package qtx.cloud.service.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 *
 * @author qtx
 * @since 2022/8/14 14:54
 */
@Component
public class RedisUtils {
  private final RedisTemplate<String, Object> redisTemplate;

  public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  /**
   * 添加object类型 默认过期时间600分钟
   *
   * @param msg 内容 string
   */
  public void setMsg(String key, Object msg) {
    int timeOut = 60 * 10;
    redisTemplate.opsForValue().set(key, msg, timeOut, TimeUnit.MINUTES);
  }

  /**
   * 添加object类型 自定义过期时间
   *
   * @param msg 内容 string
   */
  public void setMsgDiyTimeOut(String key, Object msg, int timeOut, TimeUnit timeUnit) {
    redisTemplate.opsForValue().set(key, msg, timeOut, timeUnit);
  }

  /**
   * 通过key获取对象
   *
   * @param key redis key
   * @return object
   */
  public Object getMsg(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  /**
   * hash结构 添加数据
   *
   * @param key redis key
   * @param hashKey 键值 key
   * @param msg 内容
   */
  public void setHashMsg(String key, String hashKey, Object msg) {
    redisTemplate.opsForHash().put(key, hashKey, msg);
  }

  /**
   * hash结构 批量添加
   *
   * @param key redis key
   * @param msg 内容
   */
  public void setHashMsgAll(String key, Map<?, ?> msg) {
    redisTemplate.opsForHash().putAll(key, msg);
  }

  /**
   * hash结构 批量添加 设置过期时间
   *
   * @param key redis key
   * @param msg 内容
   * @param timeOut 超时时间
   * @param timeUnit 时间类型
   */
  public void setHashMsgAllTimeOut(String key, Map<?, ?> msg, long timeOut, TimeUnit timeUnit) {
    redisTemplate.opsForHash().putAll(key, msg);
    redisTemplate.expire(key, timeOut, timeUnit);
  }

  /**
   * hash结构 添加数据 设置过期时间
   *
   * @param key redis key
   * @param hashKey 键值 key
   * @param msg 内容
   * @param timeOut 超时时间
   * @param timeUnit 时间类型
   */
  public void setHashMsgTimeOut(
      String key, String hashKey, Object msg, long timeOut, TimeUnit timeUnit) {
    redisTemplate.opsForHash().put(key, hashKey, msg);
    redisTemplate.expire(key, timeOut, timeUnit);
  }

  /**
   * 获取hash field 所有key-value
   *
   * @param key redis key
   * @return 键值map集合
   */
  public Map<Object, Object> getHashMsg(String key) {
    return redisTemplate.opsForHash().entries(key);
  }

  /**
   * 获取hash 指定hash_key—value
   * @param key redis key
   * @param hashKey hash key
   * @return 当前hash key的值
   */
  public Object getHashMsg(String key, String hashKey) {
    return redisTemplate.opsForHash().get(key, hashKey);
  }

  /**
   * 通过key删除
   *
   * @param key redis key
   */
  public boolean deleteByKey(String key) {
    return Boolean.TRUE.equals(redisTemplate.delete(key));
  }

  /** 删除所有缓存 */
  public void delAll() {
    Set<String> keys = redisTemplate.keys("*");
    assert keys != null;
    keys.forEach(this::deleteByKey);
  }
}
