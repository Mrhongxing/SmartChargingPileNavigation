package cyou.tianshu.charging.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 认证通过后放入 SecurityContext 的 principal。
 * 只放必要字段，不要放密码。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser {
    private Long id;
    private String phone;
    private String role;
}