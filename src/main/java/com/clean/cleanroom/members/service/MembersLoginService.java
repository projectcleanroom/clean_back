package com.clean.cleanroom.members.service;

import com.clean.cleanroom.members.dto.MembersLoginRequestDto;
import com.clean.cleanroom.members.dto.MembersLoginResponseDto;
import com.clean.cleanroom.members.dto.MembersLogoutResponseDto;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MembersLoginService {

    private final MembersRepository membersRepository;
    private final JwtUtil jwtUtil;

    public ResponseEntity<MembersLoginResponseDto> login(MembersLoginRequestDto requestDto) {
        // 이메일로 회원을 조회. 없으면 예외를 던짐
        Members member = membersRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 아이디입니다."));

        // 비밀번호가 일치하는지 확인. 일치하지 않으면 예외를 던짐
        if (!requestDto.getPassword().equals(member.getPassword())) {
            throw new RuntimeException("잘못된 비밀번호입니다.");
        }

        // JWT 토큰 생성
        String token = jwtUtil.generateToken(member.getEmail());
        // HTTP 헤더에 토큰 추가
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        // 응답 DTO 생성
        MembersLoginResponseDto responseDto = new MembersLoginResponseDto(member);

        // 응답 반환
        return ResponseEntity.ok()
                .headers(headers)
                .body(responseDto);
    }

    public ResponseEntity<MembersLogoutResponseDto> logout(String token) {
        // 토큰이 유효한지 확인
        if (token != null && token.startsWith("Bearer ")) {
            String actualToken = token.substring(7);
            if (jwtUtil.validateToken(actualToken)) {
                // 토큰이 유효하면 로그아웃 성공 응답 반환
                MembersLogoutResponseDto response = new MembersLogoutResponseDto("로그아웃 되었습니다.");
                return ResponseEntity.ok(response);
            }
        }
        // 토큰이 유효하지 않으면 인증 실패 응답 반환
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MembersLogoutResponseDto("로그인 후 가능합니다."));
    }
}