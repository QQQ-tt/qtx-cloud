package qtx.cloud.activity.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qtx.cloud.activity.entity.AcName;
import qtx.cloud.activity.entity.AcStart;
import qtx.cloud.activity.mapper.AcStartMapper;
import qtx.cloud.activity.service.AcNameService;
import qtx.cloud.activity.service.AcStartService;
import qtx.cloud.java.constant.StaticConstant;
import qtx.cloud.java.enums.DataEnums;
import qtx.cloud.java.exception.DataException;
import qtx.cloud.model.bo.activity.AcBO;
import qtx.cloud.model.dto.activity.AcStartUpdateDTO;
import qtx.cloud.model.vo.activity.AcToDoVO;
import qtx.cloud.model.vo.activity.TaskVO;
import qtx.cloud.service.utils.CommonMethod;
import qtx.cloud.service.utils.NumUtils;

/**
 * 流程启动表 服务实现类
 *
 * @author qtx
 * @since 2023-03-15
 */
@Service
public class AcStartServiceImpl extends ServiceImpl<AcStartMapper, AcStart>
    implements AcStartService {

  private final CommonMethod commonMethod;

  private final AcNameService acNameService;

  public AcStartServiceImpl(AcNameService acNameService, CommonMethod commonMethod) {
    this.acNameService = acNameService;
    this.commonMethod = commonMethod;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public String startAc(String acUuid) {
    String uuid = NumUtils.uuid();
    AcName one =
        acNameService.getOne(Wrappers.lambdaQuery(AcName.class).eq(AcName::getAcUuid, acUuid));
    if (one == null) {
      throw new DataException(DataEnums.DATA_AC_NULL);
    }
    List<AcBO> boList = baseMapper.selectAc(acUuid, one.getInitType(), null);
    initNode(true, boList, uuid, null);
    return uuid;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean updateAc(AcStartUpdateDTO dto) {
    String user = commonMethod.getUser();
    AcStart one =
        getOne(
            Wrappers.lambdaQuery(AcStart.class)
                .eq(AcStart::getHis, Boolean.FALSE)
                .eq(AcStart::getTaskUuid, dto.getTaskUuid())
                .eq(AcStart::getNode, Boolean.TRUE)
                .eq(AcStart::getAcBusiness, user)
                .isNull(AcStart::getThisFlag)
                .orderByAsc(AcStart::getAcNodeGroup)
                .last("limit 1"));
    if (one == null) {
      throw new DataException(DataEnums.DATA_AC_NULL);
    }
    AcStart parent = null;
    if (!StaticConstant.ACTIVITY_PARENT.equals(one.getParentTaskNodeUuid())) {
      parent =
          getOne(
              Wrappers.lambdaQuery(AcStart.class)
                  .eq(AcStart::getTaskNodeUuid, one.getParentTaskNodeUuid()));
      update(
          Wrappers.lambdaUpdate(AcStart.class)
              .eq(AcStart::getId, parent.getId())
              .set(AcStart::getHis, Boolean.TRUE));
    }
    AcStart acStart = getAcStart(dto, one);
    if (dto.getInitializeNode() != null && dto.getInitializeNode()) {
      // 初始化当前节点
      acStart.setReviewProgress(new BigDecimal(0));
      acStart.setThisNodePassNum(0);
    } else if (dto.getInitializeAc() != null && dto.getInitializeAc()) {
      // 初始化当前流程
      update(
          Wrappers.lambdaUpdate(AcStart.class)
              .eq(AcStart::getTaskUuid, dto.getTaskUuid())
              .set(AcStart::getHis, Boolean.TRUE));
      startAc(acStart.getAcUuid());
    } else {
      if (acStart.getThisNodePassNum().equals(acStart.getNodePassNum())) {
        acStart.setReviewProgress(new BigDecimal(1));
        AcName acName =
            acNameService.getOne(
                Wrappers.lambdaQuery(AcName.class).eq(AcName::getAcUuid, acStart.getAcUuid()));
        if (acName.getInitType()) {
          // 开启下一组节点
          List<AcBO> list =
              baseMapper.selectAc(acStart.getAcUuid(), Boolean.FALSE, acStart.getAcNodeGroup() + 1);
          if (parent != null) {
            parent.setAcBusiness(
                list.stream().map(AcBO::getBusinessInfo).collect(Collectors.toList()).toString());
          }
          if (!list.isEmpty()) {
            initNode(false, list, acStart.getTaskUuid(), acStart.getParentTaskNodeUuid());
          }
        }
        acStart.setFlag(Boolean.TRUE);
      } else {
        acStart.setReviewProgress(
            new BigDecimal(acStart.getThisNodePassNum())
                .divide(new BigDecimal(acStart.getNodePassNum()), 2, RoundingMode.HALF_DOWN));
      }
      if (parent != null) {
        parent.setReviewProgress(
            new BigDecimal(parent.getThisNodePassNum())
                .divide(new BigDecimal(parent.getNodePassNum()), 2, RoundingMode.HALF_DOWN));
        parent.setId(null);
        save(parent);
      }
    }
    return updateById(acStart);
  }

  @Override
  public List<AcToDoVO> toDo(String acUuid, String userCode) {
    return baseMapper.selectToDo(acUuid, userCode != null ? userCode : commonMethod.getUser());
  }

  @Override
  public List<TaskVO> listTask(String taskUuid) {
    List<TaskVO> list = baseMapper.selectTask(taskUuid);
    return list.stream()
        .filter(f -> StaticConstant.ACTIVITY_PARENT.equals(f.getParentTaskNodeUuid()))
        .peek(p -> p.setList(child(list, p)))
        .collect(Collectors.toList());
  }

  private List<TaskVO> child(List<TaskVO> list, TaskVO vo) {
    return list.stream()
        .filter(f -> f.getParentTaskNodeUuid() != null)
        .filter(f -> f.getParentTaskNodeUuid().equals(vo.getTaskNodeUuid()))
        .peek(p -> p.setList(child(list, p)))
        .collect(Collectors.toList());
  }

  private void initNode(boolean start, List<AcBO> list, String taskUuid, String pNodeUuid) {
    List<AcStart> acStarts = new ArrayList<>();
    addNode(start, list, taskUuid, pNodeUuid, acStarts);
    saveBatch(acStarts);
  }

  private void addNode(
      boolean start, List<AcBO> list, String taskUuid, String pNodeUuid, List<AcStart> acStarts) {
    list.forEach(
        bo -> {
          boolean flag = true;
          if (start) {
            flag = bo.getNodeGroup() == 1;
          }
          String uuid = NumUtils.uuid();
          List<AcBO> selectAc = null;
          if (!bo.getNodeType()) {
            selectAc = baseMapper.selectAc(bo.getAcNameUuid(), start, 1);
          }
          int num = 0;
          if (selectAc != null) {
            num = selectAc.stream().mapToInt(AcBO::getNodePassNum).sum();
          }
          acStarts.add(
              AcStart.builder()
                  .taskUuid(taskUuid)
                  .taskNodeUuid(uuid)
                  .parentTaskNodeUuid(pNodeUuid)
                  .acUuid(bo.getAcUuid())
                  .acName(bo.getName())
                  .acNode(bo.getNodeName())
                  .acNodeGroup(bo.getNodeGroup())
                  .acBusiness(bo.getBusinessInfo())
                  .node(flag ? Boolean.TRUE : Boolean.FALSE)
                  .hidden(bo.getHidden())
                  .submissionTime(flag ? LocalDateTime.now() : null)
                  .nodePassNum(selectAc != null ? num : bo.getNodePassNum())
                  .build());
          if (selectAc != null) {
            addNode(start, selectAc, taskUuid, uuid, acStarts);
          }
        });
  }

  private AcStart getAcStart(AcStartUpdateDTO dto, AcStart one) {
    return AcStart.builder()
        .id(one.getId())
        .acName(one.getAcName())
        .acNode(one.getAcNode())
        .acNodeGroup(one.getAcNodeGroup())
        .acBusiness(one.getAcBusiness())
        .acUuid(one.getAcUuid())
        .taskUuid(one.getTaskUuid())
        .submissionTime(one.getSubmissionTime())
        .passTime(dto.getThisFlag() ? LocalDateTime.now() : null)
        .thisFlag(dto.getThisFlag())
        .status(dto.getStatus())
        .thisNodePassNum(
            dto.getThisFlag() ? one.getThisNodePassNum() + 1 : one.getThisNodePassNum())
        .nodePassNum(one.getNodePassNum())
        .statusInfo(dto.getStatusInfo())
        .fileUuid(dto.getFileUuid())
        .remark(dto.getRemark())
        .node(one.getNode())
        .hidden(one.getHidden())
        .his(one.getHis())
        .build();
  }
}
