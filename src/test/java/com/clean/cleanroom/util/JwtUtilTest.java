//package com.clean.cleanroom.util;
//
//import com.clean.cleanroom.exception.CustomException;
//import com.clean.cleanroom.exception.ErrorMsg;
//import io.jsonwebtoken.security.Keys;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockedStatic;
//
//import java.security.Key;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.mockStatic;
//
//class JwtUtilTest {
//
//    private final Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);
//
//    @BeforeEach
//    void setUp() {
//        // 키 설정 등 필요한 초기 설정을 수행합니다.
//    }
//
//    @Test
//    void generateAccessToken_Success() {
//        // Given
//        String email = "test@example.com";
//
//        // When
//        String token = JwtUtil.generateAccessToken(email);
//
//        // Then
//        assertNotNull(token);
//        assertTrue(JwtUtil.validateToken(token));
//    }
//
//    @Test
//    void generateRefreshToken_Success() {
//        // Given
//        String email = "test@example.com";
//
//        // When
//        String token = JwtUtil.generateRefreshToken(email);
//
//        // Then
//        assertNotNull(token);
//        assertTrue(JwtUtil.validateToken(token));
//    }
//
//    @Test
//    void extractBearerToken_Success() {
//        // Given
//        String header = "Bearer validToken";
//
//        // When
//        String token = JwtUtil.extractBearerToken(header);
//
//        // Then
//        assertEquals("validToken", token);
//    }
//
//    @Test
//    void extractBearerToken_MissingHeader() {
//        // Given
//        String header = null;
//
//        // When & Then
//        CustomException exception = assertThrows(CustomException.class, () -> JwtUtil.extractBearerToken(header));
//        assertEquals(ErrorMsg.MISSING_AUTHORIZATION_HEADER, exception.getErrorMsg());
//    }
//
//    @Test
//    void extractBearerToken_InvalidHeader() {
//        // Given
//        String header = "InvalidToken";
//
//        // When & Then
//        CustomException exception = assertThrows(CustomException.class, () -> JwtUtil.extractBearerToken(header));
//        assertEquals(ErrorMsg.MISSING_AUTHORIZATION_HEADER, exception.getErrorMsg());
//    }
//
//    @Test
//    void getEmailFromToken_Success() {
//        // Given
//        String email = "test@example.com";
//        String token = JwtUtil.generateAccessToken(email);
//
//        // When
//        try (MockedStatic<JwtUtil> mockedJwtUtil = mockStatic(JwtUtil.class)) {
//            mockedJwtUtil.when(() -> JwtUtil.getEmailFromToken(token)).thenReturn(email);
//
//            // Then
//            String extractedEmail = JwtUtil.getEmailFromToken(token);
//            assertEquals(email, extractedEmail);
//        }
//    }
//
//    @Test
//    void getEmailFromToken_InvalidToken() {
//        // Given
//        String invalidToken = "invalidToken";
//
//        // When & Then
//        try (MockedStatic<JwtUtil> mockedJwtUtil = mockStatic(JwtUtil.class)) {
//            mockedJwtUtil.when(() -> JwtUtil.getEmailFromToken(invalidToken))
//                    .thenThrow(new CustomException(ErrorMsg.INVALID_TOKEN));
//
//            CustomException exception = assertThrows(CustomException.class, () -> JwtUtil.getEmailFromToken(invalidToken));
//            assertEquals(ErrorMsg.INVALID_TOKEN, exception.getErrorMsg());
//        }
//    }
//
//    @Test
//    void extractEmail_Success() {
//        // Given
//        String email = "test@example.com";
//        String token = "Bearer " + JwtUtil.generateAccessToken(email);
//
//        // When
//        try (MockedStatic<JwtUtil> mockedJwtUtil = mockStatic(JwtUtil.class)) {
//            mockedJwtUtil.when(() -> JwtUtil.extractEmail(token)).thenReturn(email);
//
//            // Then
//            String extractedEmail = JwtUtil.extractEmail(token);
//            assertEquals(email, extractedEmail);
//        }
//    }
//
//    @Test
//    void validateToken_Success() {
//        // Given
//        String token = JwtUtil.generateAccessToken("test@example.com");
//
//        // When
//        boolean isValid = JwtUtil.validateToken(token);
//
//        // Then
//        assertTrue(isValid);
//    }
//
//    @Test
//    void validateToken_Invalid() {
//        // Given
//        String invalidToken = "invalidToken";
//
//        // When
//        try (MockedStatic<JwtUtil> mockedJwtUtil = mockStatic(JwtUtil.class)) {
//            mockedJwtUtil.when(() -> JwtUtil.validateToken(invalidToken)).thenReturn(false);
//
//            // Then
//            boolean isValid = JwtUtil.validateToken(invalidToken);
//            assertFalse(isValid);
//        }
//    }
//}
