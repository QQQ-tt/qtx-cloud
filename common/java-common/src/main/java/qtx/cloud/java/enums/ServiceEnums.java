package qtx.cloud.java.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 服务编号
 * @author qtx
 * @since 2022/11/21 13:40
 */
@Getter
public enum ServiceEnums {

    /**
     * 伦理审查
     */
    SERVICE_ETHICS("伦理审查", "ETHICS"),
    /**
     * 学习培训
     */
    SERVICE_LEARN("学习培训", "LEARN"),
    /**
     * 机构工作台
     */
    SERVICE_MECHANISM("机构工作台", "MECHANISM"),
    /**
     * 申办方合同管理
     */
    SERVICE_SPONSOR_CONTRACT_MANAGEMENT("合同管理", "SERVICE_SPONSOR_CONTRACT_MANAGEMENT"),
    /**
     * 申办方付款凭证
     */
    SERVICE_SPONSOR_PAYMENT_VOUCHER("", "SERVICE_SPONSOR_PAYMENT_VOUCHER"),
    /**
     * 机构办合同纸质发票
     */
    SERVICE_MECHANISM_PAPER_INVOICE("纸质发票", "SERVICE_MECHANISM_PAPER_INVOICE"),
    /**
     * 机构办合同电子发票
     */
    SERVICE_MECHANISM_ELECTRONIC_INVOICE("电子发票", "SERVICE_MECHANISM_ELECTRONIC_INVOICE"),
    /**
     * 会议纪要文件
     */
    MEETING_SUMMARY("会议纪要", "MEETING_SUMMARY"),
    /**
     * 项目工作台
     */
    SERVICE_PROJECT("项目工作台", "PROJECT"),
    /**
     * 申办方工作台
     */
    SERVICE_SPONSOR("申办方工作台", "SPONSOR"),
    /**
     * 库房管理
     */
    SERVICE_STOREHOUSE("库房管理", "STOREHOUSE");

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
