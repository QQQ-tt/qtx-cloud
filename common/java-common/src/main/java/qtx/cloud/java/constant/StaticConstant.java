package qtx.cloud.java.constant;

/**
 * 静态常量
 *
 * @author qtx
 * @since 2022/11/4 14:34
 */
public class StaticConstant {
    /**
     * 用户唯一信息
     */
    public static final String USER = "user";
    public static final String USER_CODE = "userCode";

    public static final String IP = "ip";

    public static final String TOKEN = "token";
    /**
     * 服务认证信息head标志
     */
    public static final String AUTH = "auth";
    /**
     * 请求地址
     */
    public static final String URL = "url";
    /**
     * 用户登录验证码前缀
     */
    public static final String REDIS_LOGIN_AUTH_CODE = "login:auth:";

    /**
     * 验证码
     */
    public static final String REDIS_CODE = ":code";

    /**
     * 试错次数
     */
    public static final String REDIS_NUM = ":num";

    /**
     * 登录错误次数redis
     */
    public static final String REDIS_LOGIN_NUM = ":login_num";

    /**
     * 账户登录错误次数上线
     */
    public static final int LOGIN_ERROR_UPPER_LIMIT = 5;

    /**
     * 用户登录token
     */
    public static final String LOGIN_USER = "login:user:";
    /**
     * 网关限制，放行地址
     */
    public static final String SWAGGER_URL = "/*/v2/api-docs";
}
