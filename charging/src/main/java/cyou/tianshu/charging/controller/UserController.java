package cyou.tianshu.charging.controller;

import cyou.tianshu.charging.dto.LoginResponse;
import cyou.tianshu.charging.dto.RegisterResponse;
import cyou.tianshu.charging.dto.UpdateRequest;
import cyou.tianshu.charging.entity.UserInfo;
import cyou.tianshu.charging.dto.LoginRequest;
import cyou.tianshu.charging.service.UserService;
import lombok.experimental.Delegate;

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
        if (response.getToken() == null || response.getToken().isEmpty() || response.getId() == null || response.getId() == 0L) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.ok(response);
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
        }catch(Exception e){
            return ResponseEntity.status(500).body(new RegisterResponse(false, e.toString()));
        }
        
    }
    @PostMapping("/updateUserInfo")
    public ResponseEntity<Boolean> updateUserInfo(@RequestBody UpdateRequest updateRequest) {
        // Update user info logic here
        try{
            Boolean success = userService.updateUserInfo(updateRequest);
            if (success) {
                return ResponseEntity.ok(success);
            } else {
                return ResponseEntity.status(500).body(false);
            }
        }catch(Exception e){
            return ResponseEntity.status(500).body(false);
        }
    }
}
