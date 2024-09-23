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

        // given
        String image = "updatedImage.jpg";
        int size = 120;
        HouseType houseType = HouseType.HOUSE;
        CleanType cleanType = CleanType.NORMAL;
        LocalDateTime desiredDate = LocalDateTime.of(2024, 9, 1, 10, 0);
        String significant = "Updated significant";
        StatusType status = StatusType.CHECK;

        // when
        CommissionUpdateRequestDto requestDto = new CommissionUpdateRequestDto(image, size, houseType, cleanType, desiredDate, significant, status);

        // then
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

        // given
        String image = null;
        int size = 0;
        HouseType houseType = null;
        CleanType cleanType = null;
        LocalDateTime desiredDate = null;
        String significant = null;
        StatusType status = null;

        // when
        CommissionUpdateRequestDto requestDto = new CommissionUpdateRequestDto(image, size, houseType, cleanType, desiredDate, significant, status);

        // then
        assertNull(requestDto.getImage());
        assertEquals(0, requestDto.getSize());
        assertNull(requestDto.getHouseType());
        assertNull(requestDto.getCleanType());
        assertNull(requestDto.getDesiredDate());
        assertNull(requestDto.getSignificant());
        assertNull(requestDto.getStatus());
    }
}
