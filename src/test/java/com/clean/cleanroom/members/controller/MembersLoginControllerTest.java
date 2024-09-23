package com.clean.cleanroom.members.controller;

import com.clean.cleanroom.members.dto.KakaoAuthCodeRequestDto;
import com.clean.cleanroom.members.dto.MembersLoginRequestDto;
import com.clean.cleanroom.members.dto.MembersLoginResponseDto;
import com.clean.cleanroom.members.service.KakaoLoginService;
import com.clean.cleanroom.members.service.MembersLoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MembersLoginControllerTest {

    @InjectMocks
    private MembersLoginController membersLoginController;

    @Mock
    private MembersLoginService membersService;

    @Mock
    private KakaoLoginService kakaoLoginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login() {
        // given
        MembersLoginRequestDto requestDto = mock(MembersLoginRequestDto.class);
        MembersLoginResponseDto responseDto = mock(MembersLoginResponseDto.class);
        ResponseEntity<MembersLoginResponseDto> expectedResponse = new ResponseEntity<>(responseDto, HttpStatus.OK);

        when(membersService.login(any(MembersLoginRequestDto.class))).thenReturn(expectedResponse);

        // when
        ResponseEntity<MembersLoginResponseDto> result = membersLoginController.login(requestDto);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        verify(membersService, times(1)).login(requestDto);
    }

    @Test
    void socialKakaoLogin() {
        // given
        KakaoAuthCodeRequestDto kakaoAuthCodeRequestDto = mock(KakaoAuthCodeRequestDto.class);
        MembersLoginResponseDto responseDto = mock(MembersLoginResponseDto.class);
        ResponseEntity<MembersLoginResponseDto> expectedResponse = new ResponseEntity<>(responseDto, HttpStatus.OK);

        when(kakaoLoginService.socialKakaoLogin(any(KakaoAuthCodeRequestDto.class))).thenReturn(expectedResponse);

        // when
        ResponseEntity<MembersLoginResponseDto> result = membersLoginController.socialKakaoLogin(kakaoAuthCodeRequestDto);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        verify(kakaoLoginService, times(1)).socialKakaoLogin(kakaoAuthCodeRequestDto);
    }
}
