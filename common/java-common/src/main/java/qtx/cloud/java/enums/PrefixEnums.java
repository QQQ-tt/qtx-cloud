package qtx.cloud.java.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 编号前缀
 * @author huang
 * @since 2022/12/5 9:41
 */
public enum PrefixEnums {

    /**
     * 项目编号
     */
    PREFIX_PROJECT("项目", "XM"),
    /**
     * 考试
     */
    EXAM("考试", "EX");


    /**
     * 名称
     */
    private String name;

    /**
     * 前缀
     */
    private String prefix;

    PrefixEnums(String name, String prefix) {
        this.name = name;
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return String.valueOf(this.prefix);
    }

    public static final Map<String, String> PREFIX_ENUMS = new HashMap<>();

    static {
        Stream.of(PrefixEnums.values()).forEach(v -> PREFIX_ENUMS.put(v.prefix, v.name));
    }
}
