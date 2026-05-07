package cyou.tianshu.charging.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/apiForChargingStation/user")
public class Try {
    @PostMapping("/try")
    public String tryEndpoint() {
        return "This is a try endpoint!";
    }
}