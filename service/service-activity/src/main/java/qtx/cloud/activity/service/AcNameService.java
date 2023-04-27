package qtx.cloud.activity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import qtx.cloud.activity.entity.AcName;
import qtx.cloud.model.dto.activity.ActivityDTO;

/**
 * 流程名称表 服务类
 *
 * @author qtx
 * @since 2023-03-15
 */
public interface AcNameService extends IService<AcName> {

  /**
   * 创建或修改流程
   *
   * @param dto 流程详情
   * @return 流程uuid
   */
  String saveOrUpdateAc(ActivityDTO dto);
}
