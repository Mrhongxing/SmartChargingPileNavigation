package cyou.tianshu.charging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching // 启用缓存
@SpringBootApplication
public class ChargingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChargingApplication.class, args);
	}

}
