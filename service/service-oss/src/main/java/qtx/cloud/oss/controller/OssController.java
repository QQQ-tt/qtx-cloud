package qtx.cloud.oss.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "file"), @ApiImplicitParam(
                    name = "otherInfo", value = "其他信息关联字段", required = true), @ApiImplicitParam(
                    name = "business", value = "业务类型", required = true)})
    @PostMapping("/upload")
    public Result<Boolean> upload(
            @RequestParam MultipartFile file,
            @RequestParam String otherInfo, @RequestParam String business, String version, String fileUuid) {
        return Result.success(service.upload(file, otherInfo, business, version, fileUuid));
    }

    @ApiOperation("批量上传")
    @ApiImplicitParams({@ApiImplicitParam(name = "files", value = "文件集合", required = true,
            dataType = "file"), @ApiImplicitParam(name = "otherInfo", value = "其他信息关联字段",
            required = true), @ApiImplicitParam(name = "business", value = "业务类型", required = true)})
    @PostMapping("/uploads")
    public Result<Boolean> upload(
            @RequestParam MultipartFile[] files,
            @RequestParam String otherInfo, @RequestParam String business, String version, String fileUuid) {
        return Result.success(service.uploads(files, otherInfo, business, version, fileUuid));
    }

    @ApiOperation("上传到指定bucket")
    @ApiImplicitParams({@ApiImplicitParam(name = "file", value = "文件集合", required = true,
            dataType = "file"), @ApiImplicitParam(name = "otherInfo", value = "其他信息关联字段",
            required = true), @ApiImplicitParam(name = "business", value = "业务类型",
            required = true), @ApiImplicitParam(name = "bucket", value = "存储桶", required = true)})
    @PostMapping("/uploadByBucket")
    public Result<Boolean> uploadByBucket(
            @RequestParam MultipartFile file,
            @RequestParam String otherInfo,
            @RequestParam String bucket, @RequestParam String business, String version, String fileUuid) {
        return Result.success(service.uploadByBucket(file, otherInfo, bucket, business, version, fileUuid));
    }

    @ApiOperation("下载")
    @ApiImplicitParam(name = "fileObject", value = "文件对象")
    @GetMapping("/download")
    public void download(@RequestParam String fileObject, HttpServletResponse response) throws IOException {
        service.downloadFile(fileObject, response);
    }

    @ApiOperation("批量下载")
    @PostMapping("/downloadBatch")
    public void downloadBatch(HttpServletResponse response,
                              @RequestBody List<OssBatchDTO> fileObject) throws IOException {
        service.downloadFileBatch(response, fileObject);
    }

    @ApiOperation("下载指定bucket文件")
    @ApiImplicitParams({@ApiImplicitParam(name = "bucket", value = "存储桶", required = true), @ApiImplicitParam(
            name = "fileObject", value = "文件对象", required = true)})
    @GetMapping("/downloadFileByBucket")
    public void downloadFileByBucket(
            @RequestParam String bucket,
            @RequestParam String fileObject, HttpServletResponse response) throws IOException {
        service.downloadFileByBucket(bucket, fileObject, response);
    }

    @ApiOperation("删除文件")
    @ApiImplicitParam(name = "fileObject", value = "文件对象")
    @DeleteMapping("/removeFile")
    public Result<Boolean> removeFile(@RequestParam String fileObject) {
        return Result.success(service.removeFile(fileObject));
    }

    @ApiOperation("修改文件为历史文件")
    @ApiImplicitParam(name = "fileObject", value = "文件对象")
    @GetMapping("/updateToHis")
    public Result<Boolean> updateToHis(@RequestParam String fileObject) {
        return Result.success(service.updateToHis(fileObject));
    }

    @ApiOperation("删除指定bucket文件")
    @ApiImplicitParams({@ApiImplicitParam(name = "bucket", value = "存储桶", required = true), @ApiImplicitParam(
            name = "fileObject", value = "文件对象", required = true)})
    @DeleteMapping("/removeFileByBucket")
    public Result<Boolean> removeFileByBucket(@RequestParam String fileObject, @RequestParam String bucket) {
        return Result.success(service.removeFileByBucket(bucket, fileObject));
    }

    @ApiOperation("获取外链")
    @ApiImplicitParam(name = "fileObject", value = "文件对象")
    @GetMapping("/getUrl")
    public Result<String> getUrl(@RequestParam String fileObject) {
        return Result.success(service.getObjectUrl(fileObject));
    }
}
