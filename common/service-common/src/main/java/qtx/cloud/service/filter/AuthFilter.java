package qtx.cloud.service.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;
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
        String uri = request.getRequestURI();
        String userCode = request.getHeader(StaticConstant.USER);
        AntPathMatcher matcher = new AntPathMatcher();
        RequestWrapper requestWrapper = null;
        if (!matcher.match(StaticConstant.OSS_URL, uri)) {
            requestWrapper = new RequestWrapper(request);
            String s = JSON.toJSONString(requestWrapper.getBodyString());
            String replaceAll = s.replaceAll(" ", "").replaceAll("\\\\n", "").replaceAll("\\\\", "");
            log.info("request:[method:{}, path:{},json:{},param:{}]",
                    request.getMethod(),
                    uri,
                    replaceAll,
                    JSON.toJSONString(request.getParameterMap()));
        }
        String ip = request.getHeader(StaticConstant.IP);
        commonMethod.setUserCode(userCode);
        commonMethod.setIp(ip);
        String auth = request.getHeader(StaticConstant.AUTH);
        if (StringUtils.isBlank(auth)) {
            commonMethod.failed(response, DataEnums.GATEWAY_TRANSBOUNDARY);
            return;
        }
        filterChain.doFilter(requestWrapper == null ? request : requestWrapper, response);
    }
}
