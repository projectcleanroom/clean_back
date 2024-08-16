package com.clean.cleanroom.members.dto;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MembersResponseDtoTest {

    @Test
    void testGetters() {
        // Given: MembersResponseDto 객체를 모킹하여 필드 값을 설정
        Long id = 1L;
        String email = "test@example.com";
        String password = "password1!";
        String nick = "TestNick";
        String phoneNumber = "01012345678";

        MembersResponseDto responseDto = mock(MembersResponseDto.class);
        when(responseDto.getId()).thenReturn(id);
        when(responseDto.getEmail()).thenReturn(email);
        when(responseDto.getPassword()).thenReturn(password);
        when(responseDto.getNick()).thenReturn(nick);
        when(responseDto.getPhoneNumber()).thenReturn(phoneNumber);

        // When: Getter 메서드를 호출하여 값을 가져옴
        Long actualId = responseDto.getId();
        String actualEmail = responseDto.getEmail();
        String actualPassword = responseDto.getPassword();
        String actualNick = responseDto.getNick();
        String actualPhoneNumber = responseDto.getPhoneNumber();

        // Then: 기대한 값과 실제 값이 일치하는지 확인
        assertEquals(id, actualId);
        assertEquals(email, actualEmail);
        assertEquals(password, actualPassword);
        assertEquals(nick, actualNick);
        assertEquals(phoneNumber, actualPhoneNumber);
    }

    @Test
    void testNullValues() {
        // Given: MembersResponseDto 객체를 모킹하여 모든 필드 값을 null로 설정
        MembersResponseDto responseDto = mock(MembersResponseDto.class);
        when(responseDto.getId()).thenReturn(null);
        when(responseDto.getEmail()).thenReturn(null);
        when(responseDto.getPassword()).thenReturn(null);
        when(responseDto.getNick()).thenReturn(null);
        when(responseDto.getPhoneNumber()).thenReturn(null);

        // When: Getter 메서드를 호출하여 값을 가져옴
        Long actualId = responseDto.getId();
        String actualEmail = responseDto.getEmail();
        String actualPassword = responseDto.getPassword();
        String actualNick = responseDto.getNick();
        String actualPhoneNumber = responseDto.getPhoneNumber();

        // Then: 모든 값이 null인지 확인
        assertEquals(null, actualId);
        assertEquals(null, actualEmail);
        assertEquals(null, actualPassword);
        assertEquals(null, actualNick);
        assertEquals(null, actualPhoneNumber);
    }
}
