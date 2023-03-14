package qtx.cloud.activity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import qtx.cloud.activity.entity.AcName;
import qtx.cloud.activity.mapper.AcNameMapper;
import qtx.cloud.activity.service.AcNameService;
import qtx.cloud.model.dto.activity.ActivityDTO;

/**
 * <p>
 * 流程名称表 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2023-03-11
 */
@Service
public class AcNameServiceImpl extends ServiceImpl<AcNameMapper, AcName> implements AcNameService {

    @Override
    public boolean saveOrUpdateActivity(ActivityDTO dto) {
        return false;
    }
}
