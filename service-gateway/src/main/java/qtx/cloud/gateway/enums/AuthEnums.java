package qtx.cloud.gateway.enums;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author qtx
 * @since 2023/2/8 13:32
 */
public enum AuthEnums {

    /**
     * 网关限制，放行地址:注册
     */
    REGISTER("/auth/sysUser/createUser"),
    /**
     * 网关限制，放行地址:登录
     */
    LOGIN("/auth/sysUser/login"),
    /**
     * 网关限制，放行地址:验证码
     */
    AUTH_CODE("/auth/sysUser/getAuthCode"),
    /**
     * 网关限制，放行地址:his保存数据
     */
    HIS("/project/proSubjects/saveHisInfo"),
    /**
     * 网关限制，放行地址:刷新token
     */
    REFRESH_TOKEN("/auth/sysUser/token");


    private final String context;

    AuthEnums(String context) {
        this.context = context;
    }


    public static boolean authPath(String path) {
        return AUTH.contains(path);
    }

    private final static Set<String> AUTH = new HashSet<>();

    static {
        Stream.of(AuthEnums.values()).forEach(e -> AuthEnums.AUTH.add(e.context));
    }

}
