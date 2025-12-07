package cyou.tianshu.charging.dto;

public class LoginResponse {
    private String token;
    private Integer user_id;
    private String email;
    private String pet_name;
    private String phone;
    private String user_img;
    private String car_vin;
    private String car_type;

    public LoginResponse(String token, Integer user_id, String email, String pet_name, String phone, String user_img, String car_vin, String car_type) {
        this.token = token;
        this.user_id = user_id;
        this.email = email;
        this.pet_name = pet_name;
        this.phone = phone;
        this.user_img = user_img;
        this.car_vin = car_vin;
        this.car_type = car_type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPet_name() {
        return pet_name;
    }

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public String getCar_vin() {
        return car_vin;
    }

    public void setCar_vin(String car_vin) {
        this.car_vin = car_vin;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public LoginResponse() {
        this.token = "";
        this.user_id = 0;
        this.email = "";
        this.pet_name = "";
        this.phone = "";
        this.user_img = "";
        this.car_vin = "";
        this.car_type = "";
    }
}
