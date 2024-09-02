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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
        CommissionCreateResponseDto responseDto = mock(CommissionCreateResponseDto.class);

        when(tokenService.getEmailFromRequest(request)).thenReturn(email);
        when(commissionService.createCommission(anyString(), any(CommissionCreateRequestDto.class)))
                .thenReturn(responseDto);

        // When
        ResponseEntity<CommissionCreateResponseDto> response = commissionController.createCommission(request, requestDto);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void updateCommission() {
        // Given
        String email = "test@example.com";
        Long commissionId = 1L;
        Long addressId = 1L;
        CommissionUpdateRequestDto requestDto = mock(CommissionUpdateRequestDto.class);
        CommissionUpdateResponseDto responseDto = mock(CommissionUpdateResponseDto.class);

        when(tokenService.getEmailFromRequest(request)).thenReturn(email);
        when(commissionService.updateCommission(anyString(), anyLong(), anyLong(), any(CommissionUpdateRequestDto.class)))
                .thenReturn(responseDto);

        // When
        ResponseEntity<CommissionUpdateResponseDto> response = commissionController.updateCommission(request, commissionId, addressId, requestDto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void cancelCommission() {
        // Given
        String email = "test@example.com";
        Long commissionId = 1L;
        CommissionCancelResponseDto responseDto = mock(CommissionCancelResponseDto.class);

        when(tokenService.getEmailFromRequest(request)).thenReturn(email);
        when(commissionService.cancelCommission(anyString(), anyLong())).thenReturn(responseDto);

        // When
        ResponseEntity<CommissionCancelResponseDto> response = commissionController.cancelCommission(request, commissionId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void getMyCommission() {
        // Given
        String email = "test@example.com";
        List<MyCommissionResponseDto> responseDtoList = mock(List.class);

        when(tokenService.getEmailFromRequest(request)).thenReturn(email);
        when(commissionService.getMemberCommissionsByEmail(anyString()))
                .thenReturn(responseDtoList);

        // When
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

    @Test
    void imgGet_withValidMimeType() throws IOException {
        // Given: 테스트에 필요한 데이터 설정
        String token = "test-token";
        String fileName = "test.jpg";
        CommissionFileGetResponseDto responseDto = mock(CommissionFileGetResponseDto.class);
        byte[] fileData = new byte[0];

        when(commissionService.imgGet(anyString(), anyString())).thenReturn(responseDto);
        when(responseDto.getFileData()).thenReturn(fileData);

        // 실제 파일 시스템을 이용해 파일을 생성 (테스트가 끝난 후 파일 삭제)
        Path filePath = Paths.get("uploads/" + fileName);
        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath.getParent()); // 테스트를 위한 디렉토리 생성
            Files.createFile(filePath); // 테스트를 위한 파일 생성
        }

        // When: 실제 메서드 호출
        ResponseEntity<byte[]> response;
        try {
            response = commissionController.imgGet(token, fileName);
        } finally {
            // 테스트가 끝난 후 파일 정리
            Files.deleteIfExists(filePath);
        }

        // Then: 결과 검증
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(fileData, response.getBody());
        assertEquals("image/jpeg", response.getHeaders().getContentType().toString());
    }

    @Test
    void imgUpload_withEmptyFileHandledByService() throws IOException {
        // Given
        String token = "test-token";
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        // 모킹된 서비스가 빈 파일에 대해 예외를 던지도록 설정 (이 부분이 비즈니스 로직에 따라 다름)
        when(commissionService.imgUpload(anyString(), any(MultipartFile.class)))
                .thenThrow(new IllegalArgumentException("File is empty"));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            commissionController.imgUpload(token, file);
        });
    }

    @Test
    void imgGet_withInvalidFileName() {
        // Given
        String token = "test-token";
        String invalidFileName = "invalid_file_name.jpg";

        when(commissionService.imgGet(anyString(), eq(invalidFileName)))
                .thenThrow(new RuntimeException("File not found"));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            commissionController.imgGet(token, invalidFileName);
        });
    }
}
