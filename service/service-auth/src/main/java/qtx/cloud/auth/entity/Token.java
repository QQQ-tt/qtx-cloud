package qtx.cloud.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: QTX
 * @Since: 2022/9/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private String token;
    private String name;
    private User user;
}
