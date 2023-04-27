package qtx.cloud.activity.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qtx.cloud.activity.entity.AcBusiness;
import qtx.cloud.activity.entity.AcName;
import qtx.cloud.activity.entity.AcNode;
import qtx.cloud.activity.mapper.AcNameMapper;
import qtx.cloud.activity.service.AcBusinessService;
import qtx.cloud.activity.service.AcNameService;
import qtx.cloud.java.enums.DataEnums;
import qtx.cloud.java.exception.DataException;
import qtx.cloud.model.dto.activity.ActivityDTO;
import qtx.cloud.service.utils.NumUtils;

/**
 * 流程名称表 服务实现类
 *
 * @author qtx
 * @since 2023-03-15
 */
@Service
public class AcNameServiceImpl extends ServiceImpl<AcNameMapper, AcName> implements AcNameService {

  private final AcNodeServiceImpl acNodeService;

  private final AcBusinessService acBusinessService;

  public AcNameServiceImpl(AcNodeServiceImpl acNodeService, AcBusinessService acBusinessService) {
    this.acNodeService = acNodeService;
    this.acBusinessService = acBusinessService;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public String saveOrUpdateAc(ActivityDTO dto) {
    // 流程主表创建
    String uuid;
    if (dto.getId() != null) {
      AcName oldAcName = getById(dto.getId());
      uuid = oldAcName.getAcUuid();
      update(
          Wrappers.lambdaUpdate(AcName.class)
              .set(AcName::getHistory, true)
              .eq(AcName::getId, oldAcName.getId()));
    } else {
      uuid = NumUtils.uuid();
    }
    AcName buildAcName =
        AcName.builder()
            .acUuid(uuid)
            .name(dto.getName())
            .initType(dto.getInitType())
            .businessMean(dto.getBusinessMean())
            .tableName(dto.getTableName())
            .build();
    save(buildAcName);
    // 流程详情创建
    dto.getList()
        .forEach(
            e -> {
              boolean flag1 =
                  e.getNodePassNum() == null
                      && !e.getNodeType()
                      && e.getStringSet().isEmpty()
                      && StringUtils.isNotBlank(e.getAcNameUuid());
              boolean flag2 =
                  e.getNodePassNum() != null
                      && e.getNodeType()
                      && StringUtils.isBlank(e.getAcNameUuid())
                      && !e.getStringSet().isEmpty();
              boolean flag = !(flag1 || flag2);
              if (flag) {
                throw new DataException(DataEnums.DATA_IS_ABNORMAL);
              }
              if (e.getNodePassNum() != null && e.getStringSet().size() < e.getNodePassNum()) {
                throw new DataException(DataEnums.DATA_IS_ABNORMAL);
              }
              AcNode acNode =
                  AcNode.builder()
                      .acNameId(buildAcName.getId())
                      .nodeName(e.getNodeName())
                      .nodePassNum(e.getNodePassNum())
                      .nodeGroup(e.getNodeGroup())
                      .hidden(e.getHidden())
                      .nodeType(e.getNodeType())
                      .acNameUuid(e.getAcNameUuid())
                      .build();
              acNodeService.save(acNode);
              List<AcBusiness> acBusinesses = new ArrayList<>();
              e.getStringSet()
                  .forEach(
                      b ->
                          acBusinesses.add(
                              AcBusiness.builder()
                                  .acNameId(buildAcName.getId())
                                  .acNodeId(acNode.getId())
                                  .businessInfo(b)
                                  .build()));
              acBusinessService.saveBatch(acBusinesses);
            });
    return uuid;
  }
}
