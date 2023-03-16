package qtx.cloud.activity.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

  private final AcNameService acNameService;

  public AcStartServiceImpl(AcNameService acNameService) {
    this.acNameService = acNameService;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public String startAc(String acUuid) {
    String uuid = NumUtils.uuid();
    AcName one =
        acNameService.getOne(Wrappers.lambdaQuery(AcName.class).eq(AcName::getAcUuid, acUuid));
    if (one == null) {
      throw new DataException(DataEnums.DATA_IS_ABNORMAL);
    }
    List<AcBO> boList = baseMapper.selectAc(acUuid, one.getInitType());
    List<AcStart> acStarts = new ArrayList<>();
    boList.forEach(
        bo -> {
          boolean flag = bo.getNodeGroup() == 1;
          acStarts.add(
              AcStart.builder()
                  .taskUuid(uuid)
                  .acUuid(bo.getAcUuid())
                  .acName(bo.getName())
                  .acNode(bo.getNodeName())
                  .acBusiness(bo.getBusinessInfo())
                  .node(flag ? Boolean.TRUE : Boolean.FALSE)
                  .hidden(bo.getHidden())
                  .submissionTime(flag ? LocalDateTime.now() : null)
                  .nodePassNum(bo.getNodePassNum())
                  .build());
        });
    saveBatch(acStarts);
    return uuid;
  }
}
