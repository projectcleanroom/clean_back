package com.clean.cleanroom.members.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MembersAddressDelResponseDtoTest {

    @Test
    void testDefaultConstructor() {
        // when: 기본 생성자를 사용하여 MembersAddressDelResponseDto 객체를 생성
        MembersAddressDelResponseDto responseDto = new MembersAddressDelResponseDto();

        // then: 기본 메시지가 예상대로 설정되었는지 확인
        assertNotNull(responseDto);
        assertEquals("삭제가 완료 되었습니다", responseDto.getMsg());
    }
}
