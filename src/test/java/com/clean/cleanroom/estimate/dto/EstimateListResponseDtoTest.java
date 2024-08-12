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
        // given: 모의 Estimate와 Commission 객체를 생성 및 설정
        Long estimateId = 1L;
        Long commissionId = 2L;
        int price = 5000;
        LocalDateTime fixedDate = LocalDateTime.now();
        String statement = "Test Statement";

        Commission commission = mock(Commission.class);
        when(commission.getId()).thenReturn(commissionId);

        Estimate estimate = mock(Estimate.class);
        when(estimate.getId()).thenReturn(estimateId);
        when(estimate.getCommissionId()).thenReturn(commission);
        when(estimate.getPrice()).thenReturn(price);
        when(estimate.getFixedDate()).thenReturn(fixedDate);
        when(estimate.getStatement()).thenReturn(statement);

        // when: EstimateListResponseDto 객체를 생성
        EstimateListResponseDto estimateListResponseDto = new EstimateListResponseDto(estimate);

        // then: 필드 값이 예상대로 설정되었는지 확인
        assertEquals(estimateId, estimateListResponseDto.getId());
        assertEquals(commissionId, estimateListResponseDto.getCommissionId());
        assertEquals(price, estimateListResponseDto.getPrice());
        assertEquals(fixedDate, estimateListResponseDto.getFixedDate());
        assertEquals(statement, estimateListResponseDto.getStatement());
    }

    @Test
    void testEstimateListResponseDtoNoArgsConstructor() {
        // when: 기본 생성자를 사용하여 EstimateListResponseDto 객체를 생성
        EstimateListResponseDto estimateListResponseDto = new EstimateListResponseDto();

        // then: 기본 필드 값이 예상대로 설정되었는지 확인
        assertNull(estimateListResponseDto.getId());
        assertNull(estimateListResponseDto.getCommissionId());
        assertEquals(0, estimateListResponseDto.getPrice());
        assertNull(estimateListResponseDto.getFixedDate());
        assertNull(estimateListResponseDto.getStatement());
    }
}
