package cyou.tianshu.charging.entity;

<<<<<<< Updated upstream
import lombok.Data;

@Data
public class UserInfo {
    private Integer user_id;// 用户ID
    private String email;// 用户邮箱
    private String password;// 用户密码
    private String petName; //用户昵称
    private String phone;// 用户电话
    private String userImg;// 用户头像
    private String carVin;// 车辆识别码
    private String carType;// 车辆类型
    private Integer userRole;// 用户角色
    public UserInfo() {
        this.email = "";
        this.password = "";
        this.phone = "";
        this.userImg = "";
        this.carVin = "";
        this.carType = "";
        this.petName = "";
        this.userRole = 0;
    }
    public UserInfo(String email, String password) {
        this.email = email;
=======
import com.baomidou.mybatisplus.annotation.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor  // 添加无参构造函数
@AllArgsConstructor // 添加全参构造函数
@TableName("user_info")
public class UserInfo {
    @TableId(type=IdType.AUTO)
    private Long user_id;// 用户ID
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
>>>>>>> Stashed changes
        this.password = password;
    }
    public UserInfo(String username, String password, String phone) {
        this.name = username;
        this.password = password;
        this.phone = phone;
<<<<<<< Updated upstream
        this.password = password;
        this.email = "";
        this.userImg = "";
        this.carVin = "";
        this.carType = "";
        this.petName = "";
        this.userRole = 1;
    }
    public String getEmail() {
        return email;
    }
    public Integer getUserId() {
        return user_id;
    }
    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPetName() {
        return petName;
    }
    public void setPetName(String petName) {
        this.petName = petName;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getUserImg() {
        return userImg;
    }
    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }
    public String getCarVin() {
        return carVin;
    }
    public void setCarVin(String carVin) {
        this.carVin = carVin;
    }
    public String getCarType() {
        return carType;
    }
    public void setCarType(String carType) {
        this.carType = carType;
    }
    public Integer getUserRole() {
        return userRole;
    }
    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
=======
>>>>>>> Stashed changes
    }
}
