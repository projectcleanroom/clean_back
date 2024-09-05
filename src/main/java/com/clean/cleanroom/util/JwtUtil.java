package com.clean.cleanroom.util;

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;

@Slf4j
public class JwtUtil {

    // JWT 서명을 위한 키 (static으로 변경)
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // 액세스 토큰 만료 시간 (1시간)
    private static final int accessTokenExpiration = 3600;
    // 리프레시 토큰 만료 시간 (2주)
    private static final int refreshTokenExpiration = 1209600;

    // 이메일을 사용해 액세스 토큰을 생성 (static 메서드)
    public static String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // 이메일을 사용해 리프레시 토큰을 생성 (static 메서드)
    public static String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // Authorization 헤더에서 Bearer 토큰을 추출하는 메서드 (static 메서드)
    public static String extractBearerToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new CustomException(ErrorMsg.MISSING_AUTHORIZATION_HEADER);
        }
        return header.substring(7);
    }

    // JWT 토큰을 파싱하여 Claims 객체를 반환 (static 메서드)
    public static Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("Exception occurred while parsing token: {}", e.getMessage());
            throw new CustomException(ErrorMsg.INVALID_TOKEN);
        }
    }

    // 토큰에서 이메일을 추출하는 메서드 (static 메서드)
    public static String getEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            log.error("Exception occurred while parsing token: {}", e.getMessage());
            throw new CustomException(ErrorMsg.INVALID_TOKEN);
        }
    }

    // Bearer 토큰에서 이메일을 추출하는 간단한 메서드 (static 메서드)
    public static String extractEmail(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // 이메일 반환
    }

    // 토큰 유효성 검증 (static 메서드)
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
