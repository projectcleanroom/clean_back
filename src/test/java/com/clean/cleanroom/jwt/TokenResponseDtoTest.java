package com.clean.cleanroom.jwt;

import com.clean.cleanroom.jwt.dto.TokenResponseDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TokenResponseDtoTest {

    @Test
    void testTokenResponseDtoCreation() {
        // Given
        String accessToken = "sampleAccessToken";
        String refreshToken = "sampleRefreshToken";

        // When
        TokenResponseDto tokenResponseDto = new TokenResponseDto(accessToken, refreshToken);

        // Then
        assertEquals(accessToken, tokenResponseDto.getAccessToken());
        assertEquals(refreshToken, tokenResponseDto.getRefreshToken());
    }
}

