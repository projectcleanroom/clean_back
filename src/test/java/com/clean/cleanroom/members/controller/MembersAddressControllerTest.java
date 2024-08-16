package com.clean.cleanroom.members.controller;

import com.clean.cleanroom.members.dto.MembersAddressDelResponseDto;
import com.clean.cleanroom.members.dto.MembersAddressRequestDto;
import com.clean.cleanroom.members.dto.MembersAddressResponseDto;
import com.clean.cleanroom.members.service.MembersAddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MembersAddressControllerTest {

    @InjectMocks
    private MembersAddressController membersAddressController;

    @Mock
    private MembersAddressService membersAddressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAddress() {
        // given
        String token = "Bearer sampleToken";
        MembersAddressRequestDto requestDto = mock(MembersAddressRequestDto.class);
        MembersAddressResponseDto responseDto = mock(MembersAddressResponseDto.class);

        when(membersAddressService.createAddress(anyString(), any(MembersAddressRequestDto.class)))
                .thenReturn(responseDto);

        // when
        ResponseEntity<MembersAddressResponseDto> result = membersAddressController.createAddress(token, requestDto);

        // then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        verify(membersAddressService, times(1)).createAddress(token, requestDto);
    }

    @Test
    void getAddress() {
        // given
        String token = "Bearer sampleToken";
        List<MembersAddressResponseDto> responseDtos = Collections.singletonList(mock(MembersAddressResponseDto.class));

        when(membersAddressService.getAddress(anyString())).thenReturn(responseDtos);

        // when
        ResponseEntity<List<MembersAddressResponseDto>> result = membersAddressController.getAddress(token);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseDtos, result.getBody());
        verify(membersAddressService, times(1)).getAddress(token);
    }

    @Test
    void delAddress() {
        // given
        String token = "Bearer sampleToken";
        Long id = 1L;
        MembersAddressDelResponseDto responseDto = mock(MembersAddressDelResponseDto.class);

        when(membersAddressService.delAddress(anyString(), anyLong())).thenReturn(responseDto);

        // when
        ResponseEntity<MembersAddressDelResponseDto> result = membersAddressController.delAddress(token, id);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        verify(membersAddressService, times(1)).delAddress(token, id);
    }
}
