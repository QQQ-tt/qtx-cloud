package qtx.cloud.service.utils;

/**
 * 受理编号
 * @author huang
 * @since 2022/11/17 10:56
 */
public class AcceptNumUtils {

    private static final Integer ONE = 1;
    private static final Integer TWO = 2;
    private static final Integer THREE = 3;

    /**
     * prefix为自定义前缀，想要几位数可以自行修改0的数量
     * id获取方式为从数据库表中查询现在最大id+1
     */
    public static String getAcceptNumber(Long number) {
        //判断位数
        String s = number + "";
        int count = s.length();
        String producerNum = "";
        if (ONE == count) {
            producerNum += "00" + String.valueOf(number);
        } else if (TWO == count) {
            producerNum += "0" + String.valueOf(number);
        } else {
            producerNum += String.valueOf(number);
        }
        return producerNum;
    }

}
