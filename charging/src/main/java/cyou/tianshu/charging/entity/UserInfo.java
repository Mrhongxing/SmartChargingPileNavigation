package cyou.tianshu.charging.entity;

import com.baomidou.mybatisplus.annotation.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor  // 添加无参构造函数
@AllArgsConstructor // 添加全参构造函数
@TableName("smart_charging_user")
public class UserInfo {
    @TableId(type=IdType.AUTO)
    private Long id;// 用户ID
    @TableField("name")
    private String name;// 用户名
    @TableField("email")
    private String email;// 用户邮箱
    @TableField("password")
    private String password;// 用户密码
    @TableField("role")
    private String role;// 用户角色
    @TableField("created_at")
    private java.time.LocalDateTime created_at;// 创建时间
    @TableField("updated_at")
    private java.time.LocalDateTime updated_at;// 更新时间
    @TableField("phone")
    private String phone;// 用户手机号
    @TableField("car_type")
    private String carType;// 用户车型
    @TableField("car_brand")
    private String carBrand;// 用户车品牌
    public UserInfo(String username, String password) {
        this.name = username;
        this.password = password;
    }
    public UserInfo(String username, String password, String phone) {
        this.name = username;
        this.password = password;
        this.phone = phone;
    }
}
