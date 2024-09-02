package com.clean.cleanroom.members.service;

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.jwt.service.TokenService;
import com.clean.cleanroom.members.dto.MembersEmailAndPasswordDto;
import com.clean.cleanroom.members.dto.MembersLoginRequestDto;
import com.clean.cleanroom.members.dto.MembersLoginResponseDto;
import com.clean.cleanroom.members.dto.MembersLogoutResponseDto;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MembersLoginService {

    private final MembersRepository membersRepository;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;


    // 비밀번호 검증 로직
    public void checkPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new CustomException(ErrorMsg.INVALID_PASSWORD);
        }
    }

    // 일반 로그인 로직
    public ResponseEntity<MembersLoginResponseDto> login(MembersLoginRequestDto requestDto) {
        // 이메일로 회원을 조회
        MembersEmailAndPasswordDto memberEmailDto = getMemberByEmail(requestDto.getEmail());

        // 비밀번호 검증
        checkPassword(requestDto.getPassword(), memberEmailDto.getPassword());

        // 공통 로직 호출 (JWT 토큰 생성 및 응답 반환)
        return generateLoginResponse(memberEmailDto);
    }

    // 공통 로직: 이메일로 회원을 조회하고 없으면 예외를 던짐
    private MembersEmailAndPasswordDto getMemberByEmail(String email) {
        return membersRepository.findEmailByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorMsg.INVALID_ID));
    }

    // Login JWT 토큰 생성 및 응답 반환
    private ResponseEntity<MembersLoginResponseDto> generateLoginResponse(MembersEmailAndPasswordDto memberEmailDto) {
        String token = jwtUtil.generateAccessToken(memberEmailDto.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(memberEmailDto.getEmail());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Refresh-Token", "Bearer " + refreshToken);
        MembersLoginResponseDto responseDto = new MembersLoginResponseDto("로그인 성공!");

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseDto);
    }

    // 카카오 로그인 로직 (리다이렉트 포함)
    public void kakaoLogin(MembersLoginRequestDto requestDto, HttpServletResponse response) throws IOException {
        // 이메일로 회원을 조회
        MembersEmailAndPasswordDto memberEmailDto = getMemberByEmail(requestDto.getEmail());
        // 카카오 로그인은 비밀번호 검증 없이 바로 로그인 처리

        // JWT 토큰 생성 후 리다이렉트
        generateLoginResponseAndRedirect(memberEmailDto, response);
    }


    // JWT 토큰 생성 후 리다이렉트 (카카오 로그인)
    private void generateLoginResponseAndRedirect(MembersEmailAndPasswordDto memberEmailDto, HttpServletResponse response) throws IOException {
        String token = jwtUtil.generateAccessToken(memberEmailDto.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(memberEmailDto.getEmail());

        response.setHeader("Authorization", "Bearer " + token);
        response.setHeader("Refresh-Token", "Bearer " + refreshToken);

        response.sendRedirect("http://121.125.6.41:5173");
    }

    // 로그아웃 로직
    public ResponseEntity<MembersLogoutResponseDto> logout(String accessToken, String refreshToken) {
        // Access Token 검증 및 무효화 처리
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            String actualAccessToken = accessToken.substring(7);
            if (jwtUtil.validateToken(actualAccessToken)) {
                jwtUtil.revokeToken(actualAccessToken);
            }
        }

        // Refresh Token 검증 및 무효화 처리
        if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
            String actualRefreshToken = refreshToken.substring(7);
            if (jwtUtil.validateToken(actualRefreshToken)) {
                jwtUtil.revokeToken(actualRefreshToken);
            }
        }

        // 로그아웃 성공 응답 반환
        MembersLogoutResponseDto response = new MembersLogoutResponseDto("로그아웃 되었습니다.");
        return ResponseEntity.ok(response);
    }
}
