//package com.clean.cleanroom.util;
//
//import com.clean.cleanroom.exception.CustomException;
//import com.clean.cleanroom.exception.ErrorMsg;
//import jakarta.servlet.http.HttpServletRequest;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockedStatic;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//class TokenServiceImplTest {
//
//    @Mock
//    private HttpServletRequest request;
//
//    @InjectMocks
//    private TokenServiceImpl tokenServiceImpl;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void getEmailFromRequest_Success() {
//        // Given
//        String token = "Bearer sampleToken";
//        String email = "test@example.com";
//        when(request.getHeader("Authorization")).thenReturn(token);
//
//        // JwtUtil 클래스의 정적 메서드를 모킹 - try-with-resources 사용
//        try (MockedStatic<JwtUtil> mockedJwtUtil = mockStatic(JwtUtil.class)) {
//            mockedJwtUtil.when(() -> JwtUtil.getEmailFromToken("sampleToken")).thenReturn(email);
//
//            // When
//            String result = tokenServiceImpl.getEmailFromRequest(request);
//
//            // Then
//            assertEquals(email, result);
//            verify(request, times(1)).getHeader("Authorization");
//        }
//    }
//
//    @Test
//    void getEmailFromRequest_NoAuthorizationHeader() {
//        // Given
//        when(request.getHeader("Authorization")).thenReturn(null);
//
//        // When & Then
//        assertThrows(NullPointerException.class, () -> {
//            tokenServiceImpl.getEmailFromRequest(request);
//        });
//        verify(request, times(1)).getHeader("Authorization");
//    }
//
//    @Test
//    void getEmailFromRequest_InvalidToken() {
//        // Given
//        String invalidToken = "Bearer ";
//        when(request.getHeader("Authorization")).thenReturn(invalidToken);
//
//        // JwtUtil 클래스의 정적 메서드를 모킹 - try-with-resources 사용
//        try (MockedStatic<JwtUtil> mockedJwtUtil = mockStatic(JwtUtil.class)) {
//            // JwtUtil에서 빈 토큰을 처리할 때 CustomException을 던지도록 설정
//            mockedJwtUtil.when(() -> JwtUtil.getEmailFromToken("")).thenThrow(new CustomException(ErrorMsg.INVALID_TOKEN));
//
//            // When & Then
//            CustomException exception = assertThrows(CustomException.class, () -> {
//                tokenServiceImpl.getEmailFromRequest(request);
//            });
//
//            // 발생한 예외가 CustomException이고, 올바른 ErrorMsg를 가지고 있는지 확인
//            assertEquals(ErrorMsg.INVALID_TOKEN, exception.getErrorMsg());
//
//            verify(request, times(1)).getHeader("Authorization");
//        }
//    }
//
//}
