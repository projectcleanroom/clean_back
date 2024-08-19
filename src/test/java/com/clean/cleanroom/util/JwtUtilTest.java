package com.clean.cleanroom.util;

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.jwt.entity.RefreshToken;
import com.clean.cleanroom.jwt.repository.RefreshTokenRepository;
import com.clean.cleanroom.members.repository.MembersRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.Key;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    @Mock
    private MembersRepository membersRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    private Key key;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtUtil.init();
    }

    @Test
    void generateAccessToken_Success() {
        // Given
        String email = "test@example.com";

        // When
        String token = jwtUtil.generateAccessToken(email);

        // Then
        assertNotNull(token);
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void generateRefreshToken_Success() {
        // Given
        String email = "test@example.com";

        // When
        String token = jwtUtil.generateRefreshToken(email);

        // Then
        assertNotNull(token);
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void extractBearerToken_Success() {
        // Given: 올바른 Bearer 토큰이 주어진 경우
        String header = "Bearer validToken";

        // When: extractBearerToken 메서드 호출
        String token = jwtUtil.extractBearerToken(header);

        // Then: Bearer 접두사가 제거된 토큰이 반환되는지 확인
        assertEquals("validToken", token);
    }

    @Test
    void extractBearerToken_MissingHeader_ThrowsException() {
        // Given: Authorization 헤더가 없는 경우
        String header = null;

        // When & Then: 예외가 발생하는지 확인
        CustomException exception = assertThrows(CustomException.class, () -> jwtUtil.extractBearerToken(header));
        assertEquals(ErrorMsg.MISSING_AUTHORIZATION_HEADER.getCode(), exception.getCode());
    }

    @Test
    void extractBearerToken_InvalidHeader_ThrowsException() {
        // Given: Bearer로 시작하지 않는 헤더
        String header = "InvalidScheme token";

        // When & Then: 예외가 발생하는지 확인
        CustomException exception = assertThrows(CustomException.class, () -> jwtUtil.extractBearerToken(header));
        assertEquals(ErrorMsg.MISSING_AUTHORIZATION_HEADER.getCode(), exception.getCode());
    }

    @Test
    void parseToken_Success() {
        // Given
        String email = "test@example.com";
        String token = jwtUtil.generateAccessToken(email);

        // When
        Claims claims = jwtUtil.parseToken(token);

        // Then
        assertEquals(email, claims.getSubject());
    }

    @Test
    void parseToken_InvalidToken_ThrowsException() {
        // Given: 유효하지 않은 토큰
        String invalidToken = "invalidToken";

        // When & Then: 예외가 발생하는지 확인
        CustomException exception = assertThrows(CustomException.class, () -> jwtUtil.parseToken(invalidToken));
        assertEquals(ErrorMsg.INVALID_TOKEN.getCode(), exception.getCode());
    }

    @Test
    void getEmailFromToken_Success() {
        // Given
        String email = "test@example.com";
        String token = jwtUtil.generateAccessToken(email);

        // When
        String extractedEmail = jwtUtil.getEmailFromToken(token);

        // Then
        assertEquals(email, extractedEmail);
    }

    @Test
    void getEmailFromToken_InvalidToken_ThrowsException() {
        // Given: 유효하지 않은 토큰
        String invalidToken = "invalidToken";

        // When & Then: 예외가 발생하는지 확인
        CustomException exception = assertThrows(CustomException.class, () -> jwtUtil.getEmailFromToken(invalidToken));
        assertEquals(ErrorMsg.INVALID_TOKEN.getCode(), exception.getCode());
    }

    @Test
    void extractEmail_WithBearerPrefix_Success() {
        // Given: Bearer 접두사가 포함된 유효한 토큰
        String email = "test@example.com";
        String token = "Bearer " + jwtUtil.generateAccessToken(email);

        // When: extractEmail 메서드를 호출
        String extractedEmail = jwtUtil.extractEmail(token);

        // Then: 이메일이 올바르게 추출되었는지 확인
        assertEquals(email, extractedEmail);
    }

    @Test
    void extractEmail_WithoutBearerPrefix_Success() {
        // Given: Bearer 접두사가 없는 유효한 토큰
        String email = "test@example.com";
        String token = jwtUtil.generateAccessToken(email);

        // When: extractEmail 메서드를 호출 (Bearer가 없는 경우)
        String extractedEmail = jwtUtil.extractEmail(token);

        // Then: 이메일이 올바르게 추출되었는지 확인
        assertEquals(email, extractedEmail);
    }

    @Test
    void extractEmail_InvalidToken_ThrowsException() {
        // Given: 유효하지 않은 토큰
        String invalidToken = "invalidToken";

        // When & Then: 예외가 발생하는지 확인
        assertThrows(MalformedJwtException.class, () -> jwtUtil.extractEmail(invalidToken));
    }

    @Test
    void validateToken_ValidToken_ReturnsTrue() {
        // Given
        String token = jwtUtil.generateAccessToken("test@example.com");

        // When
        boolean isValid = jwtUtil.validateToken(token);

        // Then
        assertTrue(isValid);
    }

    @Test
    void validateToken_InvalidToken_ReturnsFalse() {
        // Given: 유효하지 않은 토큰
        String invalidToken = "invalidToken";

        // When
        boolean isValid = jwtUtil.validateToken(invalidToken);

        // Then
        assertFalse(isValid);
    }

    @Test
    void revokeToken_ValidToken_Success() {
        // Given
        String token = "validRefreshToken";
        RefreshToken refreshToken = mock(RefreshToken.class);
        when(refreshTokenRepository.findByToken(anyString())).thenReturn(Optional.of(refreshToken));

        // When
        jwtUtil.revokeToken(token);

        // Then
        verify(refreshToken).expire();
        verify(refreshToken).revoke();
        verify(refreshTokenRepository).save(refreshToken);
    }

    @Test
    void revokeToken_InvalidToken_DoesNothing() {
        // Given
        String invalidToken = "invalidToken";
        when(refreshTokenRepository.findByToken(anyString())).thenReturn(Optional.empty());

        // When
        jwtUtil.revokeToken(invalidToken);

        // Then
        verify(refreshTokenRepository, never()).save(any());
    }
}
