package qtx.cloud.model.dto.auth;

import lombok.Data;
import qtx.cloud.model.base.PageDateDTO;

/**
 * @author qtx
 * @since 2022/11/17 9:23
 */
@Data
public class DepartmentDTO extends PageDateDTO {

    private String name;
}
