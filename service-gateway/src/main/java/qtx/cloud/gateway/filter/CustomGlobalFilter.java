package qtx.cloud.gateway.filter;

import com.alibaba.nacos.api.utils.StringUtils;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Objects;
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

/**
 * @author qtx
 * @since 2022/11/8 10:49
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

  @Value("${service-name}")
  private String serviceName;

  private final RestTemplate restTemplate;

  public CustomGlobalFilter(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

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
    if (matcher.match(StaticConstant.SWAGGER_URL, path.toString())
        || AuthEnums.authPath(path.toString())) {
      return chain.filter(exchange);
    }
    String token = request.getHeaders().getFirst(StaticConstant.TOKEN);
    String userCard = request.getHeaders().getFirst(StaticConstant.USER_CARD);
    if (StringUtils.isBlank(token)) {
      try {
        return Method.failed(exchange, "token为空");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    if (StringUtils.isBlank(userCard)) {
      try {
        return Method.failed(exchange, "userCard为空");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.add(StaticConstant.AUTH, "123");

    HashMap<String, String> param = new HashMap<>(10);
    param.put(StaticConstant.TOKEN, token);
    param.put(StaticConstant.IP, address.toString());
    param.put(StaticConstant.USER_CARD, userCard);
    param.put(StaticConstant.URL, path.toString());
    HttpEntity<HashMap<String, String>> requestEntity = new HttpEntity<>(requestHeaders);
    try {
      ResponseEntity<AuthVO> responseToken =
          restTemplate.exchange(
              "http://"
                  + serviceName
                  + ":2008/auth/user/token"
                  + "?token={token}&ip={ip}&userCard={userCard}&url={url}",
              HttpMethod.GET,
              requestEntity,
              AuthVO.class,
              param);
      AuthVO authVO = responseToken.getBody();
      assert authVO != null;
      if (authVO.getDataEnums() != null) {
        try {
          return Method.failed(exchange, authVO.getDataEnums());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
      String user = authVO.getUserCard();
      mutate.header(StaticConstant.USER, user);
    } catch (Exception e) {
      try {
        return Method.failed(exchange, "认证服务重启中...");
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }
    return chain.filter(exchange);
  }

  @Override
  public int getOrder() {
    return 0;
  }
}
