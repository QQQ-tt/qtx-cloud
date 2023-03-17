package qtx.cloud.activity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import qtx.cloud.activity.entity.AcStart;
import qtx.cloud.model.dto.activity.AcStartUpdateDTO;

/**
 * 流程启动表 服务类
 *
 * @author qtx
 * @since 2023-03-15
 */
public interface AcStartService extends IService<AcStart> {

  /**
   * 开启流程
   *
   * @param acUuid 流程uuid
   * @return 任务uuid
   */
  String startAc(String acUuid);

  /**
   * 更新节点状态
   *
   * @param dto 节点详情
   * @return true or false
   */
  boolean updateAc(AcStartUpdateDTO dto);
}
