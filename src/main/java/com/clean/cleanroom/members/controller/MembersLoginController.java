package com.clean.cleanroom.members.controller;

import com.clean.cleanroom.members.dto.MembersLoginRequestDto;
import com.clean.cleanroom.members.dto.MembersLoginResponseDto;
import com.clean.cleanroom.members.service.MembersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MembersLoginController {

    private final MembersService membersService;

    @PostMapping("/login")
    public ResponseEntity<MembersLoginResponseDto> login(@RequestBody MembersLoginRequestDto requestDto) {
        MembersLoginResponseDto responseDto = membersService.login(requestDto);
        if (responseDto != null) {
            return ResponseEntity.ok(responseDto);
        }
        return ResponseEntity.status(401).body(null); // 인증 실패 시
    }
}

