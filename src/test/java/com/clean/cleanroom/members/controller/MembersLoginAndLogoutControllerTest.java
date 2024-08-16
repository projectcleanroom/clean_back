package com.clean.cleanroom.members.controller;

import com.clean.cleanroom.members.dto.MembersLoginRequestDto;
import com.clean.cleanroom.members.dto.MembersLoginResponseDto;
import com.clean.cleanroom.members.dto.MembersLogoutResponseDto;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MembersLoginAndLogoutControllerTest {

    @InjectMocks
    private MembersLoginAndLogoutController membersLoginAndLogoutController;

    @Mock
    private MembersLoginService membersService;

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
        ResponseEntity<MembersLoginResponseDto> result = membersLoginAndLogoutController.login(requestDto);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        verify(membersService, times(1)).login(requestDto);
    }

    @Test
    void logout() {
        // given
        String accessToken = "Bearer sampleAccessToken";
        String refreshToken = "sampleRefreshToken";
        MembersLogoutResponseDto responseDto = mock(MembersLogoutResponseDto.class);
        ResponseEntity<MembersLogoutResponseDto> expectedResponse = new ResponseEntity<>(responseDto, HttpStatus.OK);

        when(membersService.logout(anyString(), anyString())).thenReturn(expectedResponse);

        // when
        ResponseEntity<MembersLogoutResponseDto> result = membersLoginAndLogoutController.logout(accessToken, refreshToken);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        verify(membersService, times(1)).logout(accessToken, refreshToken);
    }
}
