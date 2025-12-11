package cyou.tianshu.charging.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    /**
     * 最简单的安全配置：只解决跨域，完全开放访问
     * 因为JWT验证你在Controller中自己做了
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. 禁用CSRF（REST API不需要）
            .csrf(AbstractHttpConfigurer::disable)
            
            // 2. 启用跨域配置
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // 3. 禁用所有Spring Security的安全功能
            .authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll()  // 允许所有请求，不做认证拦截
            )
            
            // 4. 禁用HTTP Basic认证（重要！）
            .httpBasic(AbstractHttpConfigurer::disable)
            
            // 5. 禁用表单登录
            .formLogin(AbstractHttpConfigurer::disable)
            
            // 6. 禁用匿名用户
            .anonymous(AbstractHttpConfigurer::disable);
        
        return http.build();
    }
    
    /**
     * 跨域配置 - 允许前端访问
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // 允许的来源（前端地址）
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:3000",  // React开发服务器
            "http://localhost:8080",  // Vue开发服务器或后端地址
            "http://localhost:5173",  // Vite开发服务器
            "http://localhost:8081"   // 其他可能的端口
        ));
        
        // 允许的HTTP方法
        configuration.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"
        ));
        
        // 允许的请求头（必须包含Authorization，因为你要传JWT）
        configuration.setAllowedHeaders(Arrays.asList(
            "Authorization",      // JWT token在这里
            "Content-Type",
            "X-Requested-With",
            "Accept",
            "Origin",
            "X-Auth-Token",      // 自定义token头（如果有）
            "X-User-Id"          // 自定义用户ID头（如果有）
        ));
        
        // 暴露的响应头（让前端能访问）
        configuration.setExposedHeaders(Arrays.asList(
            "Authorization",
            "Content-Disposition"  // 文件下载时需要
        ));
        
        // 是否允许发送凭证（cookies等）
        configuration.setAllowCredentials(true);
        
        // 预检请求的缓存时间（秒）
        configuration.setMaxAge(3600L);
        
        // 将配置应用到所有路径
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}