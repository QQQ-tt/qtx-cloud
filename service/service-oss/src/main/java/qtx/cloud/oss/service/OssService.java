package qtx.cloud.oss.service;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import qtx.cloud.model.dto.oss.OssBatchDTO;

/**
 * @author qtx
 * @since 2022/11/18 15:58
 */
public interface OssService {

  /**
   * 文件上传
   *
   * @param file 文件
   * @param otherInfo 其他id
   * @param business 业务类型
   * @param version 版本号
   * @param fileUuid 文件uuid
   * @return true or false
   */
  boolean upload(
      MultipartFile file, String otherInfo, String business, String version, String fileUuid);

  /**
   * 文件上传
   *
   * @param files 文件
   * @param otherInfo 其他id
   * @param business 业务类型
   * @param version 版本号
   * @param fileUuid 文件uuid
   * @return true or false
   */
  boolean uploads(
      MultipartFile[] files, String otherInfo, String business, String version, String fileUuid);

  /**
   * 上传至指定bucket
   *
   * @param file 文件
   * @param otherInfo 其他id
   * @param bucket bucket名称
   * @param business 业务类型
   * @param version 版本号
   * @param fileUuid 文件uuid
   * @return true or false
   */
  boolean uploadByBucket(
      MultipartFile file,
      String otherInfo,
      String bucket,
      String business,
      String version,
      String fileUuid);

  /**
   * 文件下载
   *
   * @param fileObject 文件对象
   * @param response 返回流
   */
  void downloadFile(String fileObject, HttpServletResponse response) throws IOException;

  /**
   * 文件下载
   *
   * @param bucket bucket名称
   * @param fileObject 文件对象
   * @param response 返回流
   */
  void downloadFileByBucket(String bucket, String fileObject, HttpServletResponse response)
      throws IOException;

  /**
   * 获取文件外链
   *
   * @param fileObject 文件对象
   * @return 外链
   */
  String getObjectUrl(String fileObject);

  /**
   * 删除文件
   *
   * @param fileObject 文件对象
   * @return true or false
   */
  boolean removeFile(String fileObject);

  /**
   * 删除文件
   *
   * @param bucket bucket名称
   * @param fileObject 文件对象
   * @return true or false
   */
  boolean removeFileByBucket(String bucket, String fileObject);

  /**
   * 批量下载
   *
   * @param response 返回
   * @param fileObject 文件对象
   */
  void downloadFileBatch(HttpServletResponse response, List<OssBatchDTO> fileObject)
      throws IOException;

  /**
   * 修改文件为历史文件
   *
   * @param fileObject 文件对象
   * @return true or false
   */
  boolean updateToHis(String fileObject);
}
