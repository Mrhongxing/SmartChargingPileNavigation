package cyou.tianshu.charging.util;

import cyou.tianshu.charging.entity.UserInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类 - 适配你的UserInfo结构
 * 
 * Token中存储的字段：
 * - userId (user_id)
 * - email
 * - phone
 * - userRole
 * - petName
 * 
 * 使用示例：
 * 1. 生成Token：String token = jwtUtil.generateToken(userInfo);
 * 2. 验证Token：boolean valid = jwtUtil.validateToken(token);
 * 3. 解析Token：UserInfo user = jwtUtil.parseToken(token);
 */
@Component
public class JwtUtil {
    
    // ==================== 配置参数 ====================
    
    @Value("${jwt.secret:charging-station-secret-key-2025-12-10-change-me}")
    private String secret;
    
    @Value("${jwt.expiration:86400}") // 默认24小时(秒)
    private Long expiration;
    
    @Value("${jwt.issuer:charging-station-system}")
    private String issuer;
    
    // ==================== 核心方法 ====================
    
    /**
     * 根据UserInfo生成JWT Token
     * 
     * @param userInfo 用户信息实体
     * @return JWT Token字符串
     */
    public String generateToken(UserInfo userInfo) {
        if (userInfo == null) {
            throw new IllegalArgumentException("用户信息不能为空");
        }
        
        Map<String, Object> claims = new HashMap<>();
        // 存储UserInfo的所有关键信息
        claims.put("userId", userInfo.getUser_id());
        claims.put("email", userInfo.getEmail());
        claims.put("phone", userInfo.getPhone());
        claims.put("petName", userInfo.getPetName());
        claims.put("userRole", userInfo.getUserRole());
        claims.put("userImg", userInfo.getUserImg());
        claims.put("carVin", userInfo.getCarVin());
        claims.put("carType", userInfo.getCarType());
        claims.put("created", new Date());
        
        // 使用邮箱或手机号作为subject（优先邮箱）
        String subject = userInfo.getEmail();
        if (subject == null || subject.trim().isEmpty()) {
            subject = userInfo.getPhone();
        }
        if (subject == null || subject.trim().isEmpty()) {
            subject = "user_" + userInfo.getUser_id();
        }
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    /**
     * 生成Token（简化版 - 只传关键信息）
     * 
     * @param userId 用户ID
     * @param email 邮箱
     * @param phone 手机号
     * @param userRole 用户角色
     * @param petName 昵称
     * @return JWT Token
     */
    public String generateToken(Integer userId, String email, String phone, 
                                Integer userRole, String petName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("email", email);
        claims.put("phone", phone);
        claims.put("userRole", userRole);
        claims.put("petName", petName);
        claims.put("created", new Date());
        
        String subject = email != null ? email : phone;
        if (subject == null) {
            subject = "user_" + userId;
        }
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    /**
     * 验证Token是否有效
     * 
     * @param token JWT Token
     * @return true=有效，false=无效
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
    /**
     * 验证Token并检查是否过期
     * 
     * @param token JWT Token
     * @return true=有效且未过期，false=无效或已过期
     */
    public boolean validateTokenNotExpired(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            
            // 检查过期时间
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 解析Token并返回UserInfo对象
     * 
     * @param token JWT Token
     * @return UserInfo对象
     * @throws RuntimeException Token无效时抛出异常
     */
    public UserInfo parseToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            
            // 检查是否过期
            if (claims.getExpiration().before(new Date())) {
                throw new RuntimeException("Token已过期");
            }
            
            // 构建UserInfo对象
            UserInfo userInfo = new UserInfo();
            userInfo.setUser_id(claims.get("userId", Integer.class));
            userInfo.setEmail(claims.get("email", String.class));
            userInfo.setPhone(claims.get("phone", String.class));
            userInfo.setPetName(claims.get("petName", String.class));
            userInfo.setUserRole(claims.get("userRole", Integer.class));
            
            // 可选字段
            String userImg = claims.get("userImg", String.class);
            if (userImg != null) userInfo.setUserImg(userImg);
            
            String carVin = claims.get("carVin", String.class);
            if (carVin != null) userInfo.setCarVin(carVin);
            
            String carType = claims.get("carType", String.class);
            if (carType != null) userInfo.setCarType(carType);
            
            return userInfo;
            
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token已过期: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("Token格式错误: " + e.getMessage());
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Token无效: " + e.getMessage());
        } catch (SignatureException e) {
            throw new RuntimeException("Token签名错误: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Token参数错误: " + e.getMessage());
        }
    }
    
    /**
     * 从Authorization请求头提取并验证Token
     * 
     * @param authHeader 请求头，格式："Bearer eyJhbGci..."
     * @return UserInfo对象
     * @throws RuntimeException Token无效时抛出异常
     */
    public UserInfo parseTokenFromHeader(String authHeader) {
        String token = extractTokenFromHeader(authHeader);
        if (token == null) {
            throw new RuntimeException("Authorization头格式错误，应为Bearer token");
        }
        return parseToken(token);
    }
    
    /**
     * 从Token中获取用户ID
     * 
     * @param token JWT Token
     * @return 用户ID
     */
    public Integer getUserIdFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("userId", Integer.class));
    }
    
    /**
     * 从Token中获取邮箱
     * 
     * @param token JWT Token
     * @return 邮箱
     */
    public String getEmailFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("email", String.class));
    }
    
    /**
     * 从Token中获取手机号
     * 
     * @param token JWT Token
     * @return 手机号
     */
    public String getPhoneFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("phone", String.class));
    }
    
    /**
     * 从Token中获取用户角色
     * 
     * @param token JWT Token
     * @return 用户角色
     */
    public Integer getUserRoleFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("userRole", Integer.class));
    }
    
    /**
     * 从Token中获取昵称
     * 
     * @param token JWT Token
     * @return 昵称
     */
    public String getPetNameFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("petName", String.class));
    }
    
    /**
     * 从Token中获取subject（通常是邮箱或手机号）
     * 
     * @param token JWT Token
     * @return subject
     */
    public String getSubjectFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    
    /**
     * 检查Token是否已过期
     * 
     * @param token JWT Token
     * @return true=已过期，false=未过期
     */
    public boolean isTokenExpired(String token) {
        Date expiration = getExpirationFromToken(token);
        return expiration.before(new Date());
    }
    
    /**
     * 计算Token剩余有效时间（秒）
     * 
     * @param token JWT Token
     * @return 剩余秒数，负数表示已过期
     */
    public long getRemainingTime(String token) {
        Date expiration = getExpirationFromToken(token);
        Date now = new Date();
        return (expiration.getTime() - now.getTime()) / 1000;
    }
    
    /**
     * 从Authorization请求头提取Token
     * 
     * @param authHeader 请求头，格式："Bearer eyJhbGci..."
     * @return Token字符串，如果格式错误返回null
     */
    public String extractTokenFromHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7); // 去掉"Bearer "前缀
    }
    
    /**
     * 验证Token并返回所有信息（Map格式）
     * 
     * @param token JWT Token
     * @return Map包含所有claims信息
     */
    public Map<String, Object> validateAndGetClaims(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            
            Map<String, Object> result = new HashMap<>();
            result.put("userId", claims.get("userId", Integer.class));
            result.put("email", claims.get("email", String.class));
            result.put("phone", claims.get("phone", String.class));
            result.put("petName", claims.get("petName", String.class));
            result.put("userRole", claims.get("userRole", Integer.class));
            result.put("userImg", claims.get("userImg", String.class));
            result.put("carVin", claims.get("carVin", String.class));
            result.put("carType", claims.get("carType", String.class));
            result.put("subject", claims.getSubject());
            result.put("issuedAt", claims.getIssuedAt());
            result.put("expiration", claims.getExpiration());
            result.put("isExpired", claims.getExpiration().before(new Date()));
            
            return result;
            
        } catch (Exception e) {
            throw new RuntimeException("Token验证失败: " + e.getMessage());
        }
    }
    
    // ==================== 私有方法 ====================
    
    /**
     * 获取签名密钥
     */
    private SecretKey getSigningKey() {
        // 确保密钥长度足够（至少32字符）
        if (secret == null || secret.length() < 32) {
            secret = "charging-station-default-secret-key-256-bit-required";
        }
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    
    /**
     * 从Token中提取指定的Claim
     */
    private <T> T getClaimFromToken(String token, java.util.function.Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    
    /**
     * 从Token中获取过期时间
     */
    private Date getExpirationFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    
    /**
     * 从Token中获取所有Claims
     */
    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            // Token过期，但我们仍可以获取claims
            return e.getClaims();
        }
    }
}