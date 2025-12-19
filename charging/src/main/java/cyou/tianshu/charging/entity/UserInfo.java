package cyou.tianshu.charging.entity;

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
        this.password = password;
        this.phone = "";
        this.userImg = "";
        this.carVin = "";
        this.carType = "";
        this.petName = "";
        this.userRole = 1;
    }
    public UserInfo(String phone, String password,String model) {
        this.phone = phone;
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
    }
}
