package qtx.cloud.activity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import qtx.cloud.activity.entity.AcName;
import qtx.cloud.model.dto.activity.ActivityDTO;

/**
 * <p>
 * 流程名称表 服务类
 * </p>
 *
 * @author qtx
 * @since 2023-03-11
 */
public interface AcNameService extends IService<AcName> {

    /**
     * 创建或编辑流程
     *
     * @param dto 流程详情
     * @return true or false
     */
    boolean saveOrUpdateActivity(ActivityDTO dto);

}
