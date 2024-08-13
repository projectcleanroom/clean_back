//package com.clean.cleanroom.estimate.entity;
//
//import com.clean.cleanroom.commission.entity.Commission;
//import com.clean.cleanroom.partner.entity.Partner;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class EstimateTest {
//
//    @Mock
//    private Commission commission; // 모킹된 Commission 객체
//
//    @Mock
//    private Partner partner; // 모킹된 Partner 객체
//
//    private Estimate estimate;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this); // 모킹 객체 초기화
//
//        // 연관관계를 포함하여 모든 필드를 초기화하는 생성자를 사용하여 Estimate 객체 생성
//        estimate = new Estimate(null, commission, partner, 10000, LocalDateTime.now(), "Test Statement", false);
//    }
//
//    @Test
//    void testEstimateFields() {
//        // given
//        when(commission.getId()).thenReturn(2L);
//        when(partner.getId()).thenReturn(3L);
//
//        // 객체 자체와 ID를 분리하여 검증
//        assertEquals(commission, estimate.getCommission()); // Commission 객체 검증
//        assertEquals(2L, estimate.getCommission().getId()); // Commission ID 검증
//        assertEquals(partner, estimate.getPartner()); // Partner 객체 검증
//        assertEquals(3L, estimate.getPartner().getId()); // Partner ID 검증
//        assertEquals(10000, estimate.getPrice()); // Price 검증
//        assertNotNull(estimate.getFixedDate()); // FixedDate 검증
//        assertEquals("Test Statement", estimate.getStatement()); // Statement 검증
//        assertFalse(estimate.isApproved()); // 초기 승인 상태는 false여야 함
//    }
//
//    @Test
//    void testApproveMethod() {
//        // given
//        assertFalse(estimate.isApproved()); // 초기 승인 상태가 false인지 확인
//
//        // when
//        estimate.approve(); // 승인 메서드 호출
//
//        // then
//        assertTrue(estimate.isApproved()); // 승인 상태가 true로 변경되었는지 확인
//    }
//}


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

        // 모킹된 Commission 및 Partner 객체에 대한 설정
        when(commission.getId()).thenReturn(2L);
        when(partner.getId()).thenReturn(3L);

        // 모든 필드를 초기화하는 생성자를 사용하여 Estimate 객체 생성
        estimate = new Estimate(null, commission, partner, 10000, LocalDateTime.now(), "Test Statement", false);
    }

    @Test
    void testEstimateFields() {
        // Commission과 Partner 객체의 초기화가 올바르게 이루어졌는지 검증
        assertEquals(2L, estimate.getCommission().getId()); // Commission ID 검증
        assertEquals(3L, estimate.getPartner().getId()); // Partner ID 검증
        assertEquals(10000, estimate.getPrice()); // Price 검증
        assertNotNull(estimate.getFixedDate()); // FixedDate 검증
        assertEquals("Test Statement", estimate.getStatement()); // Statement 검증
        assertFalse(estimate.isApproved()); // 초기 승인 상태는 false여야 함
    }

    @Test
    void testApproveMethod() {
        // approve 메서드가 승인 상태를 변경하는지 검증
        assertFalse(estimate.isApproved()); // 초기 승인 상태가 false인지 확인

        estimate.approve(); // 승인 메서드 호출

        assertTrue(estimate.isApproved()); // 승인 상태가 true로 변경되었는지 확인
    }
}
