package com.clean.cleanroom.members.dto;

import com.clean.cleanroom.members.entity.Members;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MembersLoginResponseDtoTest {

    @Test
    void testConstructorWithMembers() {
        // given: 유효한 Members 객체를 생성하고 설정
        String email = "test@example.com";
        String nick = "TestNick";

        Members members = mock(Members.class);
        when(members.getEmail()).thenReturn(email);
        when(members.getNick()).thenReturn(nick);

        // when: MembersLoginResponseDto 객체를 생성
        MembersLoginResponseDto responseDto = new MembersLoginResponseDto(members);

        // then: 필드 값이 예상대로 설정되었는지 확인
        assertEquals(email, responseDto.getEmail());
        assertEquals(nick, responseDto.getNick());
        assertNull(responseDto.getMessage()); // Message는 설정되지 않았으므로 null이어야 함
    }

    @Test
    void testConstructorWithMessage() {
        // given: 메시지를 설정
        String message = "Login failed";

        // when: MembersLoginResponseDto 객체를 생성
        MembersLoginResponseDto responseDto = new MembersLoginResponseDto(message);

        // then: 필드 값이 예상대로 설정되었는지 확인
        assertEquals(message, responseDto.getMessage());
        assertNull(responseDto.getEmail()); // Email은 설정되지 않았으므로 null이어야 함
        assertNull(responseDto.getNick());  // Nick은 설정되지 않았으므로 null이어야 함
    }
}
