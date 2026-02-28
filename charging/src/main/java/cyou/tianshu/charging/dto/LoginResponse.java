package cyou.tianshu.charging.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String name;
    private String email;
    private String phone;
    private String carType;
    private String carBrand;
    private String role;
    public LoginResponse(String token, String name, String email, String phone, String carType, String carBrand, String role) {
        this.token = token;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.carType = carType;
        this.carBrand = carBrand;
        this.role = role;
    }
}
