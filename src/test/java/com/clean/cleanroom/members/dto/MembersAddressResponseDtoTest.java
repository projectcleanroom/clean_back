package com.clean.cleanroom.members.dto;

import com.clean.cleanroom.members.entity.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MembersAddressResponseDtoTest {

    @Test
    void testConstructorWithValidAddress() {
        // given: 유효한 Address 객체를 생성하고 설정
        String addressValue = "123 Main St";
        String addressDetailValue = "Apt 4B";
        String addressCodeValue = "12345";

        Address address = mock(Address.class);
        when(address.getAddress()).thenReturn(addressValue);
        when(address.getAddressDetail()).thenReturn(addressDetailValue);
        when(address.getAddressCode()).thenReturn(addressCodeValue);

        // when: MembersAddressResponseDto 객체를 생성
        MembersAddressResponseDto responseDto = new MembersAddressResponseDto(address);

        // then: 필드 값이 예상대로 설정되었는지 확인
        assertEquals(addressValue, responseDto.getAddress());
        assertEquals(addressDetailValue, responseDto.getAddressDetail());
        assertEquals(addressCodeValue, responseDto.getAddressCode());
    }
}
