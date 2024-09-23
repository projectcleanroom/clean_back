package com.clean.cleanroom.members.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MembersLoginResponseDtoTest {

    @Test
    void testMembersLoginResponseDtoConstructor() {

        // Given
        String expectedMessage = "로그인 성공";

        // When
        MembersLoginResponseDto responseDto = new MembersLoginResponseDto(expectedMessage);

        // Then
        assertEquals(expectedMessage, responseDto.getMessage());
    }

    @Test
    void testMembersLoginResponseDtoConstructor_WithNullMessage() {
        // Given
        String expectedMessage = null;

        // When
        MembersLoginResponseDto responseDto = new MembersLoginResponseDto(expectedMessage);

        // Then
        assertNull(responseDto.getMessage());
    }
}
