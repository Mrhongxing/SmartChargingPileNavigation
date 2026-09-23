package cyou.tianshu.charging.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 认证通过后放入 SecurityContext 的 principal。
 * 只放必要字段，不要放密码。
 */
@Data
//@NoArgsConstructor// 无参构造函数
@AllArgsConstructor// 全参构造函数
public class LoginUser {
    private Long id;
    private String phone;
    private String role;
}