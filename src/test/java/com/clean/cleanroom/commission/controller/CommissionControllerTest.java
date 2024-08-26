package com.clean.cleanroom.commission.controller;

import com.clean.cleanroom.commission.dto.*;
import com.clean.cleanroom.commission.service.CommissionService;
import com.clean.cleanroom.util.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommissionControllerTest {

    private CommissionController commissionController;

    @Mock
    private CommissionService commissionService;

    @Mock
    private TokenService tokenService;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // 생성자를 통해 모킹된 의존성을 주입하여 Controller 생성
        commissionController = new CommissionController(commissionService, tokenService);
    }

    @Test
    void createCommission() {
        // Given
        String email = "test@example.com";
        CommissionCreateRequestDto requestDto = mock(CommissionCreateRequestDto.class);
        List<CommissionCreateResponseDto> responseDtoList = mock(List.class);

        when(tokenService.getEmailFromRequest(request)).thenReturn(email);
        when(commissionService.createCommission(anyString(), any(CommissionCreateRequestDto.class)))
                .thenReturn(responseDtoList);

        // When
        ResponseEntity<List<CommissionCreateResponseDto>> response = commissionController.createCommission(request, requestDto);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDtoList, response.getBody());
    }

    @Test
    void updateCommission() {

        // Given
        String email = "test@example.com";
        Long commissionId = 1L;
        Long addressId = 1L;
        CommissionUpdateRequestDto requestDto = mock(CommissionUpdateRequestDto.class);
        List<CommissionUpdateResponseDto> responseDtoList = mock(List.class);

        when(tokenService.getEmailFromRequest(request)).thenReturn(email);
        when(commissionService.updateCommission(anyString(), anyLong(), anyLong(), any(CommissionUpdateRequestDto.class)))
                .thenReturn(responseDtoList);

        // When
        ResponseEntity<List<CommissionUpdateResponseDto>> response = commissionController.updateCommission(request, commissionId, addressId, requestDto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDtoList, response.getBody());
    }

    @Test
    void cancelCommission() {

        // Given
        String email = "test@example.com";
        Long commissionId = 1L;
        List<CommissionCancelResponseDto> responseDtoList = mock(List.class);

        when(tokenService.getEmailFromRequest(request)).thenReturn(email);
        when(commissionService.cancelCommission(anyString(), anyLong())).thenReturn(responseDtoList);

        // When
        ResponseEntity<List<CommissionCancelResponseDto>> response = commissionController.cancelCommission(request, commissionId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDtoList, response.getBody());
    }

    @Test
    void getMyCommission() {

        // Given
        String email = "test@example.com";
        List<MyCommissionResponseDto> responseDtoList = mock(List.class);

        when(tokenService.getEmailFromRequest(request)).thenReturn(email);
        when(commissionService.getMemberCommissionsByEmail(anyString(), any(Class.class))).thenReturn(responseDtoList);

        // When
        // @SuppressWarnings :컴파일러 경고를 억제하는 어노테이션
        // mock(List.class)는 제네릭 타입 정보를 무시하고 단순히 List로 취급하기 때문에 컴파일러가 타입 경고를 발생
        ResponseEntity<List<MyCommissionResponseDto>> response = commissionController.getMyCommission(request);

        // Then

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDtoList, response.getBody());
    }

    @Test
    void getConfirmedCommissions() {
        // Given
        String email = "test@example.com";
        Long commissionId = 1L;
        CommissionConfirmListResponseDto responseDto = mock(CommissionConfirmListResponseDto.class);

        when(tokenService.getEmailFromRequest(request)).thenReturn(email);
        when(commissionService.getCommissionConfirmList(anyString(), anyLong())).thenReturn(responseDto);

        // When
        ResponseEntity<CommissionConfirmListResponseDto> response = commissionController.getConfirmedCommissions(request, commissionId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void getConfirmDetailCommissions() {
        // Given
        String email = "test@example.com";
        Long commissionId = 1L;
        CommissionConfirmDetailResponseDto responseDto = mock(CommissionConfirmDetailResponseDto.class);

        when(tokenService.getEmailFromRequest(request)).thenReturn(email);
        when(commissionService.getCommissionDetailConfirm(anyString(), anyLong())).thenReturn(responseDto);

        // When
        ResponseEntity<CommissionConfirmDetailResponseDto> response = commissionController.getConfirmDetailCommissions(request, commissionId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void imgUpload() {
        // Given
        String token = "test-token";
        MultipartFile file = mock(MultipartFile.class);
        CommissionFileResponseDto responseDto = mock(CommissionFileResponseDto.class);

        when(commissionService.imgUpload(anyString(), any(MultipartFile.class))).thenReturn(responseDto);

        // When
        ResponseEntity<CommissionFileResponseDto> response = commissionController.imgUpload(token, file);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void imgGet() {
        // Given
        String token = "test-token";
        String fileName = "test.jpg";
        CommissionFileGetResponseDto responseDto = mock(CommissionFileGetResponseDto.class);
        byte[] fileData = new byte[0];

        when(commissionService.imgGet(anyString(), anyString())).thenReturn(responseDto);
        when(responseDto.getFileData()).thenReturn(fileData);

        // When
        ResponseEntity<byte[]> response = commissionController.imgGet(token, fileName);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(fileData, response.getBody());
    }

}
