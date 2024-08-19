package com.clean.cleanroom.util;

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.util.JwtUtil;
import com.clean.cleanroom.util.TokenServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TokenServiceImplTest {

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getEmailFromRequest_Success() {
        // Given: Authorization 헤더에 유효한 Bearer 토큰이 있는 경우
        String email = "test@example.com";
        String token = "Bearer validToken";
        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtUtil.getEmailFromToken("validToken")).thenReturn(email);

        // When: getEmailFromRequest 메서드를 호출
        String extractedEmail = tokenService.getEmailFromRequest(request);

        // Then: 이메일이 올바르게 추출되었는지 확인
        assertEquals(email, extractedEmail);
        verify(request, times(1)).getHeader("Authorization");
        verify(jwtUtil, times(1)).getEmailFromToken("validToken");
    }

    @Test
    void getEmailFromRequest_MissingAuthorizationHeader_ThrowsException() {
        // Given: Authorization 헤더가 없는 경우
        when(request.getHeader("Authorization")).thenReturn(null);

        // When & Then: getEmailFromRequest 메서드를 호출하면 NullPointerException 발생
        assertThrows(NullPointerException.class, () -> {
            tokenService.getEmailFromRequest(request);
        });
    }
}
