package com.clean.cleanroom.members.service;

import com.clean.cleanroom.enums.LoginType;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.members.dto.MembersSignupRequestDto;
import com.clean.cleanroom.members.dto.MembersProfileResponseDto;
import com.clean.cleanroom.members.dto.MembersGetProfileResponseDto;
import com.clean.cleanroom.members.dto.MembersUpdateProfileRequestDto;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.redis.RedisService;
import com.clean.cleanroom.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MembersServiceTest {

    @InjectMocks
    private MembersService membersService;

    @Mock
    private MembersRepository membersRepository;

    @Mock
    private RedisService redisService;

    @Mock
    private Members members;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signup_Success() {
        // Given
        MembersSignupRequestDto requestDto = mock(MembersSignupRequestDto.class);
        when(requestDto.getEmail()).thenReturn("test@example.com");
        when(requestDto.getPassword()).thenReturn("password123");
        when(requestDto.getNick()).thenReturn("newNick");
        when(requestDto.getPhoneNumber()).thenReturn("01098765432");
        when(requestDto.getLoginType()).thenReturn(LoginType.REGULAR);

        when(membersRepository.existsByEmail(requestDto.getEmail())).thenReturn(false);
        when(membersRepository.existsByNick(requestDto.getNick())).thenReturn(false);
        when(membersRepository.existsByPhoneNumber(requestDto.getPhoneNumber())).thenReturn(false);
        when(redisService.isVerified(requestDto.getEmail())).thenReturn(true);
        when(membersRepository.save(any(Members.class))).thenReturn(members);

        // When
        membersService.signup(requestDto);

        // Then
        verify(membersRepository, times(1)).save(any(Members.class));
    }

    @Test
    void signup_ThrowsException_WhenEmailExists() {
        // Given
        MembersSignupRequestDto requestDto = mock(MembersSignupRequestDto.class);
        when(requestDto.getEmail()).thenReturn("test@example.com");
        when(requestDto.getLoginType()).thenReturn(LoginType.REGULAR);

        when(membersRepository.existsByEmail(requestDto.getEmail())).thenReturn(true);

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> membersService.signup(requestDto));

        assertEquals(ErrorMsg.DUPLICATE_EMAIL.getCode(), exception.getCode());
        verify(membersRepository, times(1)).existsByEmail(requestDto.getEmail());
    }

    @Test
    void signup_ThrowsException_WhenNickExists() {
        // Given
        MembersSignupRequestDto requestDto = mock(MembersSignupRequestDto.class);
        when(requestDto.getNick()).thenReturn("newNick");
        when(requestDto.getLoginType()).thenReturn(LoginType.REGULAR);

        when(membersRepository.existsByNick(requestDto.getNick())).thenReturn(true);

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> membersService.signup(requestDto));

        assertEquals(ErrorMsg.DUPLICATE_NICK.getCode(), exception.getCode());
        verify(membersRepository, times(1)).existsByNick(requestDto.getNick());
    }

    @Test
    void signup_ThrowsException_WhenPhoneNumberExists() {
        // Given
        MembersSignupRequestDto requestDto = mock(MembersSignupRequestDto.class);
        when(requestDto.getPhoneNumber()).thenReturn("01012345678");
        when(requestDto.getLoginType()).thenReturn(LoginType.REGULAR);

        when(membersRepository.existsByPhoneNumber(requestDto.getPhoneNumber())).thenReturn(true);

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> membersService.signup(requestDto));

        assertEquals(ErrorMsg.DUPLICATE_PHONENUMBER.getCode(), exception.getCode());
        verify(membersRepository, times(1)).existsByPhoneNumber(requestDto.getPhoneNumber());
    }

    @Test
    void profile_Success() {
        // Given
        String token = "Bearer sampleToken";
        String email = "test@example.com";

        // MembersUpdateProfileRequestDto를 목으로 생성
        MembersUpdateProfileRequestDto requestDto = mock(MembersUpdateProfileRequestDto.class);
        when(requestDto.getNick()).thenReturn("newNick");
        when(requestDto.getPhoneNumber()).thenReturn("01098765432");
        when(requestDto.getPassword()).thenReturn("newPassword");

        when(JwtUtil.extractEmail(anyString())).thenReturn(email);
        when(membersRepository.findByEmail(email)).thenReturn(Optional.of(members));
        when(members.getNick()).thenReturn("oldNick");
        when(members.getPhoneNumber()).thenReturn("01012345678");

        // When
        MembersProfileResponseDto response = membersService.profile(token, requestDto);

        // Then
        assertNotNull(response);
        verify(membersRepository, times(1)).save(members);
    }

    @Test
    void getProfile_Success() {
        // Given
        String token = "Bearer sampleToken";
        String email = "test@example.com";

        when(JwtUtil.extractEmail(anyString())).thenReturn(email);
        when(membersRepository.findByEmail(email)).thenReturn(Optional.of(members));
        when(redisService.getObject(anyString(), eq(MembersGetProfileResponseDto.class))).thenReturn(null);

        // When
        MembersGetProfileResponseDto result = membersService.getProfile(token);

        // Then
        assertNotNull(result);
        assertEquals(members.getEmail(), result.getEmail());
        verify(membersRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void getProfile_ThrowsException_WhenEmailNotFound() {
        // Given
        String token = "Bearer sampleToken";
        String email = "test@example.com";

        when(JwtUtil.extractEmail(anyString())).thenReturn(email);
        when(membersRepository.findByEmail(email)).thenReturn(Optional.empty());

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> membersService.getProfile(token));

        assertEquals(ErrorMsg.INVALID_ID.getCode(), exception.getCode());
        verify(membersRepository, times(1)).findByEmail(anyString());
    }
}
