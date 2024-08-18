package com.clean.cleanroom.util;

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.jwt.entity.RefreshToken;
import com.clean.cleanroom.jwt.repository.RefreshTokenRepository;
import com.clean.cleanroom.members.repository.MembersRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

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
        // Given
        String invalidToken = "invalidToken";

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> jwtUtil.getEmailFromToken(invalidToken));
        assertEquals(ErrorMsg.INVALID_TOKEN.getCode(), exception.getCode());
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
        // Given
        String invalidToken = "invalidToken";

        // When
        boolean isValid = jwtUtil.validateToken(invalidToken);

        // Then
        assertFalse(isValid);
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

        // When & Then: extractEmail 메서드를 호출했을 때 CustomException이 발생하는지 확인
        CustomException exception = assertThrows(CustomException.class, () -> jwtUtil.extractEmail(invalidToken));
        assertEquals(ErrorMsg.INVALID_TOKEN.getCode(), exception.getCode());
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

//    @Test
//    void refreshToken_Success() throws Exception {
//        // Given
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        String refreshToken = jwtUtil.generateRefreshToken("test@example.com");
//        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + refreshToken);
//        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(mock(Members.class)));
//
//        // When
//        jwtUtil.refreshToken(request, response);
//
//        // Then
//        verify(response).setHeader(eq("Authorization"), anyString());
//    }

    @Test
    void refreshToken_MissingAuthorizationHeader_UnauthorizedResponse() throws Exception {
        // Given: Authorization 헤더가 없을 경우
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        // When: refreshToken 메서드를 호출
        jwtUtil.refreshToken(request, response);

        // Then: Unauthorized 상태 코드가 설정되었는지 확인
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response, never()).setHeader(anyString(), anyString());
    }


    @Test
    void refreshToken_InvalidToken_UnauthorizedResponse() throws Exception {
        // Given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        String invalidToken = "invalidToken";
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + invalidToken);

        // When & Then: refreshToken 메서드를 호출했을 때 CustomException이 발생하는지 확인
        CustomException exception = assertThrows(CustomException.class, () -> {
            jwtUtil.refreshToken(request, response);
        });

        // CustomException의 메시지나 코드가 예상대로 반환되는지 확인
        assertEquals(ErrorMsg.INVALID_TOKEN.getCode(), exception.getCode());
    }

    @Test
    void refreshToken_InvalidBearerScheme_UnauthorizedResponse() throws Exception {
        // Given: Authorization 헤더가 Bearer로 시작하지 않는 경우
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("InvalidScheme refreshToken");

        // When: refreshToken 메서드를 호출
        jwtUtil.refreshToken(request, response);

        // Then: Unauthorized 상태 코드가 설정되었는지 확인
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response, never()).setHeader(anyString(), anyString());
    }

    @Test
    void refreshToken_NonExistentUser_UnauthorizedResponse() throws Exception {
        // Given: 토큰에 해당하는 사용자가 존재하지 않을 경우
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        String refreshToken = jwtUtil.generateRefreshToken("test@example.com");
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + refreshToken);
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // When: refreshToken 메서드를 호출
        jwtUtil.refreshToken(request, response);

        // Then: Unauthorized 상태 코드가 설정되었는지 확인
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response, never()).setHeader(anyString(), anyString());
    }

    @Test
    void refreshToken_ValidToken_UserNotFound_UnauthorizedResponse() throws Exception {
        // Given: 유효한 토큰이지만 사용자가 없는 경우
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        String refreshToken = jwtUtil.generateRefreshToken("test@example.com");
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + refreshToken);
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // When: refreshToken 메서드를 호출
        jwtUtil.refreshToken(request, response);

        // Then: Unauthorized 상태 코드가 설정되었는지 확인
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response, never()).setHeader(anyString(), anyString());
    }
}

