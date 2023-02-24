package qtx.cloud.model.dto.auth;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author qtx
 * @date 2022/9/15 20:43
 */
@Data
@Accessors(chain = true)
public class UserRolesDTO {

    private String userCode;

    private List<Integer> roleIds;
}
