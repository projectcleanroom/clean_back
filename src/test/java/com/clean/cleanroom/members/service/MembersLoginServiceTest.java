package com.clean.cleanroom.members.service;

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.members.dto.MembersEmailAndPasswordDto;
import com.clean.cleanroom.members.dto.MembersLoginRequestDto;
import com.clean.cleanroom.members.dto.MembersLoginResponseDto;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MembersLoginServiceTest {

    @InjectMocks
    private MembersLoginService membersLoginService;

    @Mock
    private MembersRepository membersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_Success() {
        // Given
        String email = "test@example.com";
        String password = "password123";
        String encodedPassword = "encodedPassword123";
        MembersLoginRequestDto requestDto = new MembersLoginRequestDto(email, password);
        MembersEmailAndPasswordDto membersEmailAndPasswordDto = new MembersEmailAndPasswordDto(email, encodedPassword);

        when(membersRepository.findEmailByEmail(email)).thenReturn(Optional.of(membersEmailAndPasswordDto));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

        // When
        ResponseEntity<MembersLoginResponseDto> response = membersLoginService.login(requestDto);

        // Then
        assertNotNull(response);
        assertEquals("로그인 성공!", response.getBody().getMessage());
        assertTrue(response.getHeaders().containsKey("Authorization"));
        assertTrue(response.getHeaders().containsKey("Refresh-Token"));
        verify(membersRepository, times(1)).findEmailByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, encodedPassword);
    }

    @Test
    void login_ThrowsException_WhenEmailNotFound() {
        // Given
        String email = "test@example.com";
        MembersLoginRequestDto requestDto = new MembersLoginRequestDto(email, "password123");

        when(membersRepository.findEmailByEmail(email)).thenReturn(Optional.empty());

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> membersLoginService.login(requestDto));
        assertEquals(ErrorMsg.INVALID_ID.getCode(), exception.getCode());
        verify(membersRepository, times(1)).findEmailByEmail(email);
    }

    @Test
    void login_ThrowsException_WhenPasswordIsIncorrect() {
        // Given
        String email = "test@example.com";
        String password = "wrongPassword";
        String encodedPassword = "encodedPassword123";
        MembersLoginRequestDto requestDto = new MembersLoginRequestDto(email, password);
        MembersEmailAndPasswordDto membersEmailAndPasswordDto = new MembersEmailAndPasswordDto(email, encodedPassword);

        when(membersRepository.findEmailByEmail(email)).thenReturn(Optional.of(membersEmailAndPasswordDto));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> membersLoginService.login(requestDto));
        assertEquals(ErrorMsg.INVALID_PASSWORD.getCode(), exception.getCode());
        verify(membersRepository, times(1)).findEmailByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, encodedPassword);
    }

    @Test
    void checkPassword_Success() {
        // Given
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword123";
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        // When & Then
        assertDoesNotThrow(() -> membersLoginService.checkPassword(rawPassword, encodedPassword));
    }

    @Test
    void checkPassword_ThrowsException_WhenPasswordIsIncorrect() {
        // Given
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword123";
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> membersLoginService.checkPassword(rawPassword, encodedPassword));
        assertEquals(ErrorMsg.INVALID_PASSWORD.getCode(), exception.getCode());
    }

    @Test
    void kakaoLogin_Success() {
        // Given
        String email = "test@example.com";
        String password = "password123";
        String encodedPassword = "encodedPassword123";
        MembersLoginRequestDto requestDto = new MembersLoginRequestDto(email, password);
        MembersEmailAndPasswordDto membersEmailAndPasswordDto = new MembersEmailAndPasswordDto(email, encodedPassword);

        when(membersRepository.findEmailByEmail(email)).thenReturn(Optional.of(membersEmailAndPasswordDto));

        // When
        ResponseEntity<MembersLoginResponseDto> response = membersLoginService.kakaoLogin(requestDto);

        // Then
        assertNotNull(response);
        assertEquals("로그인 성공!", response.getBody().getMessage());
        assertTrue(response.getHeaders().containsKey("Authorization"));
        assertTrue(response.getHeaders().containsKey("Refresh-Token"));
        verify(membersRepository, times(1)).findEmailByEmail(email);
    }
}
