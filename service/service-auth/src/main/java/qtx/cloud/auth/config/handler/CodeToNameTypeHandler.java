package qtx.cloud.auth.config.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;
import qtx.cloud.java.constant.StaticConstant;
import qtx.cloud.service.utils.RedisUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

/**
 * @author qtx
 * @since 2023/5/20 19:44
 */
@Slf4j
@Component
public class CodeToNameTypeHandler extends BaseTypeHandler<String> {

  private final RedisUtils redisUtils;

  public Map<Object, Object> codeToName;

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
    if (codeToName == null){
      codeToName = redisUtils.getHashMsg(StaticConstant.SYS_USER + StaticConstant.REDIS_INFO_1);
    }
    return string == null ? null : (String) Optional.ofNullable(codeToName.get(string)).orElse(" ");
  }
}
