package com.clean.cleanroom.members.dto;

import com.clean.cleanroom.members.entity.Members;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class MembersSignupResponseDtoTest {

    @Test
    void testConstructorWithMembers() {

        // given
        Members members = mock(Members.class);

        // when
        MembersSignupResponseDto responseDto = new MembersSignupResponseDto(members);

        // then
        assertEquals("회원 가입 성공!", responseDto.getMessage());
    }
}
