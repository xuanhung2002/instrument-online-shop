package com.shop.config;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME_MS = 3600000; // 1 hour

    public static String generateToken(String subject) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME_MS);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SECRET_KEY)
                .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
	// Lấy thông tin user từ jwt
//    public static Long getUserIdFromJWT(String token) {
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(SECRET_KEY)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//        return claims.get("userId", Long.class); // Lấy giá trị từ claim userId
//    }
}
