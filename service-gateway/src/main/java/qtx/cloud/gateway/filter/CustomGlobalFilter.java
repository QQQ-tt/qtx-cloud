package qtx.cloud.gateway.filter;

import com.alibaba.nacos.api.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import qtx.cloud.gateway.entity.AuthVO;
import qtx.cloud.gateway.enums.AuthEnums;
import qtx.cloud.gateway.utils.Method;
import qtx.cloud.java.constant.StaticConstant;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author qtx
 * @since 2022/11/8 10:49
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @Value("${service-name}")
    private String serviceName;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        InetSocketAddress remoteAddress = request.getRemoteAddress();
        InetAddress address = Objects.requireNonNull(remoteAddress).getAddress();
        RequestPath path = request.getPath();
        log.info("path:{}{}", address, path);
        ServerHttpRequest.Builder mutate = request.mutate();
        mutate.header(StaticConstant.AUTH, "123");
        mutate.header(StaticConstant.IP, address.toString());
        AntPathMatcher matcher = new AntPathMatcher();
        // 放行swagger 和 其他地址
        if (matcher.match(StaticConstant.SWAGGER_URL, path.toString()) || AuthEnums.authPath(path.toString())) {
            return chain.filter(exchange);
        }
        RestTemplate template = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(StaticConstant.AUTH, "123");

        String token = request.getHeaders().getFirst(StaticConstant.TOKEN);
        String userCode = request.getHeaders().getFirst(StaticConstant.USER_CODE);
        if (StringUtils.isBlank(token)) {
            try {
                return Method.failed(exchange, "token为空");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (StringUtils.isBlank(userCode)) {
            try {
                return Method.failed(exchange, "userCode为空");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        HashMap<String, String> param = new HashMap<>(10);
        param.put(StaticConstant.TOKEN, token);
        param.put(StaticConstant.IP, address.toString());
        param.put(StaticConstant.USER_CODE, userCode);
        param.put(StaticConstant.URL, path.toString());
        HttpEntity<HashMap<String, String>> requestEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<AuthVO> responseToken = template.exchange("http://" + serviceName + ":2008/auth/user/token" +
                        "?token={token}&ip={ip}&userCode={userCode}&url={url}",
                HttpMethod.GET,
                requestEntity,
                AuthVO.class,
                param);
        AuthVO authVO = responseToken.getBody();
        assert authVO != null;
        if (!StringUtils.isBlank(authVO.getDataEnums())) {
            try {
                return Method.failed(exchange, authVO.getDataEnums());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String user = authVO.getUserCode();
        mutate.header(StaticConstant.USER, user);

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
