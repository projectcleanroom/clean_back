package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.enums.StatusType;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.members.entity.Address;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommissionConfirmDetailResponseDtoTest {

    @Test
    void testCommissionConfirmDetailResponseDtoConstructor() {

        // given
        Long commissionId = 1L;
        int size = 50;
        HouseType houseType = HouseType.APT;
        CleanType cleanType = CleanType.NORMAL;
        Long addressId = 2L;
        LocalDateTime desiredDate = LocalDateTime.of(2024, 8, 1, 10, 0);
        String significant = "Test Significant";
        StatusType status = StatusType.CHECK;
        StatusType estimatedStatus = StatusType.CHECK;
        int tmpPrice = 1000;
        int price = 1500;
        String statement = "Test Statement";
        LocalDateTime fixedDate = LocalDateTime.of(2024, 8, 15, 14, 0);
        String image = "image.png";

        Commission commission = mock(Commission.class);
        Estimate estimate = mock(Estimate.class);
        Address address = mock(Address.class);

        when(commission.getId()).thenReturn(commissionId);
        when(commission.getSize()).thenReturn(size);
        when(commission.getHouseType()).thenReturn(houseType);
        when(commission.getCleanType()).thenReturn(cleanType);
        when(commission.getAddress()).thenReturn(address);
        when(address.getId()).thenReturn(addressId);
        when(commission.getDesiredDate()).thenReturn(desiredDate);  // 여기서 desiredDate를 올바르게 반환하도록 수정
        when(commission.getSignificant()).thenReturn(significant);
        when(commission.getStatus()).thenReturn(status);
        when(estimate.getStatus()).thenReturn(estimatedStatus);
        when(estimate.getTmpPrice()).thenReturn(tmpPrice);
        when(estimate.getPrice()).thenReturn(price);
        when(estimate.getStatement()).thenReturn(statement);
        when(estimate.getFixedDate()).thenReturn(fixedDate);
        when(commission.getImage()).thenReturn(image);

        // when
        CommissionConfirmDetailResponseDto responseDto = new CommissionConfirmDetailResponseDto(commission, estimate, address);

        // then: 필드 값이 예상대로 설정되었는지 확인
        assertEquals(commissionId, responseDto.getCommissionId());
        assertEquals(size, responseDto.getSize());
        assertEquals(houseType, responseDto.getHouseType());
        assertEquals(cleanType, responseDto.getCleanType());
        assertEquals(addressId, responseDto.getAddressId());
        assertEquals(desiredDate, responseDto.getDesiredDate());  // desiredDate 검증 추가
        assertEquals(significant, responseDto.getSignificant());
        assertEquals(status, responseDto.getStatus());
        assertEquals(estimatedStatus, responseDto.getEstimatedStatus());
        assertEquals(tmpPrice, responseDto.getTmpPrice());
        assertEquals(price, responseDto.getPrice());
        assertEquals(statement, responseDto.getStatment());
        assertEquals(fixedDate, responseDto.getFixedDate());
        assertEquals(image, responseDto.getImage());
    }
}
