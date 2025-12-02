package cyou.tianshu.charging.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer user_id;// 用户ID
    @Column(name = "email", nullable = false, unique = true)
    private String email;// 用户邮箱
    @Column(name = "password_hash", nullable = false)
    private String password;// 用户密码
    @Column(name = "phone", nullable = true)
    private String phone;// 用户电话
    @Column(name = "user_img", nullable = true)
    private String userImg;// 用户头像
    @Column(name = "car_vin", nullable = true)
    private String carVin;// 车辆识别码
    @Column(name = "car_type", nullable = true)
    private String carType;// 车辆类型
    public UserInfo() {
    }
    public UserInfo(String email, String password, String phone, String userImg) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.userImg = userImg;
    }
    public String getEmail() {
        return email;
    }
    public Integer getUser_id() {
        return user_id;
    }
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
