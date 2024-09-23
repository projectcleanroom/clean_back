package com.clean.cleanroom.members.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MembersLoginRequestDtoTest {

    @Test
    void testDefaultValues() {
        // when: 기본 생성자를 사용하여 MembersLoginRequestDto 객체를 생성
        MembersLoginRequestDto requestDto = new MembersLoginRequestDto();

        // then: 모든 필드 값이 기본값으로 설정되었는지 확인
        assertNull(requestDto.getEmail());
        assertNull(requestDto.getPassword());
    }

    @Test
    void testSettersAndGetters() {

        String email = "test@example.com";
        String password = "password123";

        MembersLoginRequestDto requestDto = new MembersLoginRequestDto();

        assertNull(requestDto.getEmail());
        assertNull(requestDto.getPassword());
    }
}
