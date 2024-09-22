package com.clean.cleanroom.commission.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommissionCreateResponseDtoTest {

    @Test
    void testCommissionCreateResponseDtoConstructor_Success() {

        // When
        CommissionCreateResponseDto responseDto = new CommissionCreateResponseDto();

        // Then
        assertEquals("청소의뢰 생성 완료", responseDto.getMessage());
    }
}
