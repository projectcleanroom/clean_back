package com.clean.cleanroom.estimate.entity;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.partner.entity.Partner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EstimateTest {

    @Mock
    private Commission commission; // 모킹된 Commission 객체

    @Mock
    private Partner partner; // 모킹된 Partner 객체

    private Estimate estimate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // 모킹 객체 초기화

        // 모든 필드를 초기화하는 생성자를 사용하여 Estimate 객체 생성
        estimate = new Estimate(null, commission, partner, 10000, LocalDateTime.now(), "Test Statement", false);
    }

    @Test
    void testEstimateFields() {
        // given
        when(commission.getId()).thenReturn(2L);
        when(partner.getId()).thenReturn(3L);

        // then
        assertEquals(commission, estimate.getCommissionId()); // Commission 객체 검증
        assertEquals(partner, estimate.getPartnerId()); // Partner 객체 검증
        assertEquals(10000, estimate.getPrice()); // Price 검증
        assertNotNull(estimate.getFixedDate()); // FixedDate 검증
        assertEquals("Test Statement", estimate.getStatement()); // Statement 검증
        assertFalse(estimate.isApproved()); // 초기 승인 상태는 false여야 함
    }

    @Test
    void testApproveMethod() {
        // given
        assertFalse(estimate.isApproved()); // 초기 승인 상태가 false인지 확인

        // when
        estimate.approve(); // 승인 메서드 호출

        // then
        assertTrue(estimate.isApproved()); // 승인 상태가 true로 변경되었는지 확인
    }
}
