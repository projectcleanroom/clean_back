package com.clean.cleanroom.jwt;

import com.clean.cleanroom.enums.TokenType;
import com.clean.cleanroom.jwt.entity.RefreshToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefreshTokenEntityTest {

    private RefreshToken refreshToken;

    @BeforeEach
    void setUp() {
        // Given: 초기 설정
        refreshToken = RefreshToken.builder()
                .id(1L)
                .email("test@example.com")
                .token("sampleToken")
                .tokenType(TokenType.REFRESH)
                .expired(false)
                .revoked(false)
                .build();
    }

    @Test
    void testRefreshTokenCreation() {
        // Then: RefreshToken 객체가 올바르게 생성되었는지 검증
        assertNotNull(refreshToken);
        assertEquals(1L, refreshToken.getId());
        assertEquals("test@example.com", refreshToken.getEmail());
        assertEquals("sampleToken", refreshToken.getToken());
        assertEquals(TokenType.REFRESH, refreshToken.getTokenType());
        assertFalse(refreshToken.isExpired());
        assertFalse(refreshToken.isRevoked());
    }

    @Test
    void testExpireMethod() {
        // When: expire 메서드 호출
        refreshToken.expire();

        // Then: expired 필드가 true로 변경되었는지 검증
        assertTrue(refreshToken.isExpired());
    }

    @Test
    void testRevokeMethod() {
        // When: revoke 메서드 호출
        refreshToken.revoke();

        // Then: revoked 필드가 true로 변경되었는지 검증
        assertTrue(refreshToken.isRevoked());
    }
}

