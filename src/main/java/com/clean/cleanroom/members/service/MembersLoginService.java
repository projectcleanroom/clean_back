package com.clean.cleanroom.members.service;

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.members.dto.MembersLoginRequestDto;
import com.clean.cleanroom.members.dto.MembersLoginResponseDto;
import com.clean.cleanroom.members.dto.MembersLogoutResponseDto;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.util.JwtUtil;
import com.clean.cleanroom.jwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MembersLoginService {

    private final MembersRepository membersRepository;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    // 일반 로그인 로직
    public ResponseEntity<MembersLoginResponseDto> login(MembersLoginRequestDto requestDto) {
        // 이메일로 회원을 조회
        Members member = getMemberByEmail(requestDto.getEmail());

        // 비밀번호 검증
        if (!member.checkPassword(requestDto.getPassword())) {
            throw new CustomException(ErrorMsg.INVALID_PASSWORD); // 비밀번호가 일치하지 않으면 예외 발생
        }

        // 공통 로직 호출 (JWT 토큰 생성 및 응답 반환)
        return generateLoginResponse(member);
    }

    // 카카오 로그인 로직
    public ResponseEntity<MembersLoginResponseDto> kakaoLogin(MembersLoginRequestDto requestDto) {
        // 이메일로 회원을 조회
        Members member = getMemberByEmail(requestDto.getEmail());

        // 카카오 로그인은 비밀번호 검증 없이 바로 로그인 처리

        // 공통 로직 호출 (JWT 토큰 생성 및 응답 반환)
        return generateLoginResponse(member);
    }

    // 공통 로직: 이메일로 회원을 조회하고 없으면 예외를 던짐
    private Members getMemberByEmail(String email) {
        return membersRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorMsg.INVALID_ID));
    }

    // 공통 로직: JWT 토큰 생성 및 응답 반환
    private ResponseEntity<MembersLoginResponseDto> generateLoginResponse(Members member) {
        String token = jwtUtil.generateAccessToken(member.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(member.getEmail());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Refresh-Token", "Bearer " + refreshToken);
        MembersLoginResponseDto responseDto = new MembersLoginResponseDto(member);

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseDto);
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
