package qtx.cloud.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qtx
 * @since 2022/10/27 21:24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

  private String userCode;

  private String userName;

  private String role;

  private String ip;

  private String secret;

  private String accessToken;

  private String refreshToken;

  private String time;
}
