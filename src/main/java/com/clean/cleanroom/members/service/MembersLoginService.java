package com.clean.cleanroom.members.service;

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.members.dto.MembersEmailAndPasswordDto;
import com.clean.cleanroom.members.dto.MembersLoginRequestDto;
import com.clean.cleanroom.members.dto.MembersLoginResponseDto;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MembersLoginService {

    private final MembersRepository membersRepository;
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
        String token = JwtUtil.generateAccessToken(memberEmailDto.getEmail());
        String refreshToken = JwtUtil.generateRefreshToken(memberEmailDto.getEmail());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Refresh-Token", "Bearer " + refreshToken);
        System.out.println("엑세스 토큰" + token);
        MembersLoginResponseDto responseDto = new MembersLoginResponseDto("로그인 성공!");

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseDto);
    }

    // 카카오 로그인 로직
    public ResponseEntity<MembersLoginResponseDto> kakaoLogin(MembersLoginRequestDto requestDto) {
        // 이메일로 회원을 조회
        MembersEmailAndPasswordDto memberEmailDto = getMemberByEmail(requestDto.getEmail());

        // JWT 토큰 생성 및 응답 반환
        return generateLoginResponse(memberEmailDto);
    }
}
