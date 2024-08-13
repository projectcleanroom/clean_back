package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.members.entity.Address;
import com.clean.cleanroom.members.entity.Members;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommissionUpdateResponseDtoTest {

    @Test
    void testCommissionUpdateResponseDtoConstructor() {
        // given: 모의 Commission, Members, Address 객체를 생성 및 설정
        Long commissionId = 1L;
        String memberNick = "TestNick";
        int size = 50;
        HouseType houseType = HouseType.APT;  // HouseType.APT를 사용
        CleanType cleanType = CleanType.NORMAL;  // CleanType.NORMAL를 사용
        Long addressId = 2L;
        LocalDateTime desiredDate = LocalDateTime.of(2024, 8, 1, 10, 0);
        String significant = "Test Significant";

        Members members = mock(Members.class);
        Address address = mock(Address.class);
        Commission commission = mock(Commission.class);

        when(commission.getId()).thenReturn(commissionId);
        when(commission.getMembers()).thenReturn(members);
        when(members.getNick()).thenReturn(memberNick);
        when(commission.getSize()).thenReturn(size);
        when(commission.getHouseType()).thenReturn(houseType);
        when(commission.getCleanType()).thenReturn(cleanType);
        when(commission.getAddress()).thenReturn(address);
        when(address.getId()).thenReturn(addressId);
        when(commission.getDesiredDate()).thenReturn(desiredDate);
        when(commission.getSignificant()).thenReturn(significant);

        // when: CommissionUpdateResponseDto 객체를 생성
        CommissionUpdateResponseDto responseDto = new CommissionUpdateResponseDto(commission);

        // then: 필드 값이 예상대로 설정되었는지 확인
        assertEquals(commissionId, responseDto.getCommissionId());
        assertEquals(memberNick, responseDto.getMemberNick());
        assertEquals(size, responseDto.getSize());
        assertEquals(houseType, responseDto.getHouseType());
        assertEquals(cleanType, responseDto.getCleanType());
        assertEquals(addressId, responseDto.getAddressId());
        assertEquals(desiredDate, responseDto.getDesiredDate());
        assertEquals(significant, responseDto.getSignificant());
    }
}
