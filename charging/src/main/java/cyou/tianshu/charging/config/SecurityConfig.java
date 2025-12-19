package cyou.tianshu.charging.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // ✅ 开启 CORS（关键）
            .cors(cors -> {})

            // 关闭 CSRF
            .csrf(AbstractHttpConfigurer::disable)

            // 所有请求放行
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )

            // 关闭所有认证机制
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable)

            // 无状态
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // 禁用 SecurityContext
            .securityContext(AbstractHttpConfigurer::disable)

            .requestCache(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
