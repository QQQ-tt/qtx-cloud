package qtx.cloud.oss.service.impl;

import cn.hutool.core.util.ZipUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.minio.ObjectWriteResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import qtx.cloud.java.enums.DataEnums;
import qtx.cloud.java.enums.ServiceEnums;
import qtx.cloud.java.exception.DataException;
import qtx.cloud.model.dto.oss.OssBatchDTO;
import qtx.cloud.oss.entity.FileManagement;
import qtx.cloud.oss.service.FileManagementService;
import qtx.cloud.oss.service.OssService;
import qtx.cloud.oss.utils.MinioUtils;
import qtx.cloud.service.utils.NumUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

/**
 * @author qtx
 * @since 2022/11/18 16:28
 */
@Slf4j
@Service
public class OssServiceImpl implements OssService {

    private final MinioUtils minioUtils;

    private final FileManagementService service;

    public OssServiceImpl(MinioUtils minioUtils, FileManagementService service) {
        this.minioUtils = minioUtils;
        this.service = service;
    }

    @Override
    public boolean upload(MultipartFile file, String otherInfo, String business, String version, String fileUuid) {
        //文件名
        String oldFileName = file.getOriginalFilename();
        String newFileName = NumUtils.uuid() + "." + StringUtils.substringAfterLast(oldFileName, ".");
        //类型
        String contentType = file.getContentType();
        ObjectWriteResponse uploadFile = minioUtils.uploadFile(file, newFileName, contentType);
        return saveFileInfo(otherInfo, business, version, oldFileName, newFileName, uploadFile, fileUuid);
    }

    @Override
    public boolean uploads(MultipartFile[] files, String otherInfo, String business, String version,
                           String fileUuid) {
        boolean flag = true;
        for (MultipartFile file : files) {
            //文件名
            String fileName = file.getOriginalFilename();
            String newFileName = NumUtils.uuid() + "." + StringUtils.substringAfterLast(fileName, ".");
            //类型
            String contentType = file.getContentType();
            ObjectWriteResponse uploadFile = minioUtils.uploadFile(file, newFileName, contentType);
            flag = flag & saveFileInfo(otherInfo, business, version, fileName, newFileName, uploadFile, fileUuid);
        }
        return flag;
    }

    @Override
    public boolean uploadByBucket(MultipartFile file, String otherInfo, String bucket, String business,
                                  String version, String fileUuid) {
        //文件名
        String fileName = file.getOriginalFilename();
        String newFileName = NumUtils.uuid() + "." + StringUtils.substringAfterLast(fileName, ".");
        //类型
        String contentType = file.getContentType();
        ObjectWriteResponse uploadFile = minioUtils.uploadFile(bucket, file, newFileName, contentType);
        return saveFileInfo(otherInfo, business, version, fileName, newFileName, uploadFile, fileUuid);
    }

    @Override
    public void downloadFile(String fileObject, HttpServletResponse response) throws IOException {
        InputStream fileInputStream = minioUtils.getObject(fileObject);
        getFileName(fileObject, response, fileInputStream);
    }

    @Override
    public void downloadFileByBucket(String bucket, String fileObject, HttpServletResponse response) throws IOException {
        InputStream fileInputStream = minioUtils.getObject(bucket, fileObject);
        getFileName(fileObject, response, fileInputStream);
    }

    @Override
    public String getObjectUrl(String fileObject) {
        return minioUtils.getObjectUrl(fileObject);
    }

    @Override
    public boolean removeFile(String fileObject) {
        minioUtils.removeFile(fileObject);
        return service.remove(Wrappers.lambdaUpdate(FileManagement.class)
                .eq(FileManagement::getFileObject, fileObject)
                .set(FileManagement::getHistory, true));
    }

