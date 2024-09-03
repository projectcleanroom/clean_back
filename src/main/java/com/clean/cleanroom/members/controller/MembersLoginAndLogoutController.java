package com.clean.cleanroom.members.controller;

import com.clean.cleanroom.members.dto.KakaoAuthCodeRequestDto;
import com.clean.cleanroom.members.dto.MembersLoginRequestDto;
import com.clean.cleanroom.members.dto.MembersLoginResponseDto;
import com.clean.cleanroom.members.dto.MembersLogoutResponseDto;
import com.clean.cleanroom.members.service.KakaoLoginService;
import com.clean.cleanroom.members.service.MembersLoginService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MembersLoginAndLogoutController {

    private final MembersLoginService membersService;
    private final KakaoLoginService kakaoLoginService;

    @PostMapping("/login")
    public ResponseEntity<MembersLoginResponseDto> login(@RequestBody @Valid MembersLoginRequestDto requestDto) {
        return membersService.login(requestDto);

    }

    @PostMapping("/logout")
    public ResponseEntity<MembersLogoutResponseDto> logout(@RequestHeader("Authorization") String accessToken, @RequestHeader("Refresh-Token") String refreshToken) {
        return membersService.logout(accessToken, refreshToken);
    }

    @PostMapping("/kakao-login")
    public void socialKakaoLogin(@RequestBody KakaoAuthCodeRequestDto kakaoAuthCodeRequestDto, HttpServletResponse response) throws IOException {
        kakaoLoginService.socialKakaoLogin(kakaoAuthCodeRequestDto, response);
    }
}
