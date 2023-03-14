package qtx.cloud.model.dto.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;
import qtx.cloud.model.base.PageDateDTO;

/**
 * @author qtx
 * @since 2022/11/17 9:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DepartmentDTO extends PageDateDTO {

  private String name;
}
