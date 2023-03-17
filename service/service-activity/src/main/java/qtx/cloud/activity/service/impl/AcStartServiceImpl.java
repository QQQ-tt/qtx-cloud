package qtx.cloud.activity.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qtx.cloud.activity.entity.AcName;
import qtx.cloud.activity.entity.AcStart;
import qtx.cloud.activity.mapper.AcStartMapper;
import qtx.cloud.activity.service.AcNameService;
import qtx.cloud.activity.service.AcStartService;
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
    initNode(true, boList, uuid);
    return uuid;
  }

  @Override
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
                .orderByDesc(AcStart::getAcNodeGroup)
                .last("limit 1"));
    if (one == null) {
      throw new DataException(DataEnums.DATA_AC_NULL);
    }
    AcStart acStart =
        AcStart.builder()
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
    if (dto.getInitializeNode() != null && dto.getInitializeNode()) {
      // 初始化当前节点
      acStart.setReviewProgress(new BigDecimal(0));
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
          if (!list.isEmpty()) {
            initNode(false, list, acStart.getTaskUuid());
          }
        }
        update(
            Wrappers.lambdaUpdate(AcStart.class)
                .set(AcStart::getFlag, true)
                .eq(AcStart::getAcNodeGroup, acStart.getAcNodeGroup())
                .eq(AcStart::getTaskUuid, acStart.getTaskUuid()));
      } else {
        acStart.setReviewProgress(
            new BigDecimal(acStart.getThisNodePassNum())
                .divide(new BigDecimal(acStart.getNodePassNum()), 2, RoundingMode.HALF_DOWN));
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
    return baseMapper.selectTask(taskUuid);
  }

  private void initNode(boolean start, List<AcBO> list, String taskUuid) {
    List<AcStart> acStarts = new ArrayList<>();
    list.forEach(
        bo -> {
          boolean flag = true;
          if (start) {
            flag = bo.getNodeGroup() == 1;
          }
          acStarts.add(
              AcStart.builder()
                  .taskUuid(taskUuid)
                  .acUuid(bo.getAcUuid())
                  .acName(bo.getName())
                  .acNode(bo.getNodeName())
                  .acNodeGroup(bo.getNodeGroup())
                  .acBusiness(bo.getBusinessInfo())
                  .node(flag ? Boolean.TRUE : Boolean.FALSE)
                  .hidden(bo.getHidden())
                  .submissionTime(flag ? LocalDateTime.now() : null)
                  .nodePassNum(bo.getNodePassNum())
                  .build());
        });
    saveBatch(acStarts);
  }
}
