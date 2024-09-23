package com.clean.cleanroom.members.entity;

import com.clean.cleanroom.members.dto.MembersAddressRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AddressTest {

    @Mock
    private MembersAddressRequestDto requestDto; // 모킹된 MembersAddressRequestDto 객체

    private Address address;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // 모킹 객체 초기화

        // Given: 필요한 모킹 설정
        when(requestDto.getAddress()).thenReturn("123 Main St");
        when(requestDto.getAddressDetail()).thenReturn("Apt 4B");
        when(requestDto.getAddressCode()).thenReturn("12345");

        // When: 생성자를 통해 Address 객체 생성
        address = new Address("test@example.com", requestDto);
    }

    @Test
    void testAddressFields() {
        // Then: Address 객체가 올바르게 초기화되었는지 검증
        assertEquals("test@example.com", address.getEmail());
        assertEquals("123 Main St", address.getAddress());
        assertEquals("Apt 4B", address.getAddressDetail());
        assertEquals("12345", address.getAddressCode());
    }

    @Test
    void testUpdateMethod() {
        // Given: 새로운 MembersAddressRequestDto 모킹 객체
        MembersAddressRequestDto newRequestDto = mock(MembersAddressRequestDto.class);
        when(newRequestDto.getAddress()).thenReturn("456 Elm St");
        when(newRequestDto.getAddressDetail()).thenReturn("Suite 1A");
        when(newRequestDto.getAddressCode()).thenReturn("67890");

        // When: update 메서드를 호출하여 Address 객체를 업데이트
        address.address("new@example.com", newRequestDto);

        // Then: 필드가 예상대로 업데이트되었는지 검증
        assertEquals("new@example.com", address.getEmail());
        assertEquals("456 Elm St", address.getAddress());
        assertEquals("Suite 1A", address.getAddressDetail());
        assertEquals("67890", address.getAddressCode());
    }
}
