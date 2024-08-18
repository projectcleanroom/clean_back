package com.clean.cleanroom.members.service;

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.members.dto.*;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MembersServiceTest {

    @InjectMocks
    private MembersService membersService;

    @Mock
    private MembersRepository membersRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private Members members;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signup_Success() {
        // Given
        MembersRequestDto requestDto = mock(MembersRequestDto.class);
        when(requestDto.getEmail()).thenReturn("test@example.com");
        when(requestDto.getNick()).thenReturn("testNick");
        when(requestDto.getPhoneNumber()).thenReturn("01012345678");
        when(requestDto.getPassword()).thenReturn("password123");

        when(membersRepository.existsByEmail(anyString())).thenReturn(false);
        when(membersRepository.existsByNick(anyString())).thenReturn(false);
        when(membersRepository.existsByPhoneNumber(anyString())).thenReturn(false);
        when(membersRepository.save(any(Members.class))).thenReturn(members);

        // When
        MembersSignupResponseDto response = membersService.signup(requestDto);

        // Then
        assertNotNull(response);
        verify(membersRepository, times(1)).save(any(Members.class));
    }

    @Test
    void signup_ThrowsException_WhenEmailExists() {
        // Given
        MembersRequestDto requestDto = mock(MembersRequestDto.class);
        when(requestDto.getEmail()).thenReturn("test@example.com");

        when(membersRepository.existsByEmail(anyString())).thenReturn(true);

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            membersService.signup(requestDto);
        });

        assertEquals(ErrorMsg.DUPLICATE_EMAIL.getCode(), exception.getCode());
        verify(membersRepository, times(1)).existsByEmail(anyString());
    }

    @Test
    void signup_ThrowsException_WhenNickExists() {
        // Given
        MembersRequestDto requestDto = mock(MembersRequestDto.class);
        when(requestDto.getNick()).thenReturn("testNick");

        when(membersRepository.existsByNick(anyString())).thenReturn(true);

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            membersService.signup(requestDto);
        });

        assertEquals(ErrorMsg.DUPLICATE_NICK.getCode(), exception.getCode());
        verify(membersRepository, times(1)).existsByNick(anyString());
    }

    @Test
    void signup_ThrowsException_WhenPhoneNumberExists() {
        // Given
        MembersRequestDto requestDto = mock(MembersRequestDto.class);
        when(requestDto.getPhoneNumber()).thenReturn("01012345678");

        when(membersRepository.existsByPhoneNumber(anyString())).thenReturn(true);

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            membersService.signup(requestDto);
        });

        assertEquals(ErrorMsg.DUPLICATE_PHONENUMBER.getCode(), exception.getCode());
        verify(membersRepository, times(1)).existsByPhoneNumber(anyString());
    }

    @Test
    void profile_Success() {
        // Given
        String token = "Bearer sampleToken";
        MembersUpdateProfileRequestDto requestDto = mock(MembersUpdateProfileRequestDto.class);
        when(requestDto.getNick()).thenReturn("newNick");
        when(requestDto.getPhoneNumber()).thenReturn("01098765432");

        when(jwtUtil.extractEmail(anyString())).thenReturn("test@example.com");
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(members));
        when(members.getNick()).thenReturn("oldNick");
        when(members.getPhoneNumber()).thenReturn("01012345678");

        // When
        MembersProfileResponseDto response = membersService.profile(token, requestDto);

        // Then
        assertNotNull(response);
        verify(membersRepository, times(1)).save(members);
    }

    @Test
    void profile_ThrowsException_WhenEmailNotFound() {
        // Given
        String token = "Bearer sampleToken";
        MembersUpdateProfileRequestDto requestDto = mock(MembersUpdateProfileRequestDto.class);

        when(jwtUtil.extractEmail(anyString())).thenReturn("test@example.com");
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            membersService.profile(token, requestDto);
        });

        assertEquals(ErrorMsg.INVALID_ID.getCode(), exception.getCode());
        verify(membersRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void profile_ThrowsException_WhenNickExists() {
        // Given
        String token = "Bearer sampleToken";
        MembersUpdateProfileRequestDto requestDto = mock(MembersUpdateProfileRequestDto.class);
        when(requestDto.getNick()).thenReturn("newNick");

        when(jwtUtil.extractEmail(anyString())).thenReturn("test@example.com");
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(members));
        when(members.getNick()).thenReturn("oldNick");
        when(membersRepository.existsByNick(anyString())).thenReturn(true);

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            membersService.profile(token, requestDto);
        });

        assertEquals(ErrorMsg.DUPLICATE_NICK.getCode(), exception.getCode());
        verify(membersRepository, times(1)).existsByNick(anyString());
    }

    @Test
    void profile_ThrowsException_WhenPhoneNumberExists() {
        // Given
        String token = "Bearer sampleToken";
        MembersUpdateProfileRequestDto requestDto = mock(MembersUpdateProfileRequestDto.class);
        when(requestDto.getPhoneNumber()).thenReturn("01098765432");
        when(requestDto.getNick()).thenReturn("newNick");

        when(jwtUtil.extractEmail(anyString())).thenReturn("test@example.com");
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(members));
        when(members.getPhoneNumber()).thenReturn("01012345678");
        when(members.getNick()).thenReturn("currentNick");
        when(membersRepository.existsByPhoneNumber(anyString())).thenReturn(true);

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            membersService.profile(token, requestDto);
        });

        assertEquals(ErrorMsg.DUPLICATE_PHONENUMBER.getCode(), exception.getCode());
        verify(membersRepository, times(1)).existsByPhoneNumber(anyString());
    }

    @Test
    void getProfile_Success() {
        // Given
        String token = "Bearer sampleToken";
        when(jwtUtil.extractEmail(anyString())).thenReturn("test@example.com");
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(members));

        // When
        MembersGetProfileResponseDto response = membersService.getProfile(token);

        // Then
        assertNotNull(response);
        assertEquals(members.getEmail(), response.getEmail());
        verify(membersRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void getProfile_ThrowsException_WhenEmailNotFound() {
        // Given
        String token = "Bearer sampleToken";

        when(jwtUtil.extractEmail(anyString())).thenReturn("test@example.com");
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            membersService.getProfile(token);
        });

        assertEquals(ErrorMsg.INVALID_ID.getCode(), exception.getCode());
        verify(membersRepository, times(1)).findByEmail(anyString());
    }
}
