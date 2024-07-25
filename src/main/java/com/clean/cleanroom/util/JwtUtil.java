package com.clean.cleanroom.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private Key key;

    // 객체 생성 후 키 초기화
    @PostConstruct
    public void init() {
        // HMAC-SHA512 알고리즘을 사용하여 비밀 키 생성
        key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    // 토큰 만료 시간 (초 단위, 1시간)
    private final int expiration = 3600; // 1 hour

    // 이메일을 기반으로 JWT 토큰을 생성
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // 토큰 주제 설정
                .setIssuedAt(new Date()) // 토큰 발행 시간 설정
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000)) // 토큰 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS512) // 키와 알고리즘을 사용하여 서명
                .compact(); // 토큰 생성
    }

    // JWT 토큰에서 이메일을 추출
    public String getEmailFromToken(String token) {
        // 토큰을 파싱하여 클레임 추출
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key) // 서명 검증을 위한 키 설정
                .build()
                .parseClaimsJws(token) // 토큰 파싱
                .getBody(); // 클레임 추출
        return claims.getSubject(); // 토큰 주제(이메일) 반환
    }

    // JWT 토큰의 유효성을 검증
    public boolean validateToken(String token) {
        try {
            // 토큰 파싱 및 검증
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true; // 토큰이 유효하면 true 반환
        } catch (Exception e) {
            return false; // 예외 발생 시 토큰이 유효하지 않음
        }
    }
}