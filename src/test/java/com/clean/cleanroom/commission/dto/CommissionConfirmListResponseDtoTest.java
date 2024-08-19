package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.estimate.dto.EstimateResponseDto;
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
        Long id = 1L;
        int size = 100;
        HouseType houseType = HouseType.APT;
        CleanType cleanType = CleanType.NORMAL;
        LocalDateTime desiredDate = LocalDateTime.of(2024, 8, 1, 10, 0);
        String significant = "Test Significant";
        String image = "testImage.jpg";
        StatusType statusType = StatusType.CHECK; // 새로운 필드 statusType 추가
        EstimateResponseDto estimateResponseDto = mock(EstimateResponseDto.class);

        List<EstimateResponseDto> estimates = List.of(estimateResponseDto);

        // When
        CommissionConfirmListResponseDto responseDto = new CommissionConfirmListResponseDto(
                id, size, houseType, cleanType, desiredDate, significant, image, statusType, estimates // statusType 추가
        );

        // Then: 필드 값이 예상대로 설정되었는지 확인
        assertEquals(id, responseDto.getId());
        assertEquals(size, responseDto.getSize());
        assertEquals(houseType, responseDto.getHouseType());
        assertEquals(cleanType, responseDto.getCleanType());
        assertEquals(desiredDate, responseDto.getDesiredDate());
        assertEquals(significant, responseDto.getSignificant());
        assertEquals(image, responseDto.getImage());
        assertEquals(statusType, responseDto.getStatusType()); // statusType 검증 추가
        assertEquals(estimates, responseDto.getEstimates());
    }

    @Test
    void testCommissionConfirmListResponseDtoConstructorWithNullValues() {
        // Given
        Long id = null;
        int size = 0;
        HouseType houseType = null;
        CleanType cleanType = null;
        LocalDateTime desiredDate = null;
        String significant = null;
        String image = null;
        StatusType statusType = null; // statusType null 값 테스트 추가
        List<EstimateResponseDto> estimates = new ArrayList<>();

        // When
        CommissionConfirmListResponseDto responseDto = new CommissionConfirmListResponseDto(
                id, size, houseType, cleanType, desiredDate, significant, image, statusType, estimates // statusType 추가
        );

        // Then: 필드 값이 예상대로 설정되었는지 확인
        assertNull(responseDto.getId());
        assertEquals(0, responseDto.getSize());
        assertNull(responseDto.getHouseType());
        assertNull(responseDto.getCleanType());
        assertNull(responseDto.getDesiredDate());
        assertNull(responseDto.getSignificant());
        assertNull(responseDto.getImage());
        assertNull(responseDto.getStatusType()); // statusType 검증 추가
        assertEquals(estimates, responseDto.getEstimates());
    }

}
