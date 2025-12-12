package cyou.tianshu.charging.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cyou.tianshu.charging.dto.LoginResponse;
import cyou.tianshu.charging.entity.UserInfo;
import cyou.tianshu.charging.mapper.UserMapper;
import cyou.tianshu.charging.util.PasswordUtil;
import cyou.tianshu.charging.dto.RegisterResponse;

@Service
public class UserService {
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired
    private PasswordUtil passwordUtil;

    public LoginResponse loginByEmail(String username, String password) {
        UserInfo userInfo =  userMapper.selectByEmail(username);
        if (userInfo == null) {
            return new LoginResponse();
        }
        boolean passwordMatches = passwordUtil.matches(password, userInfo.getPassword());
        if (passwordMatches) {
            String token = "123"; // Generate or retrieve the token as needed
            return new LoginResponse(token, userInfo.getUser_id(), userInfo.getEmail(), userInfo.getPetName(),userInfo.getPhone(), userInfo.getUserImg(), userInfo.getCarVin(), userInfo.getCarType());
        } else {
            return new LoginResponse();
        }
    }
    public LoginResponse loginByPhone(String username, String password) {
        UserInfo userInfo =  userMapper.selectByPhone(username);
        if (userInfo == null) {
            return new LoginResponse();
        }
        boolean passwordMatches = passwordUtil.matches(password, userInfo.getPassword());
        if (passwordMatches) {
            String token = "123"; // Generate or retrieve the token as needed
            return new LoginResponse(token, userInfo.getUser_id(), userInfo.getEmail(), userInfo.getPetName(),userInfo.getPhone(), userInfo.getUserImg(), userInfo.getCarVin(), userInfo.getCarType());
        } else {
            return new LoginResponse();
        }
    }
    public RegisterResponse registerUser(String username, String password) {
        if(username.contains("@")){
            // Register by email
            if (userMapper.existsByEmail(username)) {
                return new RegisterResponse(false, "Email already registered");
            }
            password = passwordUtil.encodePassword(password);
            UserInfo newUser = new UserInfo(username, password);
            userMapper.insert(newUser);
            return new RegisterResponse(true, "User registered successfully");
        }else{
            // Register by phone
            if (userMapper.existsByPhone(username)) {
                return new RegisterResponse(false, "Phone number already registered");
            }
            password = passwordUtil.encodePassword(password);
            UserInfo newUser = new UserInfo(username, password,"");
            userMapper.insert(newUser);
            return new RegisterResponse(true, "User registered successfully");
        }
    }
}