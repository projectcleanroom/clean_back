package com.clean.cleanroom.estimate.entity;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.partner.entity.Partner;
import com.clean.cleanroom.payment.entity.PaymentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EstimateTest {

    @Mock
    private Commission commission; // 모킹된 Commission 객체

    @Mock
    private Partner partner; // 모킹된 Partner 객체

    @Mock
    private Members member; // 모킹된 Members 객체

    @Mock
    private PaymentEntity payment1; // 모킹된 PaymentEntity 객체
    @Mock
    private PaymentEntity payment2; // 모킹된 PaymentEntity 객체

    private Estimate estimate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(commission.getId()).thenReturn(2L);
        when(partner.getId()).thenReturn(3L);
        when(member.getId()).thenReturn(4L);

        estimate = new Estimate(
                1L,
                List.of(payment1, payment2), // 결제 목록
                member, // 멤버 객체
                commission,
                partner,
                10000,
                8000,
                LocalDateTime.now(),
                "Test Statement",
                false,
                StatusType.CHECK
        );
    }

    @Test
    void testEstimateFields() {
        // Given & When: 필드 값 검증
        assertEquals(1L, estimate.getId());
        assertEquals(2L, estimate.getCommission().getId());
        assertEquals(3L, estimate.getPartner().getId());
        assertEquals(4L, estimate.getMember().getId());
        assertEquals(10000, estimate.getPrice());
        assertEquals(8000, estimate.getTmpPrice());
        assertNotNull(estimate.getFixedDate());
        assertEquals("Test Statement", estimate.getStatement());
        assertFalse(estimate.isApproved());
        assertEquals(StatusType.CHECK, estimate.getStatus());

        // PaymentEntity 관련 검증
        assertEquals(2, estimate.getPayments().size());
        assertTrue(estimate.getPayments().contains(payment1));
        assertTrue(estimate.getPayments().contains(payment2));
    }

    @Test
    void testApproveMethod() {
        // Given: 초기 승인 상태 확인
        assertFalse(estimate.isApproved());

        // When: 승인 메서드 호출
        estimate.approve();

        // Then: 승인 상태가 true로 변경되었는지 확인
        assertTrue(estimate.isApproved());
    }
}
