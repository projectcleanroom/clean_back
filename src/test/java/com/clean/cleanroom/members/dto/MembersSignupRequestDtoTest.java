package com.clean.cleanroom.members.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MembersSignupRequestDtoTest {

    @Test
    void testConstructorAndGetters() {
        // Given
        String email = "test@example.com";
        String password = "password1!";
        String nick = "TestNick";
        String phoneNumber = "01012345678";

        // When
        MembersSignupRequestDto membersSignupRequestDto = createMembersRequestDto(email, password, nick, phoneNumber);

        // Then
        assertEquals(email, membersSignupRequestDto.getEmail());
        assertEquals(password, membersSignupRequestDto.getPassword());
        assertEquals(nick, membersSignupRequestDto.getNick());
        assertEquals(phoneNumber, membersSignupRequestDto.getPhoneNumber());
    }

    @Test
    void testConstructorWithNullValues() {
        // Given
        String email = null;
        String password = null;
        String nick = null;
        String phoneNumber = null;

        // When
        MembersSignupRequestDto membersSignupRequestDto = createMembersRequestDto(email, password, nick, phoneNumber);

        // Then
        assertNull(membersSignupRequestDto.getEmail());
        assertNull(membersSignupRequestDto.getPassword());
        assertNull(membersSignupRequestDto.getNick());
        assertNull(membersSignupRequestDto.getPhoneNumber());
    }

    // MembersRequestDto를 생성하기 위한 메서드
    private MembersSignupRequestDto createMembersRequestDto(String email, String password, String nick, String phoneNumber) {
        MembersSignupRequestDto membersRequestDto = new MembersSignupRequestDto();
        setField(membersRequestDto, "email", email);
        setField(membersRequestDto, "password", password);
        setField(membersRequestDto, "nick", nick);
        setField(membersRequestDto, "phoneNumber", phoneNumber);
        return membersRequestDto;
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
