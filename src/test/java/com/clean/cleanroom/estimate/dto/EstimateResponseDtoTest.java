package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.partner.entity.Partner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EstimateResponseDtoTest {

    @Mock
    private Estimate estimate;

    @Mock
    private Partner partner;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(partner.getId()).thenReturn(3L);
        when(partner.getCompanyName()).thenReturn("Test Company");


        when(estimate.getId()).thenReturn(1L);
        when(estimate.getPrice()).thenReturn(10000);
        when(estimate.getFixedDate()).thenReturn(LocalDateTime.of(2024, 8, 1, 10, 0));
        when(estimate.getStatement()).thenReturn("Test Statement");
        when(estimate.isApproved()).thenReturn(false);
        when(estimate.getPartner()).thenReturn(partner);
    }

    @Test
    void testEstimateResponseDtoConstructor() {

        // Given
        EstimateResponseDto estimateResponseDto = new EstimateResponseDto(estimate);

        // Then
        assertEquals(1L, estimateResponseDto.getId());
        assertEquals(10000, estimateResponseDto.getPrice());
        assertEquals(LocalDateTime.of(2024, 8, 1, 10, 0), estimateResponseDto.getFixedDate());
        assertEquals("Test Statement", estimateResponseDto.getStatement());
        assertFalse(estimateResponseDto.isApproved());
        assertEquals(3L, estimateResponseDto.getPartnerId());
        assertEquals("Test Company", estimateResponseDto.getPartnerName());
    }

    @Test
    void testEstimateResponseDtoNoArgsConstructor() {

        // When
        EstimateResponseDto estimateResponseDto = new EstimateResponseDto();

        // Then
        assertNull(estimateResponseDto.getId());
        assertEquals(0, estimateResponseDto.getPrice());
        assertNull(estimateResponseDto.getFixedDate());
        assertNull(estimateResponseDto.getStatement());
        assertFalse(estimateResponseDto.isApproved());
        assertNull(estimateResponseDto.getPartnerId());
        assertNull(estimateResponseDto.getPartnerName());
    }
}
