package cyou.tianshu.charging.controller;

import cyou.tianshu.charging.dto.LoginRequest;
import cyou.tianshu.charging.dto.LoginResponse;
import cyou.tianshu.charging.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apiForChargingStation/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(String username, String password) {
        if(username.contains("@")){
            LoginResponse response = userService.loginByEmail(username, password);
        }
        
        boolean success = userService.login(username, password);
        if (success) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
