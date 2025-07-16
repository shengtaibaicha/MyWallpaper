package com.baicha.mywallpaper.tool;

import com.baicha.mywallpaper.entity.Users;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTool {

    // 密钥，用于签名JWT
    @Value(value = "${jwt.secret-key}")
    private String secretKey;

    // 过期时间
    @Value(value = "${jwt.time-out}")
    private int timeOut;

    public String generateToken(Users users) {
        // 设置JWT的过期时间（例如：24小时后过期）
        long expirationTimeMillis = System.currentTimeMillis() + 1000L * 60 * 60 * timeOut;
        Date expirationDate = new Date(expirationTimeMillis);

        // 可以添加自定义的claims（例如用户角色、ID等）
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", users.getUserId());

        // 构建JWT
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(users.getUserName()) // 设置主题（通常是用户名或用户ID）
                .setIssuedAt(new Date(System.currentTimeMillis())) // 设置签发时间
                .setExpiration(expirationDate) // 设置过期时间
                .signWith(SignatureAlgorithm.HS256, secretKey) // 使用HS256算法和密钥签名
                .compact(); // 生成最终的JWT字符串
    }

    public String validateToken(String token) {
        try {
            // 解析并验证 JWT
            Jws<Claims> jws = Jwts.parser()
                    .setSigningKey(secretKey) // 签名密钥
                    .build()
                    .parseClaimsJws(token); // 解析 JWT 字符串
            // 解析token后将userId返回
            return jws.getBody().get("userId", String.class);
        } catch (ExpiredJwtException e) {
            return "Token has expired.";
        } catch (SignatureException e) {
            return "Invalid signature.";
        } catch (Exception e) {
            return "Invalid token: " + e.getMessage();
        }
    }
}
