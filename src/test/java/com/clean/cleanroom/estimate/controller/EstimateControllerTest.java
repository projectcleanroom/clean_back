package com.clean.cleanroom.estimate.controller;

import com.clean.cleanroom.estimate.dto.EstimateDetailResponseDto;
import com.clean.cleanroom.estimate.dto.EstimateResponseDto;
import com.clean.cleanroom.estimate.dto.EstimateListResponseDto;
import com.clean.cleanroom.estimate.service.EstimateService;
import com.clean.cleanroom.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class EstimateControllerTest {

    @InjectMocks
    private EstimateController estimateController;

    @Mock
    private EstimateService estimateService;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void approveEstimate() {
        // given: 테스트 초기 조건 설정
        String token = "Bearer sampleToken";
        Long id = 1L;
        EstimateResponseDto response = mock(EstimateResponseDto.class);

        // 모킹된 메서드 반환값 설정
        when(estimateService.approveEstimate(anyString(), any(Long.class))).thenReturn(response);

        // when: 테스트할 동작 실행
        ResponseEntity<EstimateResponseDto> result = estimateController.approveEstimate(token, id);

        // then: 기대하는 결과 확인
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(estimateService, times(1)).approveEstimate(token, id);
    }

    @Test
    void getAllEstimates() {
        // given: 테스트 초기 조건 설정
        String token = "Bearer sampleToken"; // 가짜 토큰
        Long id = 1L; // 가짜 ID
        List<EstimateListResponseDto> response = Collections.singletonList(mock(EstimateListResponseDto.class));

        // 모킹된 메서드 반환값 설정
        when(estimateService.getAllEstimates(anyString(), any(Long.class))).thenReturn(response);

        // when: 테스트할 동작 실행
        ResponseEntity<List<EstimateListResponseDto>> result = estimateController.getAllEstimates(token, id);

        // then: 기대하는 결과 확인
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(estimateService, times(1)).getAllEstimates(token, id);
    }

    @Test
    void getEstimateById_Success() {
        // Given: 테스트 초기 조건 설정
        String token = "Bearer sampleToken"; // 가짜 토큰
        Long id = 1L; // 가짜 ID
        EstimateDetailResponseDto response = mock(EstimateDetailResponseDto.class);

        // 모킹된 메서드 반환값 설정
        when(estimateService.getEstimateById(anyString(), any(Long.class))).thenReturn(response);

        // When: 테스트할 동작 실행
        ResponseEntity<EstimateDetailResponseDto> result = estimateController.getEstimateById(token, id);

        // Then: 기대하는 결과 확인
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(estimateService, times(1)).getEstimateById(token, id);
    }

}

