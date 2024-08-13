package com.clean.cleanroom.commission.entity;

import com.clean.cleanroom.commission.dto.CommissionCreateRequestDto;
import com.clean.cleanroom.commission.dto.CommissionUpdateRequestDto;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.members.entity.Address;
import com.clean.cleanroom.members.entity.Members;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommissionTest {

    @Mock
    private Members members; // 모킹된 Members 객체

    @Mock
    private Address address; // 모킹된 Address 객체

    private Commission commission;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // 모킹 객체 초기화

        // Given: CommissionCreateRequestDto와 필요한 모킹 객체들
        CommissionCreateRequestDto createRequestDto = mock(CommissionCreateRequestDto.class);
        when(createRequestDto.getImage()).thenReturn("testImage.jpg");
        when(createRequestDto.getSize()).thenReturn(100);
        when(createRequestDto.getHouseType()).thenReturn(HouseType.APT);
        when(createRequestDto.getCleanType()).thenReturn(CleanType.NORMAL);
        when(createRequestDto.getDesiredDate()).thenReturn(LocalDateTime.of(2024, 8, 1, 10, 0));
        when(createRequestDto.getSignificant()).thenReturn("Test significant");

        // When: 생성자를 통해 Commission 객체 생성
        commission = new Commission(members, address, createRequestDto);
    }

    @Test
    void testCommissionFields() {
        // Then: Commission 객체가 올바르게 초기화되었는지 검증
        assertEquals(members, commission.getMembers());
        assertEquals(address, commission.getAddress());
        assertEquals("testImage.jpg", commission.getImage());
        assertEquals(100, commission.getSize());
        assertEquals(HouseType.APT, commission.getHouseType());
        assertEquals(CleanType.NORMAL, commission.getCleanType());
        assertEquals(LocalDateTime.of(2024, 8, 1, 10, 0), commission.getDesiredDate());
        assertEquals("Test significant", commission.getSignificant());
    }

    @Test
    void testUpdateMethod() {
        // Given: CommissionUpdateRequestDto와 새로운 Address 모킹
        CommissionUpdateRequestDto updateRequestDto = mock(CommissionUpdateRequestDto.class);
        when(updateRequestDto.getImage()).thenReturn("updatedImage.jpg");
        when(updateRequestDto.getSize()).thenReturn(120);
        when(updateRequestDto.getHouseType()).thenReturn(HouseType.HOUSE);
        when(updateRequestDto.getCleanType()).thenReturn(CleanType.NORMAL);
        when(updateRequestDto.getDesiredDate()).thenReturn(LocalDateTime.of(2024, 9, 1, 10, 0));
        when(updateRequestDto.getSignificant()).thenReturn("Updated significant");

        Address newAddress = mock(Address.class);

        // When: update 메서드를 호출하여 Commission 객체를 업데이트
        commission.update(updateRequestDto, newAddress);

        // Then: 필드가 예상대로 업데이트되었는지 검증
        assertEquals(newAddress, commission.getAddress());
        assertEquals("updatedImage.jpg", commission.getImage());
        assertEquals(120, commission.getSize());
        assertEquals(HouseType.HOUSE, commission.getHouseType());
        assertEquals(CleanType.NORMAL, commission.getCleanType());
        assertEquals(LocalDateTime.of(2024, 9, 1, 10, 0), commission.getDesiredDate());
        assertEquals("Updated significant", commission.getSignificant());
    }
}
