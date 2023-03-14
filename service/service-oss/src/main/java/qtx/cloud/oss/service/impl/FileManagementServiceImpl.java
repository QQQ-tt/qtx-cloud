package qtx.cloud.oss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import qtx.cloud.oss.entity.FileManagement;
import qtx.cloud.oss.mapper.FileManagementMapper;
import qtx.cloud.oss.service.FileManagementService;

/**
 * 文件管理表 服务实现类
 *
 * @author qtx
 * @since 2022-11-21
 */
@Service
public class FileManagementServiceImpl extends ServiceImpl<FileManagementMapper, FileManagement>
    implements FileManagementService {}
