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
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockedStatic;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpHeaders;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class JwtAuthenticationFilterTest {
//
//    @Mock
//    private HttpServletRequest request;
//
//    @Mock
//    private HttpServletResponse response;
//
//    @Mock
//    private FilterChain filterChain;
//
//    @InjectMocks
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void doFilterInternal_SkipFilter() throws ServletException, IOException {
//        // Given
//        when(request.getRequestURI()).thenReturn("/api/members/login");
//
//        // When
//        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
//
//        // Then
//        verify(filterChain, times(1)).doFilter(request, response);
//    }
//
//    @Test
//    void doFilterInternal_ExtractEmailFromToken() throws ServletException, IOException {
//        // Given
//        when(request.getRequestURI()).thenReturn("/api/other");
//        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer validToken");
//
//        // JwtUtil 클래스의 정적 메서드를 모킹 - try-with-resources 사용
//        try (MockedStatic<JwtUtil> mockedJwtUtil = mockStatic(JwtUtil.class)) {
//            mockedJwtUtil.when(() -> JwtUtil.extractBearerToken("Bearer validToken")).thenReturn("validToken");
//            mockedJwtUtil.when(() -> JwtUtil.getEmailFromToken("validToken")).thenReturn("test@example.com");
//
//            // When
//            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
//
//            // Then
//            verify(request, times(1)).setAttribute("email", "test@example.com");
//            verify(filterChain, times(1)).doFilter(request, response);
//        }
//    }
//
//    @Test
//    void doFilterInternal_InvalidToken() throws ServletException, IOException {
//        // Given
//        when(request.getRequestURI()).thenReturn("/api/other");
//        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer invalidToken");
//
//        // JwtUtil 클래스의 정적 메서드를 모킹 - try-with-resources 사용
//        try (MockedStatic<JwtUtil> mockedJwtUtil = mockStatic(JwtUtil.class)) {
//            mockedJwtUtil.when(() -> JwtUtil.extractBearerToken("Bearer invalidToken")).thenReturn("invalidToken");
//            mockedJwtUtil.when(() -> JwtUtil.getEmailFromToken("invalidToken"))
//                    .thenThrow(new CustomException(ErrorMsg.INVALID_TOKEN));
//
//            // NullPointerException 방지를 위해 응답 writer 모킹
//            PrintWriter printWriter = mock(PrintWriter.class);
//            when(response.getWriter()).thenReturn(printWriter);
//
//            // When
//            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
//
//            // Then
//            // 필터가 CustomException을 던지지 않는 경우 응답 상태와 로그 메시지를 검증
//            verify(response, times(1)).setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            verify(printWriter, times(1)).write(anyString()); // 로그 메시지 작성 검증
//        }
//    }
//
//}
