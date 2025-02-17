package com.clean.cleanroom.members.controller;

import com.clean.cleanroom.members.dto.KakaoAuthCodeRequestDto;
import com.clean.cleanroom.members.dto.MembersLoginRequestDto;
import com.clean.cleanroom.members.dto.MembersLoginResponseDto;
import com.clean.cleanroom.members.service.KakaoLoginService;
import com.clean.cleanroom.members.service.MembersLoginService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Tag(name = "로그인")
public class MembersLoginController {

    private final MembersLoginService membersService;
    private final KakaoLoginService kakaoLoginService;

    @PostMapping("/login")
    public ResponseEntity<MembersLoginResponseDto> login(@RequestBody @Valid MembersLoginRequestDto requestDto) {
        return membersService.login(requestDto);

    }

 // 카카오 로그인 처리
    @PostMapping("/kakao-login")
    public ResponseEntity<MembersLoginResponseDto> socialKakaoLogin(@RequestBody KakaoAuthCodeRequestDto kakaoAuthCodeRequestDto) {
        return kakaoLoginService.socialKakaoLogin(kakaoAuthCodeRequestDto);
    }
}