package qtx.cloud.service.utils;

import qtx.cloud.java.enums.PrefixEnums;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author huang
 * @since 2022/12/1 22:35
 */
public class GenerateNumberUtils {

    /**
     * 生成编号：自定义前缀+日期+9位随机数
     * @param prefix 自定义前缀
     * @return
     */
    public static String getNumber(PrefixEnums prefix) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", java.util.Locale.CHINA);
        String str = sdf.format(now);
        String number = prefix.toString() + str;
        StringBuilder randomNum = new StringBuilder();
        for (int i = 1; i <= 9; i++) {
            int x = (int) (Math.random() * 10);
            randomNum.insert(0, x);
        }
        return number + randomNum;
    }

}
