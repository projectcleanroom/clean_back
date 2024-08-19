package com.clean.cleanroom.members.service;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.jwt.service.TokenService;
import com.clean.cleanroom.members.dto.MembersLoginRequestDto;
import com.clean.cleanroom.members.dto.MembersLoginResponseDto;
import com.clean.cleanroom.members.dto.MembersLogoutResponseDto;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MembersLoginServiceTest {

    @InjectMocks
    private MembersLoginService membersLoginService;

    @Mock
    private MembersRepository membersRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private TokenService tokenService;

    @Mock
    private Members member;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_Success() {
        // Given
        String email = "test@example.com";
        String password = "password123";

        MembersLoginRequestDto requestDto = mock(MembersLoginRequestDto.class);

        when(requestDto.getEmail()).thenReturn(email);
        when(requestDto.getPassword()).thenReturn(password);

        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(member.checkPassword(anyString())).thenReturn(true);

        // When
        ResponseEntity<MembersLoginResponseDto> response = membersLoginService.login(requestDto);

        // Then
        assertNotNull(response);
        assertEquals(member.getEmail(), response.getBody().getEmail());
        verify(membersRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void login_ThrowsException_WhenEmailNotFound() {
        // Given
        String email = "test@example.com";
        MembersLoginRequestDto requestDto = mock(MembersLoginRequestDto.class);
        when(requestDto.getEmail()).thenReturn(email);

        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            membersLoginService.login(requestDto);
        });

        assertEquals(ErrorMsg.INVALID_ID.getCode(), exception.getCode());
        verify(membersRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void login_ThrowsException_WhenPasswordIsIncorrect() {
        // Given
        String email = "test@example.com";
        String password = "wrongPassword";
        MembersLoginRequestDto requestDto = mock(MembersLoginRequestDto.class);
        when(requestDto.getEmail()).thenReturn(email);
        when(requestDto.getPassword()).thenReturn(password);

        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(member.checkPassword(anyString())).thenReturn(false);

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            membersLoginService.login(requestDto);
        });

        assertEquals(ErrorMsg.INVALID_PASSWORD.getCode(), exception.getCode());
        verify(membersRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void logout_Success() {
        // Given
        String accessToken = "Bearer validAccessToken";
        String refreshToken = "Bearer validRefreshToken";

        when(jwtUtil.validateToken(anyString())).thenReturn(true);

        // When
        ResponseEntity<MembersLogoutResponseDto> response = membersLoginService.logout(accessToken, refreshToken);

        // Then
        assertNotNull(response);
        assertEquals("로그아웃 되었습니다.", response.getBody().getMessage());

        verify(jwtUtil, times(1)).validateToken("validAccessToken");
        verify(jwtUtil, times(1)).validateToken("validRefreshToken");

        verify(jwtUtil, times(2)).revokeToken(anyString());
    }

    // 추가된 테스트: Access Token이 유효하지 않은 경우의 테스트
    @Test
    void logout_WithInvalidAccessToken_Success() {
        // Given
        String accessToken = "Bearer invalidAccessToken";
        String refreshToken = "Bearer validRefreshToken";

        when(jwtUtil.validateToken("invalidAccessToken")).thenReturn(false);
        when(jwtUtil.validateToken("validRefreshToken")).thenReturn(true);

        // When
        ResponseEntity<MembersLogoutResponseDto> response = membersLoginService.logout(accessToken, refreshToken);

        // Then
        assertNotNull(response);
        assertEquals("로그아웃 되었습니다.", response.getBody().getMessage());
        verify(jwtUtil, times(1)).validateToken("invalidAccessToken");
        verify(jwtUtil, times(1)).validateToken("validRefreshToken");
        verify(jwtUtil, times(1)).revokeToken("validRefreshToken");
    }

    // 추가된 테스트: Refresh Token이 유효하지 않은 경우의 테스트
    @Test
    void logout_WithInvalidRefreshToken_Success() {
        // Given
        String accessToken = "Bearer validAccessToken";
        String refreshToken = "Bearer invalidRefreshToken";

        when(jwtUtil.validateToken("validAccessToken")).thenReturn(true);
        when(jwtUtil.validateToken("invalidRefreshToken")).thenReturn(false);

        // When
        ResponseEntity<MembersLogoutResponseDto> response = membersLoginService.logout(accessToken, refreshToken);

        // Then
        assertNotNull(response);
        assertEquals("로그아웃 되었습니다.", response.getBody().getMessage());
        verify(jwtUtil, times(1)).validateToken("validAccessToken");
        verify(jwtUtil, times(1)).validateToken("invalidRefreshToken");
        verify(jwtUtil, times(1)).revokeToken("validAccessToken");
    }

    // Access Token이 null인 경우의 테스트
    @Test
    void logout_WithNullAccessToken() {
        // Given: 테스트에 필요한 값 설정
        String accessToken = null;
        String refreshToken = "Bearer validRefreshToken";

        when(jwtUtil.validateToken("validRefreshToken")).thenReturn(true);

        // When: 로그아웃 메서드 호출
        ResponseEntity<MembersLogoutResponseDto> response = membersLoginService.logout(accessToken, refreshToken);

        // Then: 결과 확인
        assertNotNull(response);
        assertEquals("로그아웃 되었습니다.", response.getBody().getMessage());

        // access token이 null이므로 검증되지 않음을 확인
        verify(jwtUtil, never()).validateToken(eq("validAccessToken"));  // accessToken이 null이기 때문에 호출되지 않는 것을 확인

        // refresh token이 검증되고 무효화되었는지 확인
        verify(jwtUtil, times(1)).validateToken("validRefreshToken");
        verify(jwtUtil, times(1)).revokeToken("validRefreshToken");
    }



    @Test
    void logout_WithNullRefreshToken() {
        // Given: 테스트에 필요한 값 설정
        String accessToken = "Bearer validAccessToken";
        String refreshToken = null;

        when(jwtUtil.validateToken("validAccessToken")).thenReturn(true);

        // When: 로그아웃 메서드 호출
        ResponseEntity<MembersLogoutResponseDto> response = membersLoginService.logout(accessToken, refreshToken);

        // Then: 결과 확인
        assertNotNull(response);
        assertEquals("로그아웃 되었습니다.", response.getBody().getMessage());

        // access token이 검증되고 무효화되었는지 확인
        verify(jwtUtil, times(1)).validateToken("validAccessToken");
        verify(jwtUtil, times(1)).revokeToken("validAccessToken");

        // refresh token이 null이므로 검증되지 않음을 확인
        verify(jwtUtil, times(0)).validateToken("validRefreshToken");  // refreshToken이 null이기 때문에 검증되지 않는 것을 확인
    }

}