    @Override
    public boolean removeFileByBucket(String bucket, String fileObject) {
        minioUtils.removeFileByBucket(bucket, fileObject);
        return service.remove(Wrappers.lambdaUpdate(FileManagement.class)
                .eq(FileManagement::getFileObject, fileObject)
                .set(FileManagement::getHistory, true));
    }

    @Override
    public void downloadFileBatch(HttpServletResponse response, List<OssBatchDTO> fileObjects) throws IOException {
        //被压缩文件InputStream
        InputStream[] srcFiles = new InputStream[fileObjects.size()];
        String[] srcFileNames = new String[fileObjects.size()];
        HashMap<String, Integer> map = new HashMap<>(10);
        for (int i = 0; i < fileObjects.size(); i++) {
            OssBatchDTO dto = fileObjects.get(i);
            String fileObject = dto.getFileObject();
            InputStream inputStream = minioUtils.getObject(fileObject);
            if (inputStream == null) {
                continue;
            }
            // 重名更改
            String num = "";
            srcFiles[i] = inputStream;
            if (map.containsKey(dto.getName())) {
                map.put(dto.getName(), map.get(dto.getName()) + 1);
                num = "（" + map.get(dto.getName()) + ")";
            } else {
                map.put(dto.getName(), 1);
            }
            srcFileNames[i] = dto.getName() + num;
        }
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("文件集合.zip", "UTF-8"));
        ZipUtil.zip(response.getOutputStream(), srcFileNames, srcFiles);
    }

    @Override
    public boolean updateToHis(String fileObject) {
        return service.update(Wrappers.lambdaUpdate(FileManagement.class)
                .set(FileManagement::getHistory, true)
                .eq(FileManagement::getFileObject, fileObject));
    }

    /**
     * 保存文件信息
     *
     * @param otherInfo   其他信息id
     * @param business 服务类型
     * @param version     版本号
     * @param oldFileName 旧文件名称
     * @param newFileName 新文件名称
     * @param uploadFile  文件
     * @param fileUuid    文件uuid
     * @return true or false
     */
    private boolean saveFileInfo(String otherInfo, String business, String version, String oldFileName,
                                 String newFileName, ObjectWriteResponse uploadFile, String fileUuid) {

        if (!ServiceEnums.SERVICE_ENUMS.containsKey(business)) {
            throw new DataException(DataEnums.PLATFORM_IS_FAIL);
        }
        boolean empty = service.list(Wrappers.lambdaQuery(FileManagement.class)
                .eq(FileManagement::getOtherInfo, otherInfo)
                .eq(FileManagement::getFileVersion, version)).isEmpty();
        if (!empty) {
            throw new DataException(DataEnums.DATA_IS_REPEAT_VERSION);
        }
        List<FileManagement> list = service.list(Wrappers.lambdaQuery(FileManagement.class)
                .eq(FileManagement::getOtherInfo, otherInfo)
                .eq(FileManagement::getHistory, false));
        if (!list.isEmpty()) {
            list.forEach(e -> e.setHistory(true));
            service.updateBatchById(list);
        }
        return service.save(FileManagement.builder()
                .fileNameNew(newFileName)
                .fileNameOld(oldFileName)
                .fileVersion(version)
                .otherInfo(otherInfo)
                .fileType(business)
                .fileObject(uploadFile.object())
                .bucketName(uploadFile.bucket())
                .fileUuid(fileUuid)
                .build());
    }

    public void getFileName(String fileName, HttpServletResponse response, InputStream fileInputStream) throws IOException {
        FileManagement one = service.getOne(Wrappers.lambdaQuery(FileManagement.class)
                .eq(FileManagement::getFileObject, fileName));
        String fileNameOld = one.getFileNameOld();
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(StringUtils.substringBefore(fileNameOld, "."),
                        "UTF-8") + "." + StringUtils.substringAfterLast(fileName, "."));
        response.setContentType("application/force-download");
        response.setCharacterEncoding("UTF-8");
        IOUtils.copy(fileInputStream, response.getOutputStream());
    }
}
