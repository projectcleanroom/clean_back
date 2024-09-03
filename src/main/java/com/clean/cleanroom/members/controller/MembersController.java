package com.clean.cleanroom.members.controller;

import com.clean.cleanroom.members.dto.*;
import com.clean.cleanroom.members.service.EmailSenderService;
import com.clean.cleanroom.members.service.MembersService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MembersController {
    private final MembersService membersService;
    private final EmailSenderService emailSenderService;

    public MembersController(MembersService membersService, EmailSenderService emailSenderService) {
        this.membersService = membersService;
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/signup")
    public ResponseEntity<MembersSignupResponseDto> signup(@RequestBody @Valid MembersSignupRequestDto requestDto) {
        MembersSignupResponseDto membersSignupResponseDto = membersService.signup(requestDto);
        return new ResponseEntity<>(membersSignupResponseDto, HttpStatus.CREATED);
    }

    // 회원가입 전 이메일 인증 요청
    @PostMapping("/request-email-verification")
    public ResponseEntity<String> requestEmailVerification(@RequestBody @Valid EmailVerificationRequestDto requestDto) {
        String verificationCode = membersService.generateEmailVerificationCode(requestDto.getEmail());
        emailSenderService.sendVerificationEmail(requestDto.getEmail(), verificationCode);
        return new ResponseEntity<>("인증 코드가 전송되었습니다.", HttpStatus.OK);
    }

    // 이메일 인증 완료
    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestBody VerifcationCodeRequestDto request) {
        membersService.verifyEmail(request.getEmail(), request.getCode());
        return new ResponseEntity<>("이메일이 성공적으로 인증되었습니다.", HttpStatus.OK);
    }

    @PatchMapping("/profile")
    public ResponseEntity<MembersProfileResponseDto> profile(@RequestHeader("Authorization") String token, @RequestBody MembersUpdateProfileRequestDto requestDto) {
        MembersProfileResponseDto membersProfileResponseDto = membersService.profile(token, requestDto);
        return new ResponseEntity<>(membersProfileResponseDto, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<MembersGetProfileResponseDto> getProfile(@RequestHeader("Authorization") String token) {
        MembersGetProfileResponseDto membersGetProfileResponseDto = membersService.getProfile(token);
        return new ResponseEntity<>(membersGetProfileResponseDto, HttpStatus.OK);
    }
}
