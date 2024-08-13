package com.clean.cleanroom.account.controller;

import com.clean.cleanroom.account.dto.AccountRequestDto;
import com.clean.cleanroom.account.dto.MessageResponseDto;
import com.clean.cleanroom.account.entity.Account;
import com.clean.cleanroom.account.service.AccountService;
import com.clean.cleanroom.members.entity.Members;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AccountControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountInfoService;  // 모킹된 accountInfoService

    @Mock
    private AccountService accountService;  // 모킹된 accountService

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    void createAccount() throws Exception {
        // Given
        AccountRequestDto requestDto = mock(AccountRequestDto.class);
        when(requestDto.getAccountNumber()).thenReturn("123456");
        when(requestDto.getBank()).thenReturn("Bank");

        String token = "Bearer sampleToken";
        Account account = new Account(1L, "test@test.com", "123456", "Bank");
        when(accountInfoService.createAccount(any(AccountRequestDto.class), anyString())).thenReturn(account);

        // When & Then
        mockMvc.perform(post("/api/account/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"123456\",\"bank\":\"Bank\"}")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("등록되었습니다."));

        verify(accountInfoService, times(1)).createAccount(any(AccountRequestDto.class), eq(token));
    }

    @Test
    void deleteAccount() throws Exception {
        // Given
        Long accountId = 1L;
        doNothing().when(accountInfoService).deleteAccount(anyLong());

        // When & Then
        mockMvc.perform(delete("/api/account/delete")
                        .param("accountId", accountId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("삭제되었습니다."));

        verify(accountInfoService, times(1)).deleteAccount(eq(accountId));
    }

    @Test
    void getAllAccounts() throws Exception {
        // Given
        Account account1 = new Account(1L, "test1@test.com", "123456", "Bank1");
        Account account2 = new Account(2L, "test2@test.com", "654321", "Bank2");
        List<Account> accounts = Arrays.asList(account1, account2);

        when(accountInfoService.getAllAccounts()).thenReturn(accounts);

        // When & Then
        mockMvc.perform(get("/api/account/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));

        verify(accountInfoService, times(1)).getAllAccounts();
    }

    @Test
    void getAccountById() throws Exception {
        // Given
        Long accountId = 1L;
        Account account = new Account(1L, "test1@test.com", "123456", "Bank1");

        when(accountInfoService.getAccountById(anyLong())).thenReturn(account);

        // When & Then
        mockMvc.perform(get("/api/account")
                        .param("accountId", accountId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("test1@test.com"));

        verify(accountInfoService, times(1)).getAccountById(eq(accountId));
    }

    @Test
    void selectAccount() throws Exception {
        // Given
        String token = "Bearer sampleToken";
        Long accountId = 1L;

        // Assuming selectAccount returns a Members object
        Members mockMember = mock(Members.class);
        when(accountService.selectAccount(anyString(), anyLong())).thenReturn(mockMember);

        // When & Then
        mockMvc.perform(post("/api/account/select")
                        .param("accountId", accountId.toString())
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("계좌가 선택되었습니다."));

        verify(accountService, times(1)).selectAccount(eq(token), eq(accountId));
    }
}
