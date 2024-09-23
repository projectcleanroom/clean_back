package com.clean.cleanroom.members.controller;

import com.clean.cleanroom.members.dto.*;
import com.clean.cleanroom.members.service.EmailSenderService;
import com.clean.cleanroom.members.service.MembersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MembersControllerTest {

    @InjectMocks
    private MembersController membersController;

    @Mock
    private MembersService membersService;

    @Mock
    private EmailSenderService emailSenderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signup() {

        // Given
        // Dto 모킹
        MembersSignupRequestDto requestDto = mock(MembersSignupRequestDto.class);
        MembersSignupResponseDto responseDto = mock(MembersSignupResponseDto.class);

        when(membersService.signup(any(MembersSignupRequestDto.class))).thenReturn(responseDto);

        // When
        ResponseEntity<MembersSignupResponseDto> result = membersController.signup(requestDto);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        // 비즈니스 로직과 결합도를 낮추기 위해 any() 사용
        verify(membersService, times(1)).signup(any(MembersSignupRequestDto.class));
    }

    @Test
    void profile() {

        // Given
        String token = "Bearer sampleToken";
        MembersUpdateProfileRequestDto requestDto = mock(MembersUpdateProfileRequestDto.class);
        MembersProfileResponseDto responseDto = mock(MembersProfileResponseDto.class);

        when(membersService.profile(anyString(), any(MembersUpdateProfileRequestDto.class))).thenReturn(responseDto);

        // When
        ResponseEntity<MembersProfileResponseDto> result = membersController.profile(token, requestDto);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        verify(membersService, times(1)).profile(anyString(), any(MembersUpdateProfileRequestDto.class));
    }

    @Test
    void getProfile() {
        // Given
        String token = "Bearer sampleToken";
        MembersGetProfileResponseDto responseDto = mock(MembersGetProfileResponseDto.class);

        when(membersService.getProfile(anyString())).thenReturn(responseDto);

        // When
        ResponseEntity<MembersGetProfileResponseDto> result = membersController.getProfile(token);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        verify(membersService, times(1)).getProfile(anyString());
    }

    @Test
    void requestEmailVerification() {
        // Given
        EmailVerificationRequestDto requestDto = mock(EmailVerificationRequestDto.class);
        String verificationCode = "123456";

        when(requestDto.getEmail()).thenReturn("test@example.com");
        when(membersService.generateEmailVerificationCode(anyString())).thenReturn(verificationCode);
        doNothing().when(emailSenderService).sendVerificationEmail(anyString(), anyString());

        // When
        ResponseEntity<String> result = membersController.requestEmailVerification(requestDto);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("인증 코드가 전송되었습니다.", result.getBody());
        verify(membersService, times(1)).generateEmailVerificationCode("test@example.com");
        verify(emailSenderService, times(1)).sendVerificationEmail("test@example.com", verificationCode);
    }


    @Test
    void verifyEmail() {
        // Given
        VerifcationCodeRequestDto requestDto = mock(VerifcationCodeRequestDto.class);

        // requestDto의 getEmail과 getCode 메서드가 호출될 때, 적절한 값을 반환하도록 설정
        when(requestDto.getEmail()).thenReturn("test@example.com");
        when(requestDto.getCode()).thenReturn("123456");

        // membersService의 verifyEmail 메서드를 모킹
        doNothing().when(membersService).verifyEmail(anyString(), anyString());

        // When
        ResponseEntity<String> result = membersController.verifyEmail(requestDto);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("이메일이 성공적으로 인증되었습니다.", result.getBody());
        verify(membersService, times(1)).verifyEmail("test@example.com", "123456");
    }

}
