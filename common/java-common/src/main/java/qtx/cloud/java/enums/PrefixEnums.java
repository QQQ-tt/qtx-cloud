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
     * 启动会编号
     */
    LAUNCH_MEETING_CODE("启动会编号", "LM"),
    /**
     * 合同编号
     */
    PREFIX_CONTRACT("合同", "PC"),
    /**
     * 合同审批编号
     */
    CONTRACT_APPROVAL_FLOW("合同审批编号", "AF"),
    /**
     * 经费支出流水号
     */
    PROJECT_FUND("经费支出流水号", "PF"),
    /**
     * 经费审批编号
     */
    EXPENDITURE_APPROVAL("经费审批编号", "EA"),
    /**
     * 付款编号
     */
    PREFIX_PAYMENT("付款", "PR"),
    /**
     * 药品发货
     */
    PREFIX_DRUGS_SHIPMENTS("药品发货", "DD"),
    /**
     * 器械发货
     */
    PREFIX_MEDICAL_SHIPMENTS("器械发货", "MAD"),

    /**
     * 通知公告
     */
    PREFIX_ANNOUNCEMENT("通知公告", "TZG"),

    /**
     * 物资发货
     */
    PREFIX_MATERIALS_SHIPMENTS("物资发货", "UZI"),
    /**
     * 物资編號
     */
    PREFIX_MATERIALS_CODE("物资編號", "UZI"),

    /**
     * 药品回收
     */
    PREFIX_DRUGS_RECYCLE("药品回收", "MAR"),

    /**
     * 器械发货
     */
    PREFIX_MEDICAL_RECYCLE("器械回收", "QXR"),
    /**
     * 药品编号
     */
    MEDICINE_CODE("器械发货", "YP"),

    /**
     * 器械编号
     */
    INSTRUMENT_CODE("器械发货", "QX"),

    /**
     * 药品入库
     */
    PREFIX_DRUGS_STORAGE("药品入库", "DW"),

    /**
     * 药品退回
     */
    PREFIX_DRUGS_BACK("药品退回", "BACK"),

    /**
     * 器械退回
     */
    PREFIX_INSTRUMENT_BACK("器械退回", "QXBACK"),

    /**
     * 物资退回
     */
    PREFIX_MATERIALS_BACK("物资退回", "UZIBACK"),

    /**
     * 药品销毁
     */
    PREFIX_MEDICINE_DESTROY("药品入库", "XH"),
    /**
     * 器械入库
     */
    PREFIX_MEDICAL_STORAGE("器械入库", "MAW"),
    /**
     * 伦理预受理
     */
    ETHIC_PRE_ACCEPT("伦理预受理", "YSL"),
    /**
     * 机构
     */
    INSTITUTION("机构", "OC"),
    /**
     * 会议号
     */
    MEETING_NUMBER("会议号", "HY"),

    /**
     * 课件
     */
    COURSEWARE("课件", "KJ"),
    /**
     * 证书
     */
    CERTIFICATE("证书", "ZS"),
    /**
     * 题目
     */
    QUESTION("题目", "QU"),
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
