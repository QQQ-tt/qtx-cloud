package qtx.cloud.model.dto.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import qtx.cloud.model.base.PageDTO;

/**
 * @author qtx
 * @since 2022/12/7 15:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SysUserDepartmentDTO extends PageDTO {

  private String departmentCode;

  private String userName;
}
