package qtx.cloud.model.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: QTX
 * @Since: 2022/8/31
 */
@Data
public class SysUserPasswordDTO {
    private String userCode;

    @NotNull
    private String newPassword;
}
