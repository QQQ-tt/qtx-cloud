package qtx.cloud.service.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * 用户将double类型的数据格式化成小数点后两位的字符串数据：如输出为“900.00”.
 */
public class OyzDoubleSerialize extends JsonSerializer<Double> {

    private DecimalFormat df = new DecimalFormat("#0.00");

    public OyzDoubleSerialize() {
    }

    @Override
    public void serialize(Double o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (o.toString() != null && !"-".equals(o)) {
            Double dd = Double.parseDouble(o.toString());
            jsonGenerator.writeString(df.format(dd));
        } else {
            jsonGenerator.writeString(o.toString());
        }
    }


}