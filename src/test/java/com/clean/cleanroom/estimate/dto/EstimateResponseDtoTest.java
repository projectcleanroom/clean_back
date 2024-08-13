package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.estimate.entity.Estimate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstimateResponseDtoTest {

    @Test
    void testEstimateResponseDtoConstructor() {
        // given: 모의 Estimate 객체를 생성
        Estimate estimate = new Estimate();

        // when: EstimateResponseDto 객체를 생성
        EstimateResponseDto estimateResponseDto = new EstimateResponseDto(estimate);

        // then: 필드 값이 예상대로 설정되었는지 확인
        assertTrue(estimateResponseDto.isApprove()); // approve 필드가 true인지 확인
        assertEquals("견적이 승인 되었습니다.", estimateResponseDto.getMessage()); // message 필드가 예상 메시지와 일치하는지 확인
    }

    @Test
    void testEstimateResponseDtoNoArgsConstructor() {
        // when: 기본 생성자를 사용하여 EstimateResponseDto 객체를 생성
        EstimateResponseDto estimateResponseDto = new EstimateResponseDto();

        // then: 기본 필드 값이 예상대로 설정되었는지 확인
        assertFalse(estimateResponseDto.isApprove()); // approve 필드의 기본값이 false일 것으로 기대
        assertNull(estimateResponseDto.getMessage()); // message 필드의 기본값이 null일 것으로 기대
    }
}
