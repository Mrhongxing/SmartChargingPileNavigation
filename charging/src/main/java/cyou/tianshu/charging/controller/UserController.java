package cyou.tianshu.charging.controller;

import cyou.tianshu.charging.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apiForChargingStation/user")
public class UserController {
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<?> login(String username, String password) {
        boolean success = userService.login(username, password);
        if (success) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
