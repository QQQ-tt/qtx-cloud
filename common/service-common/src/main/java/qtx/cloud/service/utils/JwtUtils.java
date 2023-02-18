package qtx.cloud.service.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qtx
 * @since 2022/8/30
 */
@Data
@Component
public class JwtUtils {

    private String header = "token";
    /** 签发者 */
    private String issuer = "gcp";


    /**
     * 生成token
     * @param subject 加密字符
     * @param expire 时长
     * @param secret 密钥
     * @return token
     */
    public String generateToken(String subject, Long expire, String secret) {
        //Create the Signature SecretKey
        final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        final byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Base64.getEncoder()
                .encodeToString(secret.getBytes()));
        final Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Map<String, Object> headerMap = new HashMap<>(10);
        headerMap.put("alg", "HS256");
        headerMap.put("typ", "JWT");
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        return Jwts.builder().setHeaderParams(headerMap).setSubject(subject).setIssuedAt(nowDate)
                //设置token过期时间
                .setExpiration(expireDate).signWith(SignatureAlgorithm.HS256, signingKey)
                //设置签发人信息
                .setIssuer(getIssuer())
                //设置签发时间
                .setIssuedAt(nowDate).compact();
    }

    /**
     * 解析token
     *
     * @param token token
     * @return
     */
    public Claims parseToken(String token, String secret) {
        final byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Base64.getEncoder()
                .encodeToString(secret.getBytes()));
        return Jwts.parser().setSigningKey(apiKeySecretBytes).parseClaimsJws(token).getBody();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token
     * @return
     */
    public String getInfoFromToken(String token, String secret) {
        String username;
        try {
            Claims claims = parseToken(token, secret);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 判断令牌是否过期
     *
     * @param token
     * @return
     */
    public Boolean isTokenExpired(String token, String secret) {
        try {
            Claims claims = parseToken(token, secret);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 刷新令牌
     *
     * @param token
     * @return
     */
    public String refreshToken(String token, String secret) {
        String refreshedToken;
        try {
            Claims claims = parseToken(token, secret);
            claims.put("created", new Date());
            refreshedToken = generateToken(claims.getSubject(), 36000L, secret);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }
}
