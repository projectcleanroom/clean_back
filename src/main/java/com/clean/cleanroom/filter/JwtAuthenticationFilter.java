package com.clean.cleanroom.filter;

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // 특정 경로에 대해서는 필터를 적용하지 않습니다.
        if ("/api/members/login".equals(path) ||
                "/api/members/signup".equals(path) ||
                "/api/members/kakao-login".equals(path)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            // Authorization 헤더에서 JWT 토큰을 추출하고 이메일을 얻어옵니다.
            String token = jwtUtil.extractBearerToken(request.getHeader(HttpHeaders.AUTHORIZATION));
            String email = jwtUtil.getEmailFromToken(token);

            // 이메일을 요청에 추가하고, 다음 필터나 서블릿으로 요청을 전달합니다.
            request.setAttribute("email", email);
            chain.doFilter(request, response);

        } catch (CustomException e) {
            // 만약 액세스 토큰이 유효하지 않은 경우, 리프레시 토큰을 확인합니다.
            String refreshToken = request.getHeader("Refresh-Token");
            if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
                try {
                    refreshAccessToken(request, response, refreshToken);
                    return; // 리프레시 토큰이 유효하여 새 토큰을 발급한 경우, 요청을 종료합니다.
                } catch (CustomException ex) {
                    handleInvalidToken(request, response, ex); // 리프레시 토큰도 유효하지 않은 경우 처리
                }
            } else {
                handleInvalidToken(request, response, e); // 리프레시 토큰이 없거나 올바르지 않은 경우 처리
            }
        }
    }

    // 리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급합니다.
    private void refreshAccessToken(HttpServletRequest request, HttpServletResponse response, String refreshToken) throws IOException {
        try {
            // 리프레시 토큰에서 이메일을 추출하고, 새로운 액세스 및 리프레시 토큰을 생성합니다.
            String email = jwtUtil.getEmailFromToken(refreshToken);
            String newAccessToken = jwtUtil.generateAccessToken(email);
            String newRefreshToken = jwtUtil.generateRefreshToken(email);

            // 새로 발급된 토큰을 응답 헤더와 JSON 본문에 포함시켜 클라이언트에 전달합니다.
            response.setHeader("Authorization", "Bearer " + newAccessToken);
            response.setHeader("Refresh-Token", "Bearer " + newRefreshToken);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.format("{\"accessToken\": \"%s\", \"refreshToken\": \"%s\"}", newAccessToken, newRefreshToken));
        } catch (CustomException e) {
            // 리프레시 토큰이 유효하지 않은 경우, 예외를 그대로 처리하여 상위 메서드에서 처리되도록 합니다.
            throw e;
        }
    }

    // 유효하지 않은 토큰을 처리하는 메서드
    private void handleInvalidToken(HttpServletRequest request, HttpServletResponse response, CustomException e) throws IOException {
        // 로그 기록
        log.error("잘못된 토큰 처리 중. 오류 코드: {}", e.getErrorMsg().getCode());

        // 응답 메시지를 설정합니다.
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 코드만 응답으로 반환
        response.getWriter().write(String.format("{\"code\": %d}", e.getErrorMsg().getCode()));
    }
}
