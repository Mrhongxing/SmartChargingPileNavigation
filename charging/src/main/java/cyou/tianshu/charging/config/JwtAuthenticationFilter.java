package cyou.tianshu.charging.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * 旁路式 JWT 过滤器：
 *  - 有合法 token → 把认证信息塞进 SecurityContext
 *  - 无 token / token 无效 → 什么都不做，直接放行
 *  - 从不抛异常、从不返回 401
 * 因此对现有 Controller 逻辑零影响。
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String HEADER = "Authorization";
    public static final String PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {

        // 预检请求不带 Authorization，直接放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader(HEADER);
        if (header != null && header.startsWith(PREFIX)) {
            LoginUser loginUser = jwtUtil.parse(header.substring(PREFIX.length()));
            if (loginUser != null
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 根据角色设置权限
                String role = loginUser.getRole() != null && loginUser.getRole() == "1"
                        ? "ROLE_ADMIN" : "ROLE_USER";
                var auth = new UsernamePasswordAuthenticationToken(
                        loginUser, null, List.of(new SimpleGrantedAuthority(role)));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        // 无论成功与否都继续，由 SecurityConfig 的授权规则裁决
        chain.doFilter(request, response);
    }
}