package qtx.cloud.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import qtx.cloud.auth.service.AuthUserService;
import qtx.cloud.java.constant.StaticConstant;
import qtx.cloud.java.enums.DataEnums;
import qtx.cloud.model.vo.auth.AuthVO;
import qtx.cloud.service.utils.JwtUtils;
import qtx.cloud.service.utils.RedisUtils;

/**
 * @author qtx
 * @since 2022/11/28 14:42
 */
@Slf4j
@Service
public class AuthUserServiceImpl implements AuthUserService {

  private final JwtUtils jwtUtils;
  private final RedisUtils redisUtils;

  public AuthUserServiceImpl(JwtUtils jwtUtils, RedisUtils redisUtils) {
    this.jwtUtils = jwtUtils;
    this.redisUtils = redisUtils;
  }

  @Override
  public AuthVO authToken(String token, String ip, String userCard, String url) {
    Map<Object, Object> s =
        redisUtils.getHashMsg(StaticConstant.LOGIN_USER + userCard + StaticConstant.REDIS_INFO);
    AuthVO vo = new AuthVO();
    // 验证token
    if (Objects.isNull(s)) {
      log.info("token info error {}", DataEnums.USER_NOT_LOGIN);
      vo.setDataEnums(DataEnums.USER_NOT_LOGIN);
      return vo;
    }
    String secret = (String) s.get("secret");
    String userCardToken = jwtUtils.getInfoFromToken(token, secret);
    log.info("user code :{}", userCardToken);
    if (StringUtils.isBlank(userCardToken)) {
      log.info("token info error {}", DataEnums.USER_LOGIN_EXPIRED);
      log.info("token info error user code {}", userCard);
      vo.setDataEnums(DataEnums.USER_LOGIN_EXPIRED);
      return vo;
    }
    // userCard合法性
    if (!userCardToken.equals(s.get(StaticConstant.USER_CARD))) {
      log.info("token info error user code {}", DataEnums.USER_IS_FAIL);
      vo.setDataEnums(DataEnums.USER_IS_FAIL);
      return vo;
    }
    // ip
    if (!ip.equals(s.get(StaticConstant.IP))) {
      log.info("token info error ip {}", DataEnums.USER_IS_FAIL);
      vo.setDataEnums(DataEnums.USER_IS_FAIL);
      return vo;
    }
    // 判断是否过期
    if (jwtUtils.isTokenExpired((String) s.get(StaticConstant.ACCESS_TOKEN), secret)) {
      redisUtils.deleteByKey(userCardToken);
      log.info("token info error time {}", DataEnums.USER_LOGIN_EXPIRED);
      vo.setDataEnums(DataEnums.USER_LOGIN_EXPIRED);
      return vo;
    }
    vo.setUserCard(userCardToken);
    return vo;
  }
}
