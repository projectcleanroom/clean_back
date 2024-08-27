//package com.clean.cleanroom.filter;
//
//import com.clean.cleanroom.exception.CustomException;
//import com.clean.cleanroom.exception.ErrorMsg;
//import com.clean.cleanroom.util.JwtUtil;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpHeaders;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class JwtAuthenticationFilterTest {
//
//    @Mock
//    private JwtUtil jwtUtil;
//
//    @Mock
//    private HttpServletRequest request;
//
//    @Mock
//    private HttpServletResponse response;
//
//    @Mock
//    private FilterChain chain;
//
//    @InjectMocks
//    private JwtAuthenticationFilter filter;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        filter = new JwtAuthenticationFilter(jwtUtil); // 생성자를 통해 모킹된 객체를 주입
//    }
//
//    @Test
//    void doFilterInternal_SkipFilterForLoginAndSignupPaths() throws ServletException, IOException {
//        // Given
//        when(request.getRequestURI()).thenReturn("/api/members/login");
//
//        // When
//        filter.doFilterInternal(request, response, chain);
//
//        // Then
//        verify(chain, times(1)).doFilter(request, response);
//        verify(jwtUtil, never()).extractBearerToken(anyString());
//    }
//
//    @Test
//    void doFilterInternal_SuccessfulTokenValidation() throws ServletException, IOException {
//        // Given
//        final String token = "Bearer validToken";
//        final String email = "test@example.com";
//
//        when(request.getRequestURI()).thenReturn("/api/other");
//        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(token);
//        when(jwtUtil.extractBearerToken(token)).thenReturn("validToken");
//        when(jwtUtil.getEmailFromToken("validToken")).thenReturn(email);
//
//        // When
//        filter.doFilterInternal(request, response, chain);
//
//        // Then
//        verify(request, times(1)).setAttribute("email", email);
//        verify(chain, times(1)).doFilter(request, response);
//    }
//
//}

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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
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
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doFilterInternal_ValidToken_Success() throws ServletException, IOException {
        // Given
        String token = "Bearer validToken";
        String email = "test@example.com";
        when(request.getRequestURI()).thenReturn("/api/protected");
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(token);
        when(jwtUtil.extractBearerToken(anyString())).thenReturn("validToken");
        when(jwtUtil.getEmailFromToken("validToken")).thenReturn(email);

        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, chain);

        // Then
        verify(request, times(1)).setAttribute("email", email);
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_InvalidToken_RefreshTokenValid() throws ServletException, IOException {
        // Given
        String invalidToken = "Bearer invalidToken";
        String refreshToken = "Bearer validRefreshToken";
        String email = "test@example.com";
        String newAccessToken = "newAccessToken";
        String newRefreshToken = "newRefreshToken";

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        // 모킹된 HttpServletRequest의 동작 설정
        when(request.getRequestURI()).thenReturn("/api/protected");
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(invalidToken);
        when(jwtUtil.extractBearerToken(invalidToken)).thenReturn("invalidToken");
        when(jwtUtil.getEmailFromToken("invalidToken")).thenThrow(new CustomException(ErrorMsg.INVALID_TOKEN));
        when(request.getHeader("Refresh-Token")).thenReturn(refreshToken);
        when(jwtUtil.getEmailFromToken(refreshToken)).thenReturn(email);
        when(jwtUtil.generateAccessToken(email)).thenReturn(newAccessToken);
        when(jwtUtil.generateRefreshToken(email)).thenReturn(newRefreshToken);
        when(response.getWriter()).thenReturn(printWriter);

        // When: 필터를 실행
        jwtAuthenticationFilter.doFilterInternal(request, response, chain);

        // Then: 헤더 설정 및 작성된 내용 검증
        verify(response).setHeader(eq("Authorization"), eq("Bearer " + newAccessToken));
        verify(response).setHeader(eq("Refresh-Token"), eq("Bearer " + newRefreshToken));
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        // PrintWriter에 작성된 내용 검증
        String expectedResponse = String.format("{\"accessToken\": \"%s\", \"refreshToken\": \"%s\"}", newAccessToken, newRefreshToken);
        assertEquals(expectedResponse, stringWriter.toString().trim());
    }


    @Test
    void doFilterInternal_InvalidToken_RefreshTokenInvalid() throws ServletException, IOException {
        // Given
        String invalidToken = "Bearer invalidToken";
        String invalidRefreshToken = "Bearer invalidRefreshToken";

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        // 모킹된 HttpServletRequest와 HttpServletResponse의 동작 설정
        when(request.getRequestURI()).thenReturn("/api/protected");
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(invalidToken);
        when(jwtUtil.extractBearerToken(invalidToken)).thenReturn("invalidToken");
        when(jwtUtil.getEmailFromToken("invalidToken")).thenThrow(new CustomException(ErrorMsg.INVALID_TOKEN));
        when(request.getHeader("Refresh-Token")).thenReturn(invalidRefreshToken);
        when(jwtUtil.getEmailFromToken(invalidRefreshToken)).thenThrow(new CustomException(ErrorMsg.INVALID_TOKEN));
        when(response.getWriter()).thenReturn(printWriter);

        // When: 필터를 실행
        jwtAuthenticationFilter.doFilterInternal(request, response, chain);

        // Then: 응답 검증
        verify(response, times(1)).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(response, times(1)).setContentType("application/json");
        verify(response, times(1)).setCharacterEncoding("UTF-8");

        // PrintWriter에 작성된 내용 검증
        String expectedResponse = String.format("{\"code\": %d}", ErrorMsg.INVALID_TOKEN.getCode());
        assertEquals(expectedResponse, stringWriter.toString().trim());
    }


    @Test
    void doFilterInternal_NoTokenProvided() throws ServletException, IOException {
        // Given
        when(request.getRequestURI()).thenReturn("/api/protected");
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, chain);

        // Then
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_ExcludePaths() throws ServletException, IOException {
        // Given
        when(request.getRequestURI()).thenReturn("/api/members/login");

        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, chain);

        // Then
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_InvalidAuthorizationHeaderFormat() throws ServletException, IOException {
        // Given
        PrintWriter printWriter = mock(PrintWriter.class);

        when(request.getRequestURI()).thenReturn("/api/protected");
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("InvalidTokenFormat");
        when(response.getWriter()).thenReturn(printWriter);

        // Make sure the extractBearerToken method throws a CustomException
        doThrow(new CustomException(ErrorMsg.INVALID_TOKEN)).when(jwtUtil).extractBearerToken("InvalidTokenFormat");

        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, chain);

        // Then
        verify(response, times(1)).setContentType("application/json");
        verify(response, times(1)).setCharacterEncoding("UTF-8");
        verify(printWriter, times(1)).write(String.format("{\"code\": %d}", ErrorMsg.INVALID_TOKEN.getCode()));
        verify(response, times(1)).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }


    @Test
    void doFilterInternal_RefreshTokenWithoutBearerPrefix() throws ServletException, IOException {
        // Given
        String invalidToken = "Bearer invalidToken";
        String refreshToken = "validRefreshToken"; // No "Bearer" prefix
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(request.getRequestURI()).thenReturn("/api/protected");
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(invalidToken);
        when(jwtUtil.extractBearerToken(invalidToken)).thenReturn("invalidToken");
        when(jwtUtil.getEmailFromToken("invalidToken")).thenThrow(new CustomException(ErrorMsg.INVALID_TOKEN));
        when(request.getHeader("Refresh-Token")).thenReturn(refreshToken); // Invalid format
        when(response.getWriter()).thenReturn(printWriter);

        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, chain);

        // Then
        verify(response, times(1)).setContentType("application/json");
        verify(response, times(1)).setCharacterEncoding("UTF-8");
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        assertEquals(String.format("{\"code\": %d}", ErrorMsg.INVALID_TOKEN.getCode()), stringWriter.toString().trim());
    }


    @Test
    void doFilterInternal_EmptyAuthorizationHeader() throws ServletException, IOException {
        // Given
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(request.getRequestURI()).thenReturn("/api/protected");
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);
        when(response.getWriter()).thenReturn(printWriter);

        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, chain);

        // Then
        verify(chain, times(1)).doFilter(request, response);
        verify(response, never()).getWriter(); // response writer should not be used
    }



}

