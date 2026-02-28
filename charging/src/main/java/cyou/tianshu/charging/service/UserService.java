package cyou.tianshu.charging.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cyou.tianshu.charging.dto.LoginResponse;
import cyou.tianshu.charging.entity.UserInfo;
<<<<<<< Updated upstream
import cyou.tianshu.charging.mapper.UserMapper;
import cyou.tianshu.charging.util.JwtUtil;
=======
import cyou.tianshu.charging.mapper.UserInfoMapper;
>>>>>>> Stashed changes
import cyou.tianshu.charging.util.PasswordUtil;
import jakarta.annotation.Resource;
import cyou.tianshu.charging.dto.RegisterResponse;

@Service
public class UserService {
<<<<<<< Updated upstream
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired
    private PasswordUtil passwordUtil;
    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponse loginByEmail(String username, String password) {
        UserInfo userInfo =  userMapper.selectByEmail(username);
=======
    @Autowired
    private PasswordUtil passwordUtil;
    @Resource
    private UserInfoMapper userRepositoyByEmail;
    public LoginResponse loginByEmail(String username, String password) {
        UserInfo userInfo =  userRepositoyByEmail.findByEmail(username);

>>>>>>> Stashed changes
        if (userInfo == null) {
            return null;
        }
        boolean passwordMatches = passwordUtil.matches(password, userInfo.getPassword());
        if (passwordMatches) {
<<<<<<< Updated upstream
            String token = jwtUtil.generateToken(userInfo); // Generate or retrieve the token as needed
            return new LoginResponse(token, userInfo.getUser_id(), userInfo.getEmail(), userInfo.getPetName(),userInfo.getPhone(), userInfo.getUserImg(), userInfo.getCarVin(), userInfo.getCarType());
=======
            String token = "123"; // Generate or retrieve the token as needed
            return new LoginResponse(token, userInfo.getName(), userInfo.getEmail(),userInfo.getPhone(), userInfo.getCarType(), userInfo.getCarBrand(),userInfo.getRole());
>>>>>>> Stashed changes
        } else {
            return null;
        }
    }
    public LoginResponse loginByPhone(String username, String password) {
<<<<<<< Updated upstream
        UserInfo userInfo =  userMapper.selectByPhone(username);
=======
        UserInfo userInfo =  userRepositoyByEmail.findByPhone(username);
>>>>>>> Stashed changes
        if (userInfo == null) {
            return null;
        }
        boolean passwordMatches = passwordUtil.matches(password, userInfo.getPassword());
        if (passwordMatches) {
<<<<<<< Updated upstream
            String token = jwtUtil.generateToken(userInfo); // Generate or retrieve the token as needed
            return new LoginResponse(token, userInfo.getUser_id(), userInfo.getEmail(), userInfo.getPetName(),userInfo.getPhone(), userInfo.getUserImg(), userInfo.getCarVin(), userInfo.getCarType());
=======
            String token = "123"; // Generate or retrieve the token as needed
            return new LoginResponse(token, userInfo.getName(), userInfo.getEmail(),userInfo.getPhone(), userInfo.getCarType(), userInfo.getCarBrand(),userInfo.getRole());
>>>>>>> Stashed changes
        } else {
            return new LoginResponse("", "", "", "", "", "", "");   
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
<<<<<<< Updated upstream
            if (userMapper.existsByPhone(username)) {
                return new RegisterResponse(false, "Phone number already registered");
            }
            password = passwordUtil.encodePassword(password);
            UserInfo newUser = new UserInfo(username, password,"");
            userMapper.insert(newUser);
=======
            if (userRepositoyByEmail.existsByPhone(username)) {
                return new RegisterResponse(false, "Phone number already registered");
            }
            password = passwordUtil.encodePassword(password);
            UserInfo newUser = new UserInfo("", password, username);
            userRepositoyByEmail.save(newUser);
>>>>>>> Stashed changes
            return new RegisterResponse(true, "User registered successfully");
        }
    }
}