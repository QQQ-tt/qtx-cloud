package qtx.cloud.java.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author huang
 * @since 2023/1/11 11:27
 */
@Getter
public enum StageEnums {

    /**
     * 项目立项
     */
    INITIATE_PROJECT("项目立项", "INITIATE", 1, 1),
    /**
     * 伦理审查
     */
    ETHICS_TRIAL("伦理审查", "ETHICS_TRIAL", 1, 2),
    /**
     * 合同签署
     */
    CONTRACT_SIGN("合同签署", "CONTRACT_SIGN", 1, 3),
    /**
     * 启动会预约
     */
    MEETING_APPOINTMENT("启动会预约", "MEETING_APPOINTMENT", 1, 4),
    /**
     * 首批物资入库
     */
    FIRST_MATERIAL_STOCK("首批物资入库", "FIRST_MATERIAL_STOCK", 1, 5),
    /**
     * 首付款
     */
    INITIAL_PAYMENT("首付款", "INITIAL_PAYMENT", 1, 6),
    /**
     * 启动会召开
     */
    MEETING_CONVOKE("启动会召开", "MEETING_CONVOKE", 1, 7),
    /**
     * 项目实施
     */
    PROJECT_IMPLEMENTATION("项目实施", "IMPLEMENTATION", 2, 1),
    /**
     * 结题质控
     */
    QUALITY_CONTROL("结题质控", "QUALITY_CONTROL", 2, 2),
    /**
     * 费用清算
     */
    LIQUIDATION_EXPENSES("费用清算", "EXPENSES", 2, 2),
    /**
     * 物资清算
     */
    LIQUIDATION_MATERIAL("物资清算", "MATERIAL", 2, 3),
    /**
     * 中心小结
     */
    CENTRAL_NODULE("中心小结", "NODULE", 2, 4),
    /**
     * 研究完成报告
     */
    STUDY_COMPLETION_REPORT("研究完成报告", "REPORT", 2, 5);

    /**
     * 阶段名称
     */
    private final String name;

    /**
     * 阶段编号
     */
    private final String code;

    /**
     * 流程类型
     */
    private final Integer type;

    /**
     * 分组
     */
    private final Integer group;

    StageEnums(String name, String code, Integer type, Integer group) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.group = group;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static final Map<String, String> STAGE_ENUMS = new HashMap<>();

    public static final Map<String, Integer> INITIALIZE_CONCLUSION = new HashMap<>();
    public static final Map<Integer, List<String>> CONCLUSION = new HashMap<>();

    public static final Map<String, Integer> INITIALIZE_PROJECT = new HashMap<>();

    public static final Map<Integer, List<String>> PROJECT = new HashMap<>();

    static {
        Stream.of(StageEnums.values()).forEach(v -> {
            STAGE_ENUMS.put(v.code, v.name);
            if (v.type == 1) {
                INITIALIZE_PROJECT.put(v.name, v.group);
                List<String> strings;
                if (PROJECT.containsKey(v.group)) {
                    strings = PROJECT.get(v.group);
                } else {
                    strings = new ArrayList<>();
                }
                strings.add(v.name);
                PROJECT.put(v.group, strings);
            } else {
                INITIALIZE_CONCLUSION.put(v.name, v.group);
                List<String> strings;
                if (CONCLUSION.containsKey(v.group)) {
                    strings = CONCLUSION.get(v.group);
                } else {
                    strings = new ArrayList<>();
                }
                strings.add(v.name);
                CONCLUSION.put(v.group, strings);
            }
        });
    }

    public static List<String> getNextNode(String name, Integer type) {
        if (type == 1) {
            Integer integer = INITIALIZE_PROJECT.get(name);
            if (integer + 1 > PROJECT.size()) {
                return null;
            } else {
                return PROJECT.get(integer + 1);
            }
        } else {
            Integer integer = INITIALIZE_CONCLUSION.get(name);
            if (integer + 1 > CONCLUSION.size()) {
                return null;
            } else {
                return CONCLUSION.get(integer + 1);
            }
        }
    }

    public static List<String> getThisNode(String name, Integer type) {
        if (type == 1) {
            return PROJECT.get(INITIALIZE_PROJECT.get(name));
        } else {
            return CONCLUSION.get(INITIALIZE_CONCLUSION.get(name));
        }
    }
}
