package com.clean.cleanroom.account.entity;

import com.clean.cleanroom.account.dto.AccountRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountTest {

    @Mock
    private AccountRequestDto accountRequestDto;  // 모킹된 AccountRequestDto 객체

    private Account account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // 모킹 객체 초기화

        // 모킹된 AccountRequestDto 객체에 대한 설정
        when(accountRequestDto.getAccountNumber()).thenReturn("1234567890");
        when(accountRequestDto.getBank()).thenReturn("MockBank");

        // Account 객체 생성
        account = new Account("test@example.com", accountRequestDto);
    }

    @Test
    void testAccountFields() {
        // Account 객체의 필드 검증
        assertNull(account.getId());  // ID는 null이어야 함 (초기화되지 않음)
        assertEquals("test@example.com", account.getEmail());  // Email 검증
        assertEquals("1234567890", account.getAccountNumber());  // Account Number 검증
        assertEquals("MockBank", account.getBank());  // Bank 검증
    }

    @Test
    void testAccountConstructor() {
        // Account 객체가 올바르게 생성되었는지 검증
        AccountRequestDto anotherRequestDto = new AccountRequestDto();
        Account anotherAccount = new Account("another@example.com", accountRequestDto);

        assertEquals("another@example.com", anotherAccount.getEmail());  // Email 검증
        assertEquals("1234567890", anotherAccount.getAccountNumber());  // Account Number 검증
        assertEquals("MockBank", anotherAccount.getBank());  // Bank 검증
    }
}

