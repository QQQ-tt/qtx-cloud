package qtx.cloud.oss.controller.api;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qtx.cloud.oss.service.OssService;

/**
 * @author : qtx
 * @since : 2022/11/30 14:21
 */
@Api(hidden = true)
@RestController
@RequestMapping("/api/file")
public class OssApiController {
    @Autowired
    private OssService service;

    @GetMapping("/getUrl")
    public String getUrl(@RequestParam("fileObject") String fileObject) {
        return service.getObjectUrl(fileObject);
    }

}
