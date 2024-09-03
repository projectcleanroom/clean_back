package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.estimate.dto.EstimateResponseDto;
import com.clean.cleanroom.members.entity.Address;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommissionConfirmListResponseDtoTest {

    @Test
    void testCommissionConfirmListResponseDtoConstructor() {

        // Given
        Commission commission = mock(Commission.class);
        Address address = mock(Address.class);
        when(commission.getId()).thenReturn(1L);
        when(commission.getSize()).thenReturn(100);
        when(commission.getHouseType()).thenReturn(HouseType.APT);
        when(commission.getCleanType()).thenReturn(CleanType.NORMAL);
        when(commission.getDesiredDate()).thenReturn(LocalDateTime.of(2024, 8, 1, 10, 0));
        when(commission.getSignificant()).thenReturn("Test Significant");
        when(commission.getImage()).thenReturn("testImage.jpg");
        when(commission.getStatus()).thenReturn(StatusType.CHECK);
        when(commission.getAddress()).thenReturn(address);
        when(address.getId()).thenReturn(2L);

        EstimateResponseDto estimateResponseDto = mock(EstimateResponseDto.class);
        List<EstimateResponseDto> estimates = List.of(estimateResponseDto);

        // When
        CommissionConfirmListResponseDto responseDto = new CommissionConfirmListResponseDto(commission, estimates);

        // Then: 필드 값이 예상대로 설정되었는지 확인
        assertEquals(1L, responseDto.getCommissionId());
        assertEquals(100, responseDto.getSize());
        assertEquals(HouseType.APT, responseDto.getHouseType());
        assertEquals(CleanType.NORMAL, responseDto.getCleanType());
        assertEquals(LocalDateTime.of(2024, 8, 1, 10, 0), responseDto.getDesiredDate());
        assertEquals("Test Significant", responseDto.getSignificant());
        assertEquals("testImage.jpg", responseDto.getImage());
        assertEquals(StatusType.CHECK, responseDto.getStatus());
        assertEquals(2L, responseDto.getAddressId());
        assertEquals(estimates, responseDto.getEstimates());
    }

    @Test
    void testCommissionConfirmListResponseDtoConstructorWithNullValues() {
        // Given
        Commission commission = mock(Commission.class);
        Address address = mock(Address.class);
        when(commission.getId()).thenReturn(null);
        when(commission.getSize()).thenReturn(0);
        when(commission.getHouseType()).thenReturn(null);
        when(commission.getCleanType()).thenReturn(null);
        when(commission.getDesiredDate()).thenReturn(null);
        when(commission.getSignificant()).thenReturn(null);
        when(commission.getImage()).thenReturn(null);
        when(commission.getStatus()).thenReturn(null);
        when(commission.getAddress()).thenReturn(address);
        when(address.getId()).thenReturn(null);

        List<EstimateResponseDto> estimates = new ArrayList<>();

        // When
        CommissionConfirmListResponseDto responseDto = new CommissionConfirmListResponseDto(commission, estimates);

        // Then
        assertNull(responseDto.getCommissionId());
        assertEquals(0, responseDto.getSize());
        assertNull(responseDto.getHouseType());
        assertNull(responseDto.getCleanType());
        assertNull(responseDto.getDesiredDate());
        assertNull(responseDto.getSignificant());
        assertNull(responseDto.getImage());
        assertNull(responseDto.getStatus());
        assertNull(responseDto.getAddressId());
        assertEquals(estimates, responseDto.getEstimates());
    }

}
