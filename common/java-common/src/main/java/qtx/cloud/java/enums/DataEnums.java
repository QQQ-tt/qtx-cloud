package qtx.cloud.java.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author qtx
 * @since 4062/10/29 0:00
 */
public enum DataEnums {
    /**
     * 成功
     */
    SUCCESS("成功", 200),
    /**
     * 失败
     */
    FAILED("失败", 500),
    /**
     * 服务调用失败
     */
    SERVICE_FAILED("服务调用失败", 503),
    /**
     * 密码错误
     */
    WRONG_PASSWORD("密码错误", 401),
    /**
     * 无法访问
     */
    ACCESS_DENIED("无法访问", 403),
    /**
     * 通过网关访问
     */
    GATEWAY_TRANSBOUNDARY("通过网关访问", 403),
    /**
     * 登录已过期
     */
    USER_LOGIN_EXPIRED("登录已过期", 401),
    /**
     * 验证码已过期
     */
    AUTH_CODE_EXPIRED("验证码已过期", 401),
    /**
     * token不存在
     */
    TOKEN_IS_NULL("token不存在", 401),
    /**
     * 账户已锁定5分钟
     */
    USER_LOGIN_LOCKING("账户已锁定5分钟", 403),
    /**
     * 用户未登录
     */
    USER_NOT_LOGIN("用户未登录", 401),
    /**
     * 用户验证失败
     */
    USER_IS_FAIL("用户验证失败", 406),
    /**
     * 当前服务管理员已经存在，请更换角色
     */
    ADMIN_IS_USER("当前服务管理员已经存在，请更换角色", 401),
    /**
     * 用户不存在
     */
    USER_IS_NULL("用户不存在", 406),
    /**
     * 验证码错误
     */
    USER_CODE_FAIL("验证码错误", 406),
    /**
     * 用户角色为空
     */
    USER_ROLE_NULL("用户角色为空", 406),
    /**
     * 用户不在该项目内
     */
    USER_PROJECT_NULL("用户不在该项目内", 406),
    /**
     * 服务编号不匹配
     */
    PLATFORM_IS_FAIL("服务编号不匹配", 406),
    /**
     * 入参数据为空.
     */
    DATA_IS_NULL("入参数据为空", 406),

    /**
     * 回收数据赋值审核状态失败.
     */
    UPDATE_RECYCLE_FILED("回收数据赋值审核状态失败", 406),

    /**
     * 入参数据为空.
     */
    FREQUENCY("观看次数增加失败", 201),
    /**
     * 入参数据异常
     */
    DATA_IS_ABNORMAL("入参数据异常", 406),
    /**
     * 结果集为空.
     */
    DATA_RESULT_NULL("结果集为空", 406),

    /**
     * 刷新库存失败.
     */
    FLASH_PRE_AMOUNT_FILED("刷新库存失败", 406),

    /**
     * 库存为0
     */
    MIMES_IS_ZERO("库存不足，请重新选择", 406),

    /**
     * 销毁药品集合为空.
     */
    DATA_DESTROY_ISNULL("销毁药品集合为空", 406),
    /**
     * 数据插入失败
     */
    DATA_INSERT_FAIL("数据插入失败", 406),
    /**
     * 数据更新失败
     */
    DATA_UPDATE_FAIL("数据更新失败", 406),
    /**
     * 数据重复
     */
    DATA_IS_REPEAT("数据重复", 406),
    /**
     * 受试者重复
     */
    DATA_SUBJECTS_IS_REPEAT("受试者重复", 406),
    /**
     * 受试者身份异常
     */
    DATA_SUBJECTS_ID_CARD_ERROR("受试者身份异常", 406),
    /**
     * 版本数据重复
     */
    DATA_IS_REPEAT_VERSION("版本数据重复", 406),
    /**
     * 合同名称不能重复
     */
    DATA_CONTRACT_IS_NULL("合同名称不能重复", 406),
    /**
     * 所有计划节点付款已完成，请不要重复付款
     */
    SCHEDULED_NODE_PAYMENT_COMPLETED("所有计划节点付款已完成，请不要重复付款", 406),
    /**
     * 发货单号不允许为空
     */
    DATA_SHIP_IS_NULL("发货单号为空", 406),

