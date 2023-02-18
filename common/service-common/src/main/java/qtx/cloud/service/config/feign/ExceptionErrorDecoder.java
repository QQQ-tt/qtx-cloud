package qtx.cloud.service.config.feign;

import com.alibaba.fastjson.JSONObject;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import qtx.cloud.java.Result;
import qtx.cloud.java.enums.DataEnums;
import qtx.cloud.java.exception.DataException;
import qtx.cloud.java.exception.FeignException;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author qtx
 * @since 2023/1/30 15:38
 */
@Slf4j
public class ExceptionErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        if (response.body() != null) {
            String body;
            try {
                body = Util.toString(response.body().asReader(Charset.defaultCharset()));
            } catch (IOException e) {
                throw new FeignException(DataEnums.SERVICE_FAILED);
            }
            log.info("feign-body-error:{}", body);
            //将字符串转换为自定义的异常信息
            Result<?> object = JSONObject.parseObject(body, Result.class);
            //返回异常信息，随便返回哪个异常都行，主要是将code和message透传到前端
            return new DataException(object.getMsg(), object.getCode());
        }
        //默认返回"系统异常,请稍后重试"
        return new FeignException(DataEnums.SERVICE_FAILED);
    }
}
