package com.clean.cleanroom.commission.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommissionCancelResponseDtoTest {

    @Test
    void testCommissionCancelResponseDtoConstructor_Success() {

        // When
        CommissionCancelResponseDto responseDto = new CommissionCancelResponseDto();

        // Then
        assertEquals("청소의뢰 취소 완료", responseDto.getMessage());
    }
}
