package qtx.cloud.model.dto.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;
import qtx.cloud.model.base.PageDateDTO;

/**
 * @author qtx
 * @since 2022/11/16 15:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleDTO extends PageDateDTO {

  private String name;
}
