package cyou.tianshu.charging.util;

import cyou.tianshu.charging.entity.LoginUser;
import cyou.tianshu.charging.entity.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey key;
    private final long expireMillis;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expire-minutes:120}") long expireMinutes) {
        // 要求 secret 至少 32 字节，否则抛 WeakKeyException
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expireMillis = expireMinutes * 60_000L;
    }

    /**
     * 登录成功后调用，用你的 User 实体签发 token。
     * 不修改 User 实体，不修改登录逻辑。
     */
    public String generate(UserInfo user) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .claim("phone", user.getPhone())
                .claim("role", user.getRole())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expireMillis)))
                .signWith(key)                       // 默认 HS256，密钥长度决定算法
                .compact();
    }

    /**
     * 解析失败（过期 / 签名错 / 格式错）统一返回 null。
     * 让调用方（过滤器）保持"静默失败"语义，不抛异常。
     */
    public LoginUser parse(String token) {
        try {
            Claims c = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String role = c.get("role", String.class);   // JSON 数字反序列化可能是 Integer 或 Long
            return new LoginUser(
                    Long.valueOf(c.getSubject()),
                    c.get("phone", String.class),
                    role == null ? null : role);
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    /** 取剩余有效毫秒数，供 Redis 黑名单设置 TTL 用 */
    public long remainingMillis(String token) {
        try {
            Date exp = Jwts.parser().verifyWith(key).build()
                    .parseSignedClaims(token).getPayload().getExpiration();
            return Math.max(0, exp.getTime() - System.currentTimeMillis());
        } catch (Exception e) {
            return 0;
        }
    }
}