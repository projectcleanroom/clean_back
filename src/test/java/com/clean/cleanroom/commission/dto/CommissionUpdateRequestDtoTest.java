package com.clean.cleanroom.commission.dto;

import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommissionUpdateRequestDtoTest {

    @Test
    void testConstructorAndGetters() {
        // Given
        String image = "testImage.jpg";
        int size = 100;
        HouseType houseType = HouseType.APT;  // 사용할 enum 값
        CleanType cleanType = CleanType.NORMAL;  // 사용할 enum 값
        LocalDateTime desiredDate = LocalDateTime.of(2024, 8, 1, 10, 0);
        String significant = "Test significant note";

        // When
        CommissionUpdateRequestDto requestDto = new CommissionUpdateRequestDto(image, size, houseType, cleanType, desiredDate, significant);

        // Then
        assertEquals(image, requestDto.getImage());
        assertEquals(size, requestDto.getSize());
        assertEquals(houseType, requestDto.getHouseType());
        assertEquals(cleanType, requestDto.getCleanType());
        assertEquals(desiredDate, requestDto.getDesiredDate());
        assertEquals(significant, requestDto.getSignificant());
    }

    @Test
    void testConstructorWithNullValues() {
        // Given
        String image = null;
        int size = 0;
        HouseType houseType = null;
        CleanType cleanType = null;
        LocalDateTime desiredDate = null;
        String significant = null;

        // When
        CommissionUpdateRequestDto requestDto = new CommissionUpdateRequestDto(image, size, houseType, cleanType, desiredDate, significant);

        // Then
        assertNull(requestDto.getImage());
        assertEquals(0, requestDto.getSize());
        assertNull(requestDto.getHouseType());
        assertNull(requestDto.getCleanType());
        assertNull(requestDto.getDesiredDate());
        assertNull(requestDto.getSignificant());
    }
}