    /**
     * 项目编号为空
     */
    DATA_PROJECT_IS_NULL("项目编号为空", 406),
    /**
     * 收货单处方号不允许为空
     */
    DATA_PRESCRIPTION_IS_NULL("处方号为空", 406),
    /**
     * 药品柜不允许为空
     */
    DATA_MEDICINE_LOCKER_IS_NULL("请选择药品柜", 406),

    /**
     * 器械柜不允许为空
     */
    DATA_INSTRUMENT_LOCKER_IS_NULL("请选择器械柜", 406),
    /**
     * 药品详情集合不允许为空
     */
    DATA_DRUG_IS_NULL("药品详情集合为空", 406),

    /**
     * 器械详情集合不允许为空
     */
    DATA_INSTRUMENT_IS_NULL("器械详情集合为空", 406),

    /**
     * 器械详情集合不允许为空
     */
    DATA_APPARATUS_IS_NULL("器械详情集合为空", 406),

    /**
     * 物资明细信息不全
     */
    DATA_MATERIALS_DETAIL_IS_NULL("物资明细信息不全", 406),


    /**
     * 物资年检日期为空
     */
    DATA_MATERIALS_IS_NULL("物资年检日期为空", 406),

    /**
     * 请添加内包装
     */
    DATA_PACK_IS_NULL("请添加内包装", 406),

    /**
     * 项目审核人跟时间未插入成功.
     */
    PROJECT_REVIEW_IS_NULL("项目审核人跟时间未插入成功", 406),

    /**
     * 药品收货订单id为空
     */
    DATA_DRUG_ID_IS_NULL("药品收货订单id为空", 406),
    /**
     * 确认药品入库失败
     */
    DATA_DRUG_STORAGE_FAILED("药品入库失败", 406),

    /**
     * 确认器械入库失败
     */
    DATA_INSTRUMENT_STORAGE_FAILED("器械入库失败", 406),

    /**
     * 确认物资入库失败
     */
    DATA_MATERIALS_STORAGE_FAILED("物资入库失败", 406),

    /**
     * 器械退回详情信息不全
     */
    DATA_BACK_LIST_IS_NULL("器械退回详情信息不全", 406),

    /**
     * 物资退回详情信息不全
     */
    DATA_MATERIALS_BACK_LIST_IS_NULL("物资退回详情信息不全", 406),
    /**
     * 药品详情集合为空
     */
    DATA_drug_IS_NULL("药品详情集合为空", 406),

    /**
     * 待回收主表信息插入失败
     */
    DATA_INSTRUMENT_RECYCLE_FILED("待回收主表信息插入失败", 406),
    /**
     * 死亡时间不能为空
     */
    DATA_DIETIMR_IS_NULL("死亡时间为空", 406),
    /**
     * Susar详情为空
     */
    DATA_SUSAR_IS_NULL("Susar详情为空", 406),
    /**
     * SAE分类不能为空
     */
    DATA_SAE_IS_NULL("SAE分类为空", 406),
    /**
     * 请选择发放药品选项
     */
    DATA_ID_IS_NULL("待发放药品id不能为空", 406),

    /**
     * 审核状态不允许为空
     */
    DATA_STATUS_IS_NULL("审核状态不允许为空", 406),

    /**
     * 项目编码不允许为空
     */
    PROJECT_CODE_IS_NULL("项目编码不允许为空", 406),


    /**
     * 物资退回时候检验项目编码，isDraft
     */
    PROJECT_CODE_OR_NAME_OR_IS_DRAFT_IS_NULL("projectCode或projectName或isDraft不允许为空", 406),
    /**
     * 批号不允许为空
     */
    BATCH_NUMBER_IS_NULL("批号不允许为空", 406),

    /**
     * 没有该数据
     */
    DATA_JIHE_NULL("没有该数据", 406),

    /**
     * 更新库存失败
     */
    DATA_PRESELLAMOUNT_IS_FILE("更新库存失败", 406),
    /**
     * 请选择发放数量
     */
    DATA_AMOUNT_IS_NULL("请选择发放数量", 406),
    /**
     * 数据已发布，不许更改
     */
    DATA_PUBLISH_NOT_ALLOWED("数据已发布，不许更改", 406),
    /**
     * 访视窗不存在
     */
    DATA_ACCESS_WINDOWS_NULL("访视窗不存在", 406),
    /**
     * 受理号不能为空
     */
    ACCEPTANCE_NUMBER_IS_NOT("受理号为空", 406),
    /**
     * 请选择用户工号
     */
    USER_CODE_IS_NULL("请选择用户工号", 406),
    /**
     * 器械回收集合不允许为空
     */
    DATA_INSTRUMENT_RECYCLE_IS_NULL("器械回收信息为空", 406),
    /**
     * 项目实施中不能撤回
     */
    NOT_WITHDRAW("项目实施中不能撤回", 406),

