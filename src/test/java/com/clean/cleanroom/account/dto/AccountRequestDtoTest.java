package com.clean.cleanroom.account.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountRequestDtoTest {

    @Test
    void testDefaultConstructorAndGetters() {
        // Given
        AccountRequestDto requestDto = new AccountRequestDto();

        // Then
        assertNull(requestDto.getAccountNumber());
        assertNull(requestDto.getBank());
    }
}
