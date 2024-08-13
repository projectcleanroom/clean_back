package com.clean.cleanroom.account.service;

import com.clean.cleanroom.account.dto.AccountRequestDto;
import com.clean.cleanroom.account.entity.Account;
import com.clean.cleanroom.account.repository.AccountRepository;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private MembersRepository membersRepository;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAccount() {
        // Given
        String token = "Bearer sampleToken";
        String email = "test@example.com";
        AccountRequestDto accountRequestDto = mock(AccountRequestDto.class);
        Members member = mock(Members.class);
        Account account = new Account(email, accountRequestDto);

        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        // When
        Account createdAccount = accountService.createAccount(accountRequestDto, token);

        // Then
        assertNotNull(createdAccount);
        assertEquals(email, createdAccount.getEmail());
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void createAccount_ThrowsException_IfMemberNotFound() {
        // Given
        String token = "Bearer sampleToken";
        String email = "test@example.com";
        AccountRequestDto accountRequestDto = mock(AccountRequestDto.class);

        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Then
        assertThrows(CustomException.class, () -> {
            // When
            accountService.createAccount(accountRequestDto, token);
        });
    }

    @Test
    void deleteAccount() {
        // Given
        Long accountId = 1L;

        when(accountRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(accountRepository).deleteById(anyLong());

        // When
        accountService.deleteAccount(accountId);

        // Then
        verify(accountRepository).deleteById(accountId);
    }

    @Test
    void deleteAccount_ThrowsException_IfAccountNotFound() {
        // Given
        Long accountId = 1L;

        when(accountRepository.existsById(anyLong())).thenReturn(false);

        // Then
        assertThrows(CustomException.class, () -> {
            // When
            accountService.deleteAccount(accountId);
        });
    }

    @Test
    void getAllAccounts() {
        // Given
        Account account1 = mock(Account.class);
        Account account2 = mock(Account.class);
        List<Account> accounts = List.of(account1, account2);

        when(accountRepository.findAll()).thenReturn(accounts);

        // When
        List<Account> result = accountService.getAllAccounts();

        // Then
        assertEquals(2, result.size());
        verify(accountRepository).findAll();
    }

    @Test
    void getAccountById() {
        // Given
        Long accountId = 1L;
        Account account = mock(Account.class);

        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));

        // When
        Account result = accountService.getAccountById(accountId);

        // Then
        assertNotNull(result);
        verify(accountRepository).findById(accountId);
    }

    @Test
    void getAccountById_ThrowsException_IfAccountNotFound() {
        // Given
        Long accountId = 1L;

        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Then
        assertThrows(CustomException.class, () -> {
            // When
            accountService.getAccountById(accountId);
        });
    }

    @Test
    void selectAccount() {
        // Given
        String token = "Bearer sampleToken";
        Long accountId = 1L;
        String email = "test@example.com";
        Members member = mock(Members.class);
        Account account = mock(Account.class);

        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));
        when(membersRepository.save(any(Members.class))).thenReturn(member);

        // When
        Members result = accountService.selectAccount(token, accountId);

        // Then
        assertNotNull(result);
        verify(membersRepository).save(member);
    }

    @Test
    void selectAccount_ThrowsException_IfMemberNotFound() {
        // Given
        String token = "Bearer sampleToken";
        String email = "test@example.com";
        Long accountId = 1L;

        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Then
        assertThrows(CustomException.class, () -> {
            // When
            accountService.selectAccount(token, accountId);
        });
    }

    @Test
    void selectAccount_ThrowsException_IfAccountNotFound() {
        // Given
        String token = "Bearer sampleToken";
        String email = "test@example.com";
        Long accountId = 1L;
        Members member = mock(Members.class);

        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Then
        assertThrows(CustomException.class, () -> {
            // When
            accountService.selectAccount(token, accountId);
        });
    }
}
