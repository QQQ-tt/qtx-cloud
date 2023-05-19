package qtx.cloud.service.utils;

import com.alibaba.fastjson.JSONArray;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import qtx.cloud.java.Result;
import qtx.cloud.java.enums.DataEnums;

/**
 * @author qtx
 * @since 2022/11/2
 */
@Setter
@Component
public class CommonMethod {

  private String userCard;

  private String ip;

  /**
   * 过滤器返回信息
   *
   * @param response response
   * @param dataEnums 错误信息
   * @throws IOException io失败
   */
  public void failed(HttpServletResponse response, DataEnums dataEnums) throws IOException {
    response.setCharacterEncoding("utf-8");
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    // 设置响应状态码
    response.setStatus(HttpServletResponse.SC_OK);
    // 输入响应内容
    PrintWriter writer = response.getWriter();
    String s = JSONArray.toJSON(Result.failed(dataEnums)).toString();
    writer.write(s);
    writer.flush();
  }

  /**
   * 获取当前登录人userCard
   *
   * @return userCard
   */
  public String getUser() {
    return userCard;
  }

  /**
   * 获取请求ip
   *
   * @return ip
   */
  public String getIp() {
    return ip;
  }
}
