package com.clean.cleanroom.members.controller;

import com.clean.cleanroom.members.dto.*;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signup() {
        // given
        MembersRequestDto requestDto = mock(MembersRequestDto.class);
        MembersSignupResponseDto responseDto = mock(MembersSignupResponseDto.class);

        when(membersService.signup(any(MembersRequestDto.class))).thenReturn(responseDto);

        // when
        ResponseEntity<MembersSignupResponseDto> result = membersController.signup(requestDto);

        // then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        verify(membersService, times(1)).signup(requestDto);
    }

    @Test
    void profile() {
        // given
        String token = "Bearer sampleToken";
        MembersUpdateProfileRequestDto requestDto = mock(MembersUpdateProfileRequestDto.class);
        MembersProfileResponseDto responseDto = mock(MembersProfileResponseDto.class);

        when(membersService.profile(anyString(), any(MembersUpdateProfileRequestDto.class))).thenReturn(responseDto);

        // when
        ResponseEntity<MembersProfileResponseDto> result = membersController.profile(token, requestDto);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        verify(membersService, times(1)).profile(token, requestDto);
    }

    @Test
    void getProfile() {
        // given
        String token = "Bearer sampleToken";
        MembersGetProfileResponseDto responseDto = mock(MembersGetProfileResponseDto.class);

        when(membersService.getProfile(anyString())).thenReturn(responseDto);

        // when
        ResponseEntity<MembersGetProfileResponseDto> result = membersController.getProfile(token);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        verify(membersService, times(1)).getProfile(token);
    }
}
