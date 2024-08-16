package com.clean.cleanroom.members.dto;

import com.clean.cleanroom.members.entity.Members;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MembersProfileResponseDtoTest {

    @Test
    void testConstructorWithValidMembers() {
        // given: 유효한 Members 객체를 생성하고 설정
        String email = "test@example.com";
        String nick = "TestNick";
        String phoneNumber = "010-1234-5678";

        Members members = mock(Members.class);
        when(members.getEmail()).thenReturn(email);
        when(members.getNick()).thenReturn(nick);
        when(members.getPhoneNumber()).thenReturn(phoneNumber);

        // when: MembersProfileResponseDto 객체를 생성
        MembersProfileResponseDto responseDto = new MembersProfileResponseDto(members);

        // then: 필드 값이 예상대로 설정되었는지 확인
        assertEquals(email, responseDto.getEmail());
        assertEquals(nick, responseDto.getNick());
        assertEquals(phoneNumber, responseDto.getPhoneNumber());
    }
}
