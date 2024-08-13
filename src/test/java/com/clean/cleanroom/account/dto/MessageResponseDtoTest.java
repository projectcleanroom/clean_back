package com.clean.cleanroom.account.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageResponseDtoTest {

    @Test
    void testConstructorAndGetters() {
        // Given
        String testMessage = "Test message";

        // When
        MessageResponseDto responseDto = new MessageResponseDto(testMessage);

        // Then
        assertEquals(testMessage, responseDto.getMessage());
    }
}
