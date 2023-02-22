package qtx.cloud.oss.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import qtx.cloud.java.Result;
import qtx.cloud.model.dto.oss.OssBatchDTO;
import qtx.cloud.oss.service.OssService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author qtx
 * @since 2022/11/18 14:51
 */
@Api(tags = "文件上传")
@RestController
@RequestMapping("/file")
public class OssController {

    private final OssService service;

    public OssController(OssService service) {
        this.service = service;
    }

    @ApiOperation("上传")
    @PostMapping("/upload")
    public Result<Boolean> upload(
            @RequestParam(value = "file", name = "文件") MultipartFile file,
            @RequestParam(value = "otherInfo", name = "其他信息关联字段") String otherInfo,
            @RequestParam(value = "business", name = "业务类型") String business, String version, String fileUuid) {
        return Result.success(service.upload(file, otherInfo, business, version, fileUuid));
    }

    @ApiOperation("批量上传")
    @PostMapping("/uploads")
    public Result<Boolean> upload(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("otherInfo") String otherInfo,
            @RequestParam("business") String business, String version, String fileUuid) {
        return Result.success(service.uploads(files, otherInfo, business, version, fileUuid));
    }

    @ApiOperation("上传到指定bucket")
    @PostMapping("/uploadByBucket")
    public Result<Boolean> uploadByBucket(
            @RequestParam("file") MultipartFile file,
            @RequestParam("otherInfo") String otherInfo,
            @RequestParam("bucket") String bucket,
            @RequestParam("business") String business, String version, String fileUuid) {
        return Result.success(service.uploadByBucket(file, otherInfo, bucket, business, version, fileUuid));
    }

    @ApiOperation("下载")
    @GetMapping("/download")
    public void download(
            @RequestParam("fileObject") String fileObject, HttpServletResponse response) throws IOException {
        service.downloadFile(fileObject, response);
    }

    @ApiOperation("批量下载")
    @PostMapping("/downloadBatch")
    public void downloadBatch(HttpServletResponse response,
                              @RequestBody List<OssBatchDTO> fileObject) throws IOException {
        service.downloadFileBatch(response, fileObject);
    }

    @ApiOperation("下载指定bucket文件")
    @GetMapping("/downloadFileByBucket")
    public void downloadFileByBucket(
            @RequestParam("bucket") String bucket,
            @RequestParam("fileObject") String fileObject, HttpServletResponse response) throws IOException {
        service.downloadFileByBucket(bucket, fileObject, response);
    }

    @ApiOperation("删除文件")
    @DeleteMapping("/removeFile")
    public Result<Boolean> removeFile(@RequestParam("fileObject") String fileObject) {
        return Result.success(service.removeFile(fileObject));
    }

    @ApiOperation("修改文件为历史文件")
    @GetMapping("/updateToHis")
    public Result<Boolean> updateToHis(@RequestParam("fileObject") String fileObject) {
        return Result.success(service.updateToHis(fileObject));
    }

    @ApiOperation("删除指定bucket文件")
    @DeleteMapping("/removeFileByBucket")
    public Result<Boolean> removeFileByBucket(
            @RequestParam("fileObject") String fileObject, @RequestParam("bucket") String bucket) {
        return Result.success(service.removeFileByBucket(bucket, fileObject));
    }

    @ApiOperation("获取外链")
    @GetMapping("/getUrl")
    public Result<String> getUrl(@RequestParam("fileObject") String fileObject) {
        return Result.success(service.getObjectUrl(fileObject));
    }
}
