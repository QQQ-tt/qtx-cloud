package qtx.cloud.model.vo.auth;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author qtx
 * @since 2022/8/31
 */
@Data
@Accessors(chain = true)
public class LoginVO {
    private String name;

    private String accessToken;

    private String refreshToken;

    private String userCode;
}
