package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.estimate.entity.Estimate;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EstimateListResponseDtoTest {

    @Test
    void testEstimateListResponseDtoConstructor() {
        // given
        Long estimateId = 1L;
        Long commissionId = 2L;
        int price = 5000;
        LocalDateTime fixedDate = LocalDateTime.now();
        String statement = "Test Statement";

        // Commission 객체 모킹
        Commission commission = mock(Commission.class);
        when(commission.getId()).thenReturn(commissionId);

        // Estimate 객체 모킹
        Estimate estimate = mock(Estimate.class);
        when(estimate.getId()).thenReturn(estimateId);
        when(estimate.getCommission()).thenReturn(commission);
        when(estimate.getPrice()).thenReturn(price);
        when(estimate.getFixedDate()).thenReturn(fixedDate);
        when(estimate.getStatement()).thenReturn(statement);

        // when
        EstimateListResponseDto estimateListResponseDto = new EstimateListResponseDto(estimate);

        // then
        assertEquals(estimateId, estimateListResponseDto.getId());
        assertEquals(commissionId, estimateListResponseDto.getCommissionId());
        assertEquals(price, estimateListResponseDto.getPrice());
        assertEquals(fixedDate, estimateListResponseDto.getFixedDate());
        assertEquals(statement, estimateListResponseDto.getStatement());
    }

    @Test
    void testEstimateListResponseDtoNoArgsConstructor() {
        // when
        EstimateListResponseDto estimateListResponseDto = new EstimateListResponseDto();

        // then
        assertNull(estimateListResponseDto.getId());
        assertNull(estimateListResponseDto.getCommissionId());
        assertEquals(0, estimateListResponseDto.getPrice());
        assertNull(estimateListResponseDto.getFixedDate());
        assertNull(estimateListResponseDto.getStatement());
    }
}
