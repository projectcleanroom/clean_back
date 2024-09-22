package com.clean.cleanroom.members.entity;

import com.clean.cleanroom.account.entity.Account;
import com.clean.cleanroom.enums.LoginType;
import com.clean.cleanroom.members.dto.MembersSignupRequestDto;
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
    private MembersSignupRequestDto membersSignupRequestDto;

    @Mock
    private MembersUpdateProfileRequestDto updateProfileRequestDto;

    @Mock
    private Account account;

    private Members members;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRegularMember_Success() {
        // Given
        when(membersSignupRequestDto.getEmail()).thenReturn("test@example.com");
        when(membersSignupRequestDto.getNick()).thenReturn("TestNick");
        when(membersSignupRequestDto.getPhoneNumber()).thenReturn("01012345678");
        when(membersSignupRequestDto.getLoginType()).thenReturn(LoginType.REGULAR);
        when(membersSignupRequestDto.getPassword()).thenReturn("password123");

        // When
        members = new Members(membersSignupRequestDto);

        // Then
        assertEquals("test@example.com", members.getEmail());
        assertEquals("TestNick", members.getNick());
        assertEquals("01012345678", members.getPhoneNumber());
        assertEquals(LoginType.REGULAR, members.getLoginType());
        assertTrue(PasswordUtil.matches("password123", members.getPassword()));
        assertNull(members.getKakaoId());
    }

    @Test
    void createKakaoMember_Success() {
        // Given
        String email = "kakao@example.com";
        String nick = "KakaoNick";
        String phoneNumber = "01098765432";
        String kakaoId = "kakao123";
        LoginType loginType = LoginType.KAKAO;

        // When
        members = new Members(email, nick, phoneNumber, kakaoId, loginType);

        // Then
        assertEquals(email, members.getEmail());
        assertEquals(nick, members.getNick());
        assertEquals(phoneNumber, members.getPhoneNumber());
        assertEquals(loginType, members.getLoginType());
        assertEquals(kakaoId, members.getKakaoId());
        assertNull(members.getPassword());
    }

    @Test
    void updateMembersProfile_Success() {
        // Given
        members = new Members("test@example.com", "TestNick", "01012345678", null, LoginType.REGULAR);
        when(updateProfileRequestDto.getNick()).thenReturn("UpdatedNick");
        when(updateProfileRequestDto.getPhoneNumber()).thenReturn("01087654321");

        // When
        members.updateMembers(updateProfileRequestDto);

        // Then
        assertEquals("UpdatedNick", members.getNick());
        assertEquals("01087654321", members.getPhoneNumber());
    }

    @Test
    void updateMembersProfile_WithNullValues() {
        // Given
        members = new Members("test@example.com", "TestNick", "01012345678", null, LoginType.REGULAR);
        when(updateProfileRequestDto.getNick()).thenReturn(null);
        when(updateProfileRequestDto.getPhoneNumber()).thenReturn(null);

        // When
        members.updateMembers(updateProfileRequestDto);

        // Then
        assertEquals("TestNick", members.getNick()); // 기존 Nick 유지
        assertEquals("01012345678", members.getPhoneNumber()); // 기존 PhoneNumber 유지
    }

    @Test
    void setPassword_Success() {
        // Given
        members = new Members("test@example.com", "TestNick", "01012345678", null, LoginType.REGULAR);
        String newPassword = "newPassword123";

        // When
        members.setPassword(newPassword);

        // Then
        assertTrue(PasswordUtil.matches(newPassword, members.getPassword()));
    }

    @Test
    void setSelectedAccount_Success() {
        // Given
        members = new Members("test@example.com", "TestNick", "01012345678", null, LoginType.REGULAR);

        // When
        members.selectedAccount(account);

        // Then
        assertEquals(account, members.getSelectedAccount());
    }

    @Test
    void setKakaoId_Success() {
        // Given
        members = new Members("kakao@example.com", "KakaoNick", "01098765432", null, LoginType.KAKAO);
        String kakaoId = "newKakaoId123";

        // When
        members.setKakaoId(kakaoId);

        // Then
        assertEquals(kakaoId, members.getKakaoId());
    }
}
