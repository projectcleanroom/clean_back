package com.clean.cleanroom.members.controller;

import com.clean.cleanroom.members.dto.MembersLoginRequestDto;
import com.clean.cleanroom.members.dto.MembersLoginResponseDto;
import com.clean.cleanroom.members.dto.MembersLogoutResponseDto;
import com.clean.cleanroom.members.service.MembersService;
import com.clean.cleanroom.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MembersLoginAndLogoutController {

    private final MembersService membersService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<MembersLoginResponseDto> login(@RequestBody MembersLoginRequestDto requestDto) {
        try {
            MembersLoginResponseDto responseDto = membersService.login(requestDto);
            String token = jwtUtil.generateToken(responseDto.getEmail());
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(responseDto);
        } catch (RuntimeException e) {
            MembersLoginResponseDto errorResponse = new MembersLoginResponseDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<MembersLogoutResponseDto> logout(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String actualToken = token.substring(7);
            if (jwtUtil.validateToken(actualToken)) {
                MembersLogoutResponseDto response = new MembersLogoutResponseDto("로그아웃 되었습니다.");
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MembersLogoutResponseDto("로그인 후 가능합니다."));
    }
}

