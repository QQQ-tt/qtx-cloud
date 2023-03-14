package qtx.cloud.oss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qtx.cloud.oss.entity.FileManagement;

/**
 * 文件管理表 Mapper 接口
 *
 * @author qtx
 * @since 2022-11-21
 */
@Mapper
public interface FileManagementMapper extends BaseMapper<FileManagement> {}
