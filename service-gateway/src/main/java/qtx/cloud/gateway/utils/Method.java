package qtx.cloud.gateway.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author qtx
 * @since 2022/11/28 17:40
 */
public class Method {

    /**
     * 过滤器返回信息
     *
     * @param exchange response
     * @param msg 错误信息
     * @throws IOException io失败
     */
    public static Mono<Void> failed(ServerWebExchange exchange, String msg) throws IOException {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        Result<Object> failed = Result.failed(msg);
        byte[] bytes2 = JSONObject.toJSONString(failed).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes2);
        return response.writeWith(Mono.just(buffer));
    }
}
