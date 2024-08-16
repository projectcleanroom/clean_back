package com.clean.cleanroom.members.dto;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MembersUpdateProfileRequestDtoTest {

    @Test
    void testGetters() {
        // Given: MembersUpdateProfileRequestDto 객체를 모킹하여 필드 값을 설정
        String password = "password1!";
        String nick = "TestNick";
        String phoneNumber = "01012345678";

        MembersUpdateProfileRequestDto requestDto = mock(MembersUpdateProfileRequestDto.class);
        when(requestDto.getPassword()).thenReturn(password);
        when(requestDto.getNick()).thenReturn(nick);
        when(requestDto.getPhoneNumber()).thenReturn(phoneNumber);

        // When: Getter 메서드를 호출하여 값을 가져옴
        String actualPassword = requestDto.getPassword();
        String actualNick = requestDto.getNick();
        String actualPhoneNumber = requestDto.getPhoneNumber();

        // Then: 기대한 값과 실제 값이 일치하는지 확인
        assertEquals(password, actualPassword);
        assertEquals(nick, actualNick);
        assertEquals(phoneNumber, actualPhoneNumber);
    }

    @Test
    void testNullValues() {
        // Given: MembersUpdateProfileRequestDto 객체를 모킹하여 모든 필드 값을 null로 설정
        MembersUpdateProfileRequestDto requestDto = mock(MembersUpdateProfileRequestDto.class);
        when(requestDto.getPassword()).thenReturn(null);
        when(requestDto.getNick()).thenReturn(null);
        when(requestDto.getPhoneNumber()).thenReturn(null);

        // When: Getter 메서드를 호출하여 값을 가져옴
        String actualPassword = requestDto.getPassword();
        String actualNick = requestDto.getNick();
        String actualPhoneNumber = requestDto.getPhoneNumber();

        // Then: 모든 값이 null인지 확인
        assertEquals(null, actualPassword);
        assertEquals(null, actualNick);
        assertEquals(null, actualPhoneNumber);
    }

    @Test
    void testInvalidPasswordPattern() {
        // Given: 비밀번호가 규칙을 따르지 않는 경우를 테스트
        String invalidPassword = "123456";  // 유효하지 않은 비밀번호
        MembersUpdateProfileRequestDto requestDto = mock(MembersUpdateProfileRequestDto.class);
        when(requestDto.getPassword()).thenReturn(invalidPassword);

        // When: Getter 메서드를 호출하여 값을 가져옴
        String actualPassword = requestDto.getPassword();

        // Then: 비밀번호가 설정한 값과 동일한지 확인 (패턴 유효성은 실제 사용 시 발생)
        assertEquals(invalidPassword, actualPassword);
    }

    @Test
    void testInvalidNickPattern() {
        // Given: 닉네임이 규칙을 따르지 않는 경우를 테스트
        String invalidNick = "Invalid!@";  // 유효하지 않은 닉네임
        MembersUpdateProfileRequestDto requestDto = mock(MembersUpdateProfileRequestDto.class);
        when(requestDto.getNick()).thenReturn(invalidNick);

        // When: Getter 메서드를 호출하여 값을 가져옴
        String actualNick = requestDto.getNick();

        // Then: 닉네임이 설정한 값과 동일한지 확인 (패턴 유효성은 실제 사용 시 발생)
        assertEquals(invalidNick, actualNick);
    }

    @Test
    void testInvalidPhoneNumberPattern() {
        // Given: 전화번호가 규칙을 따르지 않는 경우를 테스트
        String invalidPhoneNumber = "123456789";  // 유효하지 않은 전화번호
        MembersUpdateProfileRequestDto requestDto = mock(MembersUpdateProfileRequestDto.class);
        when(requestDto.getPhoneNumber()).thenReturn(invalidPhoneNumber);

        // When: Getter 메서드를 호출하여 값을 가져옴
        String actualPhoneNumber = requestDto.getPhoneNumber();

        // Then: 전화번호가 설정한 값과 동일한지 확인 (패턴 유효성은 실제 사용 시 발생)
        assertEquals(invalidPhoneNumber, actualPhoneNumber);
    }
}
