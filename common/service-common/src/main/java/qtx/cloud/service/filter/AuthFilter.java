package qtx.cloud.service.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;
import qtx.cloud.java.constant.StaticConstant;
import qtx.cloud.java.enums.DataEnums;
import qtx.cloud.service.utils.CommonMethod;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author qtx
 * @since 2022/11/8 10:30
 */
@Slf4j
@Order(1)
@WebFilter("/*")
public class AuthFilter extends OncePerRequestFilter {

    @Resource
    private CommonMethod commonMethod;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.info("request:[method:{}, path:{}]", request.getMethod(), request.getRequestURI());
        log.info("request:[param:{}]", JSON.toJSONString(request.getParameterMap()));
        String userCode = request.getHeader(StaticConstant.USER);
        String ip = request.getHeader(StaticConstant.IP);
        commonMethod.setUserCode(userCode);
        commonMethod.setIp(ip);
        String auth = request.getHeader(StaticConstant.AUTH);
        if (StringUtils.isBlank(auth)) {
            commonMethod.failed(response, DataEnums.GATEWAY_TRANSBOUNDARY);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
