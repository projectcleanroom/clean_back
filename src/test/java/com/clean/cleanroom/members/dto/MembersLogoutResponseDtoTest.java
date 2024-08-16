package com.clean.cleanroom.members.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MembersLogoutResponseDtoTest {

    @Test
    void testConstructorWithMessage() {
        // given: 메시지를 설정
        String message = "Logout successful";

        // when: MembersLogoutResponseDto 객체를 생성
        MembersLogoutResponseDto responseDto = new MembersLogoutResponseDto(message);

        // then: 필드 값이 예상대로 설정되었는지 확인
        assertEquals(message, responseDto.getMessage());
    }

    @Test
    void testConstructorWithNullMessage() {
        // given: null 메시지를 설정
        String message = null;

        // when: MembersLogoutResponseDto 객체를 생성
        MembersLogoutResponseDto responseDto = new MembersLogoutResponseDto(message);

        // then: 필드 값이 null로 설정되었는지 확인
        assertNull(responseDto.getMessage());
    }
}
