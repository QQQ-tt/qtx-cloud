package qtx.cloud.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import qtx.cloud.auth.service.AuthUserService;
import qtx.cloud.java.constant.StaticConstant;
import qtx.cloud.java.enums.DataEnums;
import qtx.cloud.model.vo.auth.AuthVO;
import qtx.cloud.service.utils.JwtUtils;
import qtx.cloud.service.utils.RedisUtils;

import java.util.Map;
import java.util.Objects;

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
    public AuthVO authToken(String token, String ip, String userCode, String url) {
        Map<Object, Object> s = redisUtils.getHashMsg(StaticConstant.LOGIN_USER + userCode + StaticConstant.REDIS_INFO);
        AuthVO vo = new AuthVO();
        // 验证token
        if (Objects.isNull(s)) {
            log.info("token info error {}", DataEnums.USER_NOT_LOGIN);
            vo.setDataEnums(DataEnums.USER_NOT_LOGIN);
            return vo;
        }
        String secret = (String) s.get("secret");
        String userCodeToken = jwtUtils.getInfoFromToken(token, secret);
        log.info("user code :{}", userCodeToken);
        if (StringUtils.isBlank(userCodeToken)) {
            log.info("token info error {}", DataEnums.USER_LOGIN_EXPIRED);
            log.info("token info error user code {}", userCode);
            vo.setDataEnums(DataEnums.USER_LOGIN_EXPIRED);
            return vo;
        }
        // userCode合法性
        if (!userCodeToken.equals(s.get("userCode"))) {
            log.info("token info error user code {}", DataEnums.USER_IS_FAIL);
            vo.setDataEnums(DataEnums.USER_IS_FAIL);
            return vo;
        }
        // ip
        if (!ip.equals(s.get("ip"))) {
            log.info("token info error ip {}", DataEnums.USER_IS_FAIL);
            vo.setDataEnums(DataEnums.USER_IS_FAIL);
            return vo;
        }
        // 判断是否过期
        if (jwtUtils.isTokenExpired((String) s.get("accessToken"), secret)) {
            redisUtils.deleteByKey(userCodeToken);
            log.info("token info error time {}", DataEnums.USER_LOGIN_EXPIRED);
            vo.setDataEnums(DataEnums.USER_LOGIN_EXPIRED);
            return vo;
        }
        vo.setUserCode(userCodeToken);
        return vo;
    }
}
