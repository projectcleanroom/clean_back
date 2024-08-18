package com.clean.cleanroom.util;

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.jwt.dto.TokenResponseDto;
import com.clean.cleanroom.jwt.entity.RefreshToken;
import com.clean.cleanroom.jwt.repository.RefreshTokenRepository;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    // JWT 서명을 위한 키
    private Key key;
    private final MembersRepository membersRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    // 객체 초기화 후 키 생성
    @PostConstruct
    public void init() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    // 액세스 토큰 만료 시간 (1시간)
    private final int accessTokenExpiration = 3600;
    // 리프레시 토큰 만료 시간 (2주)
    private final int refreshTokenExpiration = 1209600;

    // 이메일을 사용해 액세스 토큰을 생성
    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // 이메일을 사용해 리프레시 토큰을 생성
    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // Authorization 헤더에서 Bearer 토큰을 추출합니다.
    // Bearer 토큰이 없거나 올바르지 않으면 CustomException을 던집니다.
    public String extractBearerToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new CustomException(ErrorMsg.MISSING_AUTHORIZATION_HEADER);
        }
        return header.substring(7);
    }

    // JWT 토큰을 파싱하여 Claims 객체를 반환합니다.
    // 이 메서드는 토큰의 유효성을 확인하지 않습니다.
    public Claims parseToken(String token) {
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

    public String getEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            // 서명 예외 처리
            log.error("Exception occurred while parsing token: {}", e.getMessage());
            throw new CustomException(ErrorMsg.INVALID_TOKEN);
        }
    }

    // 이메일을 추출하는 메서드, 유효성 검증은 이미 필터에서 진행한 것으로 가정합니다.
    // Bearer 토큰에서 이메일을 추출하는 간단한 메서드로, 유효성 검증은 하지 않습니다.
    public String extractEmail(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // 이메일을 반환
    }


    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 개별 토큰을 무효화하는 메서드
    public void revokeToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElse(null);
        if (refreshToken != null) {
            refreshToken.expire();
            refreshToken.revoke();
            refreshTokenRepository.save(refreshToken);
        }
    }
}
