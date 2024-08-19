package com.clean.cleanroom.filter;

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @InjectMocks
    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filter = new JwtAuthenticationFilter(jwtUtil); // 생성자를 통해 모킹된 객체를 주입
    }

    @Test
    void doFilterInternal_SkipFilterForLoginAndSignupPaths() throws ServletException, IOException {
        // Given
        when(request.getRequestURI()).thenReturn("/api/members/login");

        // When
        filter.doFilterInternal(request, response, chain);

        // Then
        verify(chain, times(1)).doFilter(request, response);
        verify(jwtUtil, never()).extractBearerToken(anyString());
    }

    @Test
    void doFilterInternal_SuccessfulTokenValidation() throws ServletException, IOException {
        // Given
        final String token = "Bearer validToken";
        final String email = "test@example.com";

        when(request.getRequestURI()).thenReturn("/api/other");
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(token);
        when(jwtUtil.extractBearerToken(token)).thenReturn("validToken");
        when(jwtUtil.getEmailFromToken("validToken")).thenReturn(email);

        // When
        filter.doFilterInternal(request, response, chain);

        // Then
        verify(request, times(1)).setAttribute("email", email);
        verify(chain, times(1)).doFilter(request, response);
    }

}
