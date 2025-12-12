package cyou.tianshu.charging.controller;

import cyou.tianshu.charging.dto.LoginResponse;
import cyou.tianshu.charging.dto.RegisterResponse;
import cyou.tianshu.charging.dto.LoginRequest;
import cyou.tianshu.charging.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apiForChargingStation/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response;
        if(loginRequest.getUsername().contains("@")){
            response = userService.loginByEmail(loginRequest.getUsername(), loginRequest.getPassword());
        }else{
            response = userService.loginByPhone(loginRequest.getUsername(), loginRequest.getPassword());
        }
        if (response.getToken() != null && !response.getToken().isEmpty()) {
            return ResponseEntity.ok(response);
        } else {//再次尝试登录
            if(loginRequest.getUsername().contains("@")){
                response = userService.loginByEmail(loginRequest.getUsername(), loginRequest.getPassword());
            }else{
                response = userService.loginByPhone(loginRequest.getUsername(), loginRequest.getPassword());
            }
            if (response.getToken() != null && !response.getToken().isEmpty()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(401).body(new LoginResponse());
            }
        }
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody LoginRequest registerRequest) {
        // Registration logic here
        try{
            RegisterResponse response = userService.registerUser(registerRequest.getUsername(), registerRequest.getPassword());
            if (!response.isSuccess()) {
                return ResponseEntity.status(400).body(new RegisterResponse(false, response.getMessage()));
            }else{
                return ResponseEntity.ok(response);
            }
        }catch(Exception  e){
            return ResponseEntity.status(500).body(new RegisterResponse(false, e.toString()));
        }
        
    }
}
