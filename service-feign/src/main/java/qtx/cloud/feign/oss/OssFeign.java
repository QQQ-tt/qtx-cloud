package qtx.cloud.feign.oss;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : gx
 * @since : 2022/11/30 14:20
 */
@FeignClient(name = "service-oss")
public interface OssFeign {
    /**
     * 获取外链
     *
     * @param fileObject
     * @return
     */
    @GetMapping("/api/file/getUrl")
    String getUrl(@RequestParam(value = "fileObject",name = "文件对象") String fileObject);

}
