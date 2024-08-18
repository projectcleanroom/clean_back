package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.enums.StatusType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommissionUpdateRequestDtoTest {

    @Test
    void testCommissionUpdateRequestDtoConstructor() {

        // given: 테스트할 필드 값들을 준비
        String image = "updatedImage.jpg";
        int size = 120;
        HouseType houseType = HouseType.HOUSE;
        CleanType cleanType = CleanType.NORMAL;
        LocalDateTime desiredDate = LocalDateTime.of(2024, 9, 1, 10, 0);
        String significant = "Updated significant";
        StatusType status = StatusType.CHECK;

        // when: CommissionUpdateRequestDto 객체 생성
        CommissionUpdateRequestDto requestDto = new CommissionUpdateRequestDto(image, size, houseType, cleanType, desiredDate, significant, status);

        // then: 필드 값이 예상대로 설정되었는지 확인
        assertEquals(image, requestDto.getImage());
        assertEquals(size, requestDto.getSize());
        assertEquals(houseType, requestDto.getHouseType());
        assertEquals(cleanType, requestDto.getCleanType());
        assertEquals(desiredDate, requestDto.getDesiredDate());
        assertEquals(significant, requestDto.getSignificant());
        assertEquals(status, requestDto.getStatus());
    }

    @Test
    void testCommissionUpdateRequestDtoWithNullValues() {

        // given: 필드 값을 null로 설정
        String image = null;
        int size = 0;
        HouseType houseType = null;
        CleanType cleanType = null;
        LocalDateTime desiredDate = null;
        String significant = null;
        StatusType status = null;

        // when: CommissionUpdateRequestDto 객체 생성
        CommissionUpdateRequestDto requestDto = new CommissionUpdateRequestDto(image, size, houseType, cleanType, desiredDate, significant, status);

        // then: 필드 값이 예상대로 null 또는 기본값으로 설정되었는지 확인
        assertNull(requestDto.getImage());
        assertEquals(0, requestDto.getSize());
        assertNull(requestDto.getHouseType());
        assertNull(requestDto.getCleanType());
        assertNull(requestDto.getDesiredDate());
        assertNull(requestDto.getSignificant());
        assertNull(requestDto.getStatus());
    }
}
