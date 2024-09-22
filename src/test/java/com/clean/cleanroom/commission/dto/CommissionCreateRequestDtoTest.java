package com.clean.cleanroom.commission.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommissionCreateRequestDtoTest {

    @Test
    void testDefaultConstructorAndGetters() {
        // Given
        CommissionCreateRequestDto requestDto = new CommissionCreateRequestDto();

        // Then
        assertNull(requestDto.getImage());
        assertEquals(0, requestDto.getSize());
        assertNull(requestDto.getHouseType());
        assertNull(requestDto.getCleanType());
        assertNull(requestDto.getAddressId());
        assertNull(requestDto.getDesiredDate());
        assertNull(requestDto.getSignificant());
    }
}
