package com.clean.cleanroom.members.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MembersRequestDtoTest {

    @Test
    void testConstructorAndGetters() {
        // Given
        String email = "test@example.com";
        String password = "password1!";
        String nick = "TestNick";
        String phoneNumber = "01012345678";

        // When
        MembersRequestDto requestDto = createMembersRequestDto(email, password, nick, phoneNumber);

        // Then
        assertEquals(email, requestDto.getEmail());
        assertEquals(password, requestDto.getPassword());
        assertEquals(nick, requestDto.getNick());
        assertEquals(phoneNumber, requestDto.getPhoneNumber());
    }

    @Test
    void testConstructorWithNullValues() {
        // Given
        String email = null;
        String password = null;
        String nick = null;
        String phoneNumber = null;

        // When
        MembersRequestDto requestDto = createMembersRequestDto(email, password, nick, phoneNumber);

        // Then
        assertNull(requestDto.getEmail());
        assertNull(requestDto.getPassword());
        assertNull(requestDto.getNick());
        assertNull(requestDto.getPhoneNumber());
    }

    // MembersRequestDto를 생성하기 위한 메서드
    private MembersRequestDto createMembersRequestDto(String email, String password, String nick, String phoneNumber) {
        MembersRequestDto requestDto = new MembersRequestDto();
        setField(requestDto, "email", email);
        setField(requestDto, "password", password);
        setField(requestDto, "nick", nick);
        setField(requestDto, "phoneNumber", phoneNumber);
        return requestDto;
    }

    // Reflection을 사용해 필드 값을 설정하는 메서드
    private void setField(Object target, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
