package com.clean.cleanroom.filter;

import com.clean.cleanroom.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 요청 헤더에서 "Authorization" 헤더 값을 가져옴
        String header = request.getHeader("Authorization");
        String token = null;
        String email = null;

        // 헤더가 존재하고 "Bearer "로 시작하는지 확인
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            email = jwtUtil.getEmailFromToken(token);
        }

        // 이메일이 존재하고, 토큰이 유효한지 확인
        if (email != null && jwtUtil.validateToken(token)) {
            // 토큰이 유효한 경우, 요청에 이메일 속성을 설정
            request.setAttribute("email", email);
        } else if (request.getRequestURI().equals("/api/members/logout")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"message\": \"로그인 후 가능합니다.\"}");
            return;
        }

        // 필터 체인을 따라 다음 필터를 호출
        chain.doFilter(request, response);
    }
}