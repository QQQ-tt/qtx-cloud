package qtx.cloud.model.dto.auth;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author qtx
 * @since 2022/9/15 20:43
 */
@Data
@Accessors(chain = true)
public class UserRolesDTO {

  private String userCode;

  private List<Integer> roleIds;
}
