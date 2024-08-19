package com.clean.cleanroom.members.entity;

import com.clean.cleanroom.account.entity.Account;
import com.clean.cleanroom.members.dto.MembersRequestDto;
import com.clean.cleanroom.members.dto.MembersUpdateProfileRequestDto;
import com.clean.cleanroom.util.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MembersTest {

    @Mock
    private MembersRequestDto requestDto; // 모킹된 MembersRequestDto 객체

    @Mock
    private MembersUpdateProfileRequestDto updateProfileRequestDto; // 모킹된 MembersUpdateProfileRequestDto 객체

    @Mock
    private Account account; // 모킹된 Account 객체

    private Members members;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // 모킹 객체 초기화

        // Given: MembersRequestDto와 필요한 모킹 설정
        when(requestDto.getEmail()).thenReturn("test@example.com");
        when(requestDto.getPassword()).thenReturn("password123");
        when(requestDto.getNick()).thenReturn("TestNick");
        when(requestDto.getPhoneNumber()).thenReturn("01012345678");

        // When: 생성자를 통해 Members 객체 생성
        members = new Members(requestDto);

        // 암호화된 비밀번호를 바로 주입
        String encodedPassword = PasswordUtil.encodePassword("password123");
        when(requestDto.getPassword()).thenReturn(encodedPassword);

        // When: 생성자를 통해 Members 객체 생성
        members = new Members(requestDto);
    }

    @Test
    void testMembersFields() {
        // Then: Members 객체가 올바르게 초기화되었는지 검증
        assertEquals("test@example.com", members.getEmail());
        assertEquals("TestNick", members.getNick());
        assertEquals("01012345678", members.getPhoneNumber());
    }

    @Test
    void testMembersMethod() {
        // Given: 새로운 MembersRequestDto 객체
        MembersRequestDto newRequestDto = mock(MembersRequestDto.class);
        when(newRequestDto.getEmail()).thenReturn("newemail@example.com");
        when(newRequestDto.getNick()).thenReturn("NewNick");
        when(newRequestDto.getPhoneNumber()).thenReturn("01098765432");

        // When: members 메서드를 호출하여 필드를 업데이트
        members.members(newRequestDto);

        // Then: 필드가 예상대로 업데이트되었는지 검증
        assertEquals("newemail@example.com", members.getEmail());
        assertEquals("NewNick", members.getNick());
        assertEquals("01098765432", members.getPhoneNumber());
    }

    @Test
    void testUpdateMembers() {
        // Given: 필요한 모킹 설정
        when(updateProfileRequestDto.getNick()).thenReturn("UpdatedNick");
        when(updateProfileRequestDto.getPhoneNumber()).thenReturn("01087654321");

        // When: updateMembers 메서드를 호출하여 Members 객체를 업데이트
        members.updateMembers(updateProfileRequestDto);

        // Then: 필드가 예상대로 업데이트되었는지 검증
        assertEquals("UpdatedNick", members.getNick());
        assertEquals("01087654321", members.getPhoneNumber());
    }

    @Test
    void testUpdateMembers_WithNullNick() {
        when(updateProfileRequestDto.getNick()).thenReturn(null);
        when(updateProfileRequestDto.getPhoneNumber()).thenReturn("01087654321");

        members.updateMembers(updateProfileRequestDto);

        assertEquals("TestNick", members.getNick()); // 기존 nick 유지
        assertEquals("01087654321", members.getPhoneNumber());
    }

    @Test
    void testUpdateMembers_WithNullPhoneNumber() {
        when(updateProfileRequestDto.getNick()).thenReturn("UpdatedNick");
        when(updateProfileRequestDto.getPhoneNumber()).thenReturn(null);

        members.updateMembers(updateProfileRequestDto);

        assertEquals("UpdatedNick", members.getNick());
        assertEquals("01012345678", members.getPhoneNumber()); // 기존 phoneNumber 유지
    }

    @Test
    void testUpdateMembers_WithNullNickAndPhoneNumber() {
        when(updateProfileRequestDto.getNick()).thenReturn(null);
        when(updateProfileRequestDto.getPhoneNumber()).thenReturn(null);

        members.updateMembers(updateProfileRequestDto);

        assertEquals("TestNick", members.getNick()); // 기존 nick 유지
        assertEquals("01012345678", members.getPhoneNumber()); // 기존 phoneNumber 유지
    }

    @Test
    void testCheckPassword() {
        // When & Then: 비밀번호가 일치하는지 확인
        assertTrue(members.checkPassword("password123"));
        assertFalse(members.checkPassword("wrongPassword"));
    }

    @Test
    void testSetPassword() {
        // Given: 원래 비밀번호를 설정
        String rawPassword = "newPassword123";
        members.setPassword(rawPassword);

        // When & Then: 비밀번호가 암호화되어 올바르게 저장되었는지 검증
        assertTrue(PasswordUtil.matches(rawPassword, members.getPassword()));
    }



    @Test
    void testSelectedAccount() {
        // When: SelectedAccount 메서드를 호출하여 Account를 설정
        members.SelectedAccount(account);

        // Then: 선택된 계정이 올바르게 설정되었는지 확인
        assertEquals(account, members.getSelectedAccount());
    }
}
