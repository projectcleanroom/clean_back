package com.clean.cleanroom.jwt;

import com.clean.cleanroom.enums.TokenType;
import com.clean.cleanroom.jwt.entity.RefreshToken;
import com.clean.cleanroom.jwt.repository.RefreshTokenRepository;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.jwt.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private Members member;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveToken() {
        // Given
        String email = "test@example.com";
        String token = "refreshToken123";
        when(member.getEmail()).thenReturn(email);

        // When
        tokenService.saveToken(member, token);

        // Then
        verify(refreshTokenRepository, times(1)).save(any(RefreshToken.class));
    }

    @Test
    void testRevokeAllUserTokens() {
        // Given
        String email = "test@example.com";
        when(member.getEmail()).thenReturn(email);

        RefreshToken token1 = RefreshToken.builder()
                .token("token1")
                .tokenType(TokenType.REFRESH)
                .expired(false)
                .revoked(false)
                .email(email)
                .build();

        RefreshToken token2 = RefreshToken.builder()
                .token("token2")
                .tokenType(TokenType.REFRESH)
                .expired(false)
                .revoked(false)
                .email(email)
                .build();

        when(refreshTokenRepository.findAllValidTokenByEmail(anyString()))
                .thenReturn(List.of(token1, token2));

        // When
        tokenService.revokeAllUserTokens(member);

        // Then
        assertTrue(token1.isExpired());
        assertTrue(token1.isRevoked());
        assertTrue(token2.isExpired());
        assertTrue(token2.isRevoked());
        verify(refreshTokenRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testRevokeAllUserTokens_NoValidTokens() {
        // Given
        String email = "test@example.com";
        when(member.getEmail()).thenReturn(email);
        when(refreshTokenRepository.findAllValidTokenByEmail(anyString())).thenReturn(List.of());

        // When
        tokenService.revokeAllUserTokens(member);

        // Then
        verify(refreshTokenRepository, times(0)).saveAll(anyList());
    }
}

