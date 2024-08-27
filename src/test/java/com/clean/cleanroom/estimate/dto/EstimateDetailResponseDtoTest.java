package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.enums.StatusType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EstimateDetailResponseDtoTest {

    @Test
    void testEstimateDetailResponseDtoConstructor() {
        // Given
        Long estimateId = 1L;
        int price = 5000;
        LocalDateTime fixedDate = LocalDateTime.now();
        String statement = "Test Statement";
        StatusType status = StatusType.CHECK;

        // Estimate 객체 모킹
        Estimate estimate = mock(Estimate.class);
        when(estimate.getId()).thenReturn(estimateId);
        when(estimate.getPrice()).thenReturn(price);
        when(estimate.getFixedDate()).thenReturn(fixedDate);
        when(estimate.getStatement()).thenReturn(statement);
        when(estimate.getStatus()).thenReturn(status);

        // When
        EstimateDetailResponseDto estimateDetailResponseDto = new EstimateDetailResponseDto(estimate);

        // Then
        assertEquals(estimateId, estimateDetailResponseDto.getId());
        assertEquals(price, estimateDetailResponseDto.getPrice());
        assertEquals(fixedDate, estimateDetailResponseDto.getFixedDate());
        assertEquals(statement, estimateDetailResponseDto.getStatement());
        assertEquals(status, estimateDetailResponseDto.getStatus());
    }

    @Test
    void testEstimateDetailResponseDtoNoArgsConstructor() {
        // When
        EstimateDetailResponseDto estimateDetailResponseDto = new EstimateDetailResponseDto();

        // Then
        assertNull(estimateDetailResponseDto.getId());
        assertEquals(0, estimateDetailResponseDto.getPrice());
        assertNull(estimateDetailResponseDto.getFixedDate());
        assertNull(estimateDetailResponseDto.getStatement());
        assertNull(estimateDetailResponseDto.getStatus());
    }
}
