package com.clean.cleanroom.members.service;

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.members.dto.MembersAddressDelResponseDto;
import com.clean.cleanroom.members.dto.MembersAddressRequestDto;
import com.clean.cleanroom.members.dto.MembersAddressResponseDto;
import com.clean.cleanroom.members.entity.Address;
import com.clean.cleanroom.members.repository.AddressRepository;
import com.clean.cleanroom.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MembersAddressServiceTest {

    @InjectMocks
    private MembersAddressService membersAddressService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private Address address;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAddress_Success() {
        // Given
        String token = "Bearer sampleToken";
        String email = "test@example.com";
        MembersAddressRequestDto requestDto = mock(MembersAddressRequestDto.class);

        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        // When
        MembersAddressResponseDto responseDto = membersAddressService.createAddress(token, requestDto);

        // Then
        assertNotNull(responseDto);
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    void getAddress_Success() {
        // Given
        String token = "Bearer sampleToken";
        String email = "test@example.com";
        List<Address> addresses = List.of(address);

        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(addressRepository.findAllByEmail(anyString())).thenReturn(addresses);

        // When
        List<MembersAddressResponseDto> responseDtoList = membersAddressService.getAddress(token);

        // Then
        assertNotNull(responseDtoList);
        assertEquals(1, responseDtoList.size());
        verify(addressRepository, times(1)).findAllByEmail(anyString());
    }

    @Test
    void delAddress_Success() {
        // Given
        String token = "Bearer sampleToken";
        String email = "test@example.com";
        Long id = 1L;

        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(addressRepository.findByEmailAndId(anyString(), anyLong())).thenReturn(Optional.of(address));

        // When
        MembersAddressDelResponseDto responseDto = membersAddressService.delAddress(token, id);

        // Then
        assertNotNull(responseDto);
        verify(addressRepository, times(1)).findByEmailAndId(anyString(), anyLong());
        verify(addressRepository, times(1)).delete(any(Address.class));
    }

    @Test
    void delAddress_ThrowsException_WhenAddressNotFound() {
        // Given
        String token = "Bearer sampleToken";
        String email = "test@example.com";
        Long id = 1L;

        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(addressRepository.findByEmailAndId(anyString(), anyLong())).thenReturn(Optional.empty());

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            membersAddressService.delAddress(token, id);
        });

        // 에러 메세지를 코드로 반환하기 때문에 getCode() 사용
        assertEquals(ErrorMsg.ADDRESS_NOT_FOUND.getCode(), exception.getCode());
        verify(addressRepository, times(1)).findByEmailAndId(anyString(), anyLong());
        verify(addressRepository, times(0)).delete(any(Address.class));
    }
}
