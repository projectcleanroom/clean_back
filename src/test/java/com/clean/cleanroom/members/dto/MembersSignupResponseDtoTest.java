package com.clean.cleanroom.members.dto;

import com.clean.cleanroom.members.entity.Members;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class MembersSignupResponseDtoTest {

    @Test
    void testConstructorWithMembers() {
        // given: 유효한 Members 객체를 생성
        Members members = mock(Members.class);

        // when: MembersSignupResponseDto 객체를 생성
        MembersSignupResponseDto responseDto = new MembersSignupResponseDto(members);

        // then: 필드 값이 예상대로 설정되었는지 확인
        assertEquals("회원 가입 성공!", responseDto.getMessage());
    }
}
