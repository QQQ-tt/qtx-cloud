package qtx.cloud.service.config.feign;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import qtx.cloud.java.Result;
import qtx.cloud.java.enums.DataEnums;
import qtx.cloud.java.exception.DataException;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author qtx
 * @since 2023/2/2 13:38
 */
@Slf4j
public class ExceptionDecoder extends SpringDecoder {

    public ExceptionDecoder(ObjectFactory<HttpMessageConverters> messageConverters,
                            ObjectProvider<HttpMessageConverterCustomizer> customizers) {
        super(messageConverters, customizers);
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        String body = "";
        if (response.body() != null) {
            try {
                body = Util.toString(response.body().asReader(Charset.defaultCharset()));
            } catch (IOException e) {
                throw new DataException(DataEnums.SERVICE_FAILED);
            }
            log.info("feign-body-info:{}", body);
            //将字符串转换为自定义的异常信息
            Result<?> object = null;
            try {
                object = JSONObject.parseObject(body, Result.class);
                // 项目内 不能用 msg 做参数名
            } catch (Exception e) {
                log.info("feign-body-error:{}", e.getMessage());
            }
            if (object != null && StringUtils.isNotBlank(object.getMsg()) && object.getCode() != DataEnums.SUCCESS.getCode()) {
                throw new DataException(object.getMsg(), object.getCode());
            }
        }
        return super.decode(response.toBuilder().body(body, StandardCharsets.UTF_8).build(), type);
    }


    public static String toString(Reader reader) throws IOException {
        if (reader == null) {
            return null;
        }
        StringBuilder to = new StringBuilder();
        CharBuffer charBuf = CharBuffer.allocate(0x800);
        // must cast to super class Buffer otherwise break when running with java 11
        while (reader.read(charBuf) != -1) {
            charBuf.flip();
            to.append(charBuf);
            charBuf.clear();
        }
        return to.toString();
    }
}
