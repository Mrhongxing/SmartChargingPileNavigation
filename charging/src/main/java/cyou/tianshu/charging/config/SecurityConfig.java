package cyou.tianshu.charging.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Configuration //声明这是一个配置类
@EnableWebSecurity //启用Spring Security的Web安全支持
@RequiredArgsConstructor //自动生成构造函数，注入依赖
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean //声明一个名为filterChain的Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. 禁用CSRF（REST API不需要）
            .csrf(AbstractHttpConfigurer::disable)
            // 2. 启用跨域配置
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            // 3. 禁用所有Spring Security的安全功能
            //.authorizeHttpRequests(authz -> authz
                //.anyRequest().permitAll()  // 允许所有请求，不做认证拦截
            //)
            // 4. 禁用HTTP Basic认证（重要！）
            .httpBasic(AbstractHttpConfigurer::disable)

            // 5. 禁用表单登录
            .formLogin(AbstractHttpConfigurer::disable)
            // 6. 禁用Session管理，改为无状态（重要！）
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // ← 新增：把 JWT 过滤器插到用户名密码过滤器之前
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

            // ← 授权规则：两种模式二选一，见下文
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/apiForChargingStation/user/login", "/apiForChargingStation/user/register", "/error").permitAll()
                .anyRequest().authenticated()
            )

            // ← 新增：统一 401，避免默认返回 HTML
            .exceptionHandling(ex -> ex.authenticationEntryPoint((req, res, e) -> {
                res.setStatus(401);
                res.setContentType("application/json;charset=UTF-8");
                res.getWriter().write("{\"code\":401,\"msg\":\"未登录或登录已过期\"}");
            }));
            
            // 6. 禁用匿名用户
            //.anonymous(AbstractHttpConfigurer::disable);
        
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
            "http://localhost:3001",  // React开发服务器
            "http://localhost:5173",  // Vite开发服务器
            "http://localhost:3000",   // 其他可能的端口
            "https://charging.tianshu.cyou" ,    // 生产环境可能的地址
            "https://charging.hong.email" ,
            "https://www.hong.email"  // 生产环境可能的地址
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