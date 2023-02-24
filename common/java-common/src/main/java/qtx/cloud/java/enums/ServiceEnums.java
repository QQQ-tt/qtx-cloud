package qtx.cloud.java.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 不同业务用户类型
 *
 * @author qtx
 * @since 2022/11/21 13:40
 */
@Getter
public enum ServiceEnums {

    /**
     * 学习培训
     */
    SERVICE_LEARN("学习培训", "LEARN"),
    /**
     * 业务类型
     */
    SERVICE_STOREHOUSE("不同类型业务", "TYPE");

    /**
     * 服务名称
     */
    private final String name;

    /**
     * 服务编号
     */
    private final String code;

    ServiceEnums(String name, String code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public String toString() {
        return String.valueOf(this.code);
    }

    public static final Map<String, String> SERVICE_ENUMS = new HashMap<>();

    static {
        Stream.of(ServiceEnums.values()).forEach(v -> SERVICE_ENUMS.put(v.code, v.name));
    }
}
