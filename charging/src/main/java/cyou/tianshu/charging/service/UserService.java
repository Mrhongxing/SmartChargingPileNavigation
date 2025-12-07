package cyou.tianshu.charging.service;

import org.springframework.beans.factory.annotation.Autowired;

import cyou.tianshu.charging.dto.LoginResponse;
import cyou.tianshu.charging.entity.UserInfo;
import cyou.tianshu.charging.respository.UserRepositoyByEmail;
import cyou.tianshu.charging.util.PasswordUtil;

public class UserService {
    @Autowired
    private UserRepositoyByEmail userRepositoyByEmail;
    @Autowired
    private PasswordUtil passwordUtil;

    public LoginResponse loginByEmail(String username, String password) {
        UserInfo userInfo =  userRepositoyByEmail.findByEmail(username);
        if (userInfo == null) {
            return new LoginResponse();
        }
        boolean passwordMatches = passwordUtil.matches(password, userInfo.getPassword());
        if (passwordMatches) {
            String token = ""; // Generate or retrieve the token as needed
            return new LoginResponse(token, userInfo.getUser_id(), userInfo.getEmail(), userInfo.getPetName(),userInfo.getPhone(), userInfo.getUserImg(), userInfo.getCarVin(), userInfo.getCarType());
        } else {
            return new LoginResponse();
        }
    }
}