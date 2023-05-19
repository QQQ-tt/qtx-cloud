package qtx.cloud.model.vo.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qtx
 * @since 2022/8/31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateVO {
  private String name;
  private String userCard;
  private String password;
}
