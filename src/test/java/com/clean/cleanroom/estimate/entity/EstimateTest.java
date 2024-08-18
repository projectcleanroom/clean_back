package com.clean.cleanroom.estimate.entity;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.StatusType;
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

        MockitoAnnotations.openMocks(this);

        when(commission.getId()).thenReturn(2L);
        when(partner.getId()).thenReturn(3L);

        estimate = new Estimate(
                null,
                commission,
                partner,
                10000,
                8000,
                LocalDateTime.now(),
                "Test Statement",
                false,
                StatusType.CHECK);
    }

    @Test
    void testEstimateFields() {

        assertEquals(2L, estimate.getCommission().getId());
        assertEquals(3L, estimate.getPartner().getId());
        assertEquals(10000, estimate.getPrice());
        assertEquals(8000, estimate.getTmpPrice());
        assertNotNull(estimate.getFixedDate());
        assertEquals("Test Statement", estimate.getStatement());
        assertFalse(estimate.isApproved());
        assertEquals(StatusType.CHECK, estimate.getStatus());
    }

    @Test
    void testApproveMethod() {

        assertFalse(estimate.isApproved());

        estimate.approve();

        assertTrue(estimate.isApproved());
    }
}
