package com.clean.cleanroom.estimate.dto;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.partner.entity.Partner;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EstimateDetailResponseDtoTest {

    @Test
    void testEstimateDetailResponseDtoConstructor() {

        // Given
        Long estimateId = 1L;
        Long commissionId = 2L;
        Long partnerId = 3L;
        int price = 10000;
        LocalDateTime fixedDate = LocalDateTime.now();
        String statement = "Test Statement";
        StatusType estimateStatus = StatusType.CHECK;
        CleanType cleanType = CleanType.NORMAL;
        int size = 50;
        LocalDateTime desiredDate = LocalDateTime.now().plusDays(7);
        String significant = "Test Significant";
        StatusType commissionStatus = StatusType.CHECK;
        String partnerPhoneNumber = "010-1234-5678";
        String managerName = "Manager Kim";
        String companyName = "Clean Corp";

        Estimate estimate = mock(Estimate.class);
        Commission commission = mock(Commission.class);
        Partner partner = mock(Partner.class);

        when(estimate.getId()).thenReturn(estimateId);
        when(estimate.getPrice()).thenReturn(price);
        when(estimate.getFixedDate()).thenReturn(fixedDate);
        when(estimate.getStatement()).thenReturn(statement);
        when(estimate.getStatus()).thenReturn(estimateStatus);

        when(commission.getId()).thenReturn(commissionId);
        when(commission.getCleanType()).thenReturn(cleanType);
        when(commission.getSize()).thenReturn(size);
        when(commission.getDesiredDate()).thenReturn(desiredDate);
        when(commission.getSignificant()).thenReturn(significant);
        when(commission.getStatus()).thenReturn(commissionStatus);

        when(partner.getId()).thenReturn(partnerId);
        when(partner.getPhoneNumber()).thenReturn(partnerPhoneNumber);
        when(partner.getManagerName()).thenReturn(managerName);
        when(partner.getCompanyName()).thenReturn(companyName);

        // When
        EstimateDetailResponseDto dto = new EstimateDetailResponseDto(estimate, commission, partner);

        // Then
        assertEquals(estimateId, dto.getId());
        assertEquals(commissionId, dto.getCommissionId());
        assertEquals(partnerId, dto.getPartnerId());
        assertEquals(price, dto.getPrice());
        assertEquals(fixedDate, dto.getFixedDate());
        assertEquals(statement, dto.getStatement());
        assertEquals(estimateStatus, dto.getStatus());
        assertEquals(cleanType, dto.getCleanType());
        assertEquals(size, dto.getSize());
        assertEquals(desiredDate, dto.getDesiredDate());
        assertEquals(significant, dto.getSignificant());
        assertEquals(commissionStatus, dto.getCommissionStatus());
        assertEquals(partnerPhoneNumber, dto.getPhoneNumber());
        assertEquals(managerName, dto.getManagerName());
        assertEquals(companyName, dto.getCompanyName());
    }


    @Test
    void testEstimateDetailResponseDtoNoArgsConstructor() {
        // When
        EstimateDetailResponseDto dto = new EstimateDetailResponseDto();

        // Then
        assertNull(dto.getId());
        assertNull(dto.getCommissionId());
        assertNull(dto.getPartnerId());
        assertEquals(0, dto.getPrice());
        assertNull(dto.getFixedDate());
        assertNull(dto.getStatement());
        assertNull(dto.getStatus());
        assertNull(dto.getCleanType());
        assertEquals(0, dto.getSize());
        assertNull(dto.getDesiredDate());
        assertNull(dto.getSignificant());
        assertNull(dto.getCommissionStatus());
        assertNull(dto.getPhoneNumber());
        assertNull(dto.getManagerName());
        assertNull(dto.getCompanyName());
    }
}
