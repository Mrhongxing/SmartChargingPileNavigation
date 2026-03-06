package cyou.tianshu.charging.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import cyou.tianshu.charging.dto.LoginResponse;
import cyou.tianshu.charging.entity.UserInfo;
import cyou.tianshu.charging.mapper.UserInfoMapper;
import cyou.tianshu.charging.util.PasswordUtil;
import jakarta.annotation.Resource;
import cyou.tianshu.charging.dto.RegisterResponse;
import cyou.tianshu.charging.dto.UpdateRequest;

@Service
public class UserService {
    @Autowired
    private PasswordUtil passwordUtil;
    @Resource
    private UserInfoMapper userRepositoyByEmail;
    public LoginResponse loginByEmail(String username, String password) {
        UserInfo userInfo =  userRepositoyByEmail.findByEmail(username);

        if (userInfo == null) {
            return new LoginResponse(null, "", "", "", "", "", "", "");
        }
        boolean passwordMatches = passwordUtil.matches(password, userInfo.getPassword());
        if (passwordMatches) {
            String token = "123"; // Generate or retrieve the token as needed
            return new LoginResponse(userInfo.getId(), token, userInfo.getName(), userInfo.getEmail(),userInfo.getPhone(), userInfo.getCarType(), userInfo.getCarBrand(),userInfo.getRole());
        } else {
            return new LoginResponse(null, "", "", "", "", "", "", "");
        }
    }
    public LoginResponse loginByPhone(String username, String password) {
        UserInfo userInfo =  userRepositoyByEmail.findByPhone(username);
        if (userInfo == null) {
            return new LoginResponse(0L, "", "", "", "", "", "", "");
        }
        boolean passwordMatches = passwordUtil.matches(password, userInfo.getPassword());
        if (passwordMatches) {
            String token = "123"; // Generate or retrieve the token as needed
            return new LoginResponse(userInfo.getId(), token, userInfo.getName(), userInfo.getEmail(),userInfo.getPhone(), userInfo.getCarType(), userInfo.getCarBrand(),userInfo.getRole());
        } else {
            return new LoginResponse(0L, "", "", "", "", "", "", "");   
        }
    }
    public RegisterResponse registerUser(String username, String password) {
        if(username.contains("@")){
            // Register by email
            if (userRepositoyByEmail.existsByEmail(username)) {
                return new RegisterResponse(false, "Email already registered");
            }
            password = passwordUtil.encodePassword(password);
            UserInfo newUser = new UserInfo(username, password);
            userRepositoyByEmail.save(newUser);
            return new RegisterResponse(true, "User registered successfully");
        }else{
            // Register by phone
            if (userRepositoyByEmail.existsByPhone(username)) {
                return new RegisterResponse(false, "Phone number already registered");
            }
            password = passwordUtil.encodePassword(password);
            UserInfo newUser = new UserInfo("", password, username);
            userRepositoyByEmail.save(newUser);
            return new RegisterResponse(true, "User registered successfully");
        }
    }
    public Boolean updateUserInfo(UpdateRequest updateRequest) {
        try{
            List<UserInfo> userInfoList = userRepositoyByEmail.findByUserId(updateRequest.getId());
            if(userInfoList.isEmpty()){
                return false;
            }
            UserInfo userInfo = userInfoList.get(0);
            userInfo.setName(updateRequest.getName());
            userInfo.setEmail(updateRequest.getEmail());
            userInfo.setPhone(updateRequest.getPhone());

            UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
updateWrapper.eq("id", userInfo.getId());
userRepositoyByEmail.update(userInfo, updateWrapper);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}