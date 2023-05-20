package qtx.cloud.auth.config.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;
import qtx.cloud.java.constant.StaticConstant;
import qtx.cloud.service.utils.RedisUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author qtx
 * @since 2023/5/20 19:44
 */
@Component
public class CodeToNameTypeHandler extends BaseTypeHandler<String> {

  private final RedisUtils redisUtils;

  public CodeToNameTypeHandler(RedisUtils redisUtils) {
    this.redisUtils = redisUtils;
  }

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
    ps.setString(i, parameter);
  }

  @Override
  public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
    String string = rs.getString(columnName);
    return convertCodeToName(string);
  }

  @Override
  public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    String string = rs.getString(columnIndex);
    return convertCodeToName(string);
  }

  @Override
  public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    String string = cs.getString(columnIndex);
    return convertCodeToName(string);
  }

  private String convertCodeToName(String string) {
    return string == null ? null : (String) redisUtils.getHashMsg(StaticConstant.SYS_USER + string + StaticConstant.REDIS_INFO, "userName");
  }
}