    /**
     *驳回意见不能为空
     */
    REJECT_OPINION_IS_NOT_NULL("驳回意见不能为空", 406),
    /**
     * 文件版本号和版本日期不能为空
     */
    FILE_VERSION_DATE_IS_NOT_NULL("文件版本号和版本日期不能为空", 406),
    /**
     * 参数为空
     */
    PARAMETER_IS_NULL("参数为空", 406),
    /**
     *otherId不能为空
     */
    OTHER_ID_IS_NOT_NULL("otherId不能为空", 406),
    /**
     * 文件所属阶段不匹配
     */
    STAGE_UNMATCHED("文件所属阶段不匹配", 406),
    /**
     * 操作数不能为空
     */
    OPERATE_NUM_IS_NOT_NULL("操作数不能为空", 406),
    /**
     * 流程进度错误
     */
    PROCESS_PROGRESS_IS_INCORRECT("流程进度错误", 406),
    /**
     * 伦理预审不存在
     */
    ANTECEDENT_TRIAL_IS_NULL("伦理预审不存在", 406),
    /**
     * 伦理受理号已存在
     */
    ACCEPT_NUMBER_IS_EXIST("伦理受理号已存在", 406),
    /**
     * 考试次数用尽
     */
    NUMBER_OF_EXAMS("考试次数用尽", 406),
    /**
     * otherId不存在
     */
    OTHER_ID_NOT_EXIST("otherId不存在", 205),
    /**
     * 主研角色不存在
     */
    MAIN_RESEARCH_NULL("主研角色不存在", 406),
    /**
     * 机构角色不存在
     */
    INSTITUTION_RESEARCH_NULL("机构角色不存在", 406),
    /**
     * 此状态不允许删除
     */
    REMOVE_IS_NO("此状态不允许删除", 406),

    /**
     * 驳回后将回收记录复原
     */
    BO_HUI_IS_RIGHT("驳回后将回收记录复原", 406),
    /**
     * 伦理审查意见汇总已完成
     */
    TRIAL_COLLECT_FINISH("伦理审查意见汇总已完成", 406),
    /**
     *主审审查未完成
     */
    PRINCIPAL_TRIAL_NOT_FINISH("主审审查未完成", 406),
    /**
     * SAR详情为空
     */
    SAR_IS_NULL("SAU详情为空", 406),
    /**
     * 伦理受理号重复
     */
    ACCEPT_NUMBER_REPEAT("伦理受理号重复", 406),
    /**
     * 该项目暂无审查历史
     */
    PROJECT_NOT_HISTORY("该项目暂无审查历史", 406),
    /**
     * 受理号为空请选择项目
     */
    ACCEPT_NUMBER_IS_NULL("受理号为空请选择项目", 406),
    /**
     * 该otherId无对应数据
     */
    OTHER_ID_DATA_IS_NULL("该otherId无对应数据", 406),
    /**
     * 该类型伦理表单字典为空，请前往配置中心配置
     */
    DATA_DICTIONARY_IS_NULL("该类型伦理表单字典为空，请前往配置中心配置", 406),
    /**
     * 伦理审查类型为空，请选择
     */
    ETHIC_TYPE_IS_NULL("伦理审查类型为空，请选择", 406),
    /**
     * 科室编码不存在
     */
    DEPT_CODE_IS_NULL("科室编码不存在", 406),
    /**
     * 字典类型不匹配
     */
    TYPE_NOT_FOUND("字典类型不匹配", 406);

    /**
     * 提示
     */
    private final String msg;
    /**
     * 错误编码
     */
    private final int code;

    DataEnums(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    @Override
    public String toString() {
        return this.msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    private static final Map<String, Integer> DATE_ENUMS = new HashMap<>();

    static {
        Stream.of(DataEnums.values()).forEach(v -> DATE_ENUMS.put(v.msg, v.code));
    }

}
