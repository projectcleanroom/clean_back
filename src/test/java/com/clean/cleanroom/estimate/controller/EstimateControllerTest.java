//package com.clean.cleanroom.estimate.controller;
//
//import com.clean.cleanroom.estimate.dto.EstimateResponseDto;
//import com.clean.cleanroom.estimate.dto.EstimateListResponseDto;
//import com.clean.cleanroom.estimate.service.EstimateService;
//import com.clean.cleanroom.util.JwtUtil;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//public class EstimateControllerTest {
//
//    @InjectMocks
//    private EstimateController estimateController; // 테스트할 컨트롤러 클래스 인스턴스
//
//    @Mock
//    private EstimateService estimateService; // 모킹된 EstimateService 인스턴스
//
//    @Mock
//    private JwtUtil jwtUtil; // 모킹된 JwtUtil 인스턴스
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this); // Mockito 어노테이션을 초기화하여 목 객체들을 활성화
//    }
//
//    @Test
//    void approveEstimate() {
//        // given: 테스트 초기 조건 설정
//        String token = "Bearer sampleToken"; // 가짜 토큰
//        Long id = 1L; // 가짜 ID
//        EstimateResponseDto response = mock(EstimateResponseDto.class); // 모킹된 EstimateResponseDto 인스턴스
//
//        // 모킹된 메서드 반환값 설정
//        when(estimateService.approveEstimate(anyString(), any(Long.class))).thenReturn(response);
//
//        // when: 테스트할 동작 실행
//        ResponseEntity<EstimateResponseDto> result = estimateController.approveEstimate(token, id);
//
//        // then: 기대하는 결과 확인
//        assertEquals(HttpStatus.OK, result.getStatusCode()); // 상태 코드가 200(OK)인지 확인
//        assertEquals(response, result.getBody()); // 응답 본문이 예상한 response 객체와 일치하는지 확인
//        verify(estimateService, times(1)).approveEstimate(token, id); // approveEstimate 메서드가 한 번 호출되었는지 확인
//    }
//
//    @Test
//    void getAllEstimates() {
//        // given: 테스트 초기 조건 설정
//        String token = "Bearer sampleToken"; // 가짜 토큰
//        Long id = 1L; // 가짜 ID
//        List<EstimateListResponseDto> response = Collections.singletonList(mock(EstimateListResponseDto.class)); // 모킹된 EstimateListResponseDto 리스트
//
//        // 모킹된 메서드 반환값 설정
//        when(estimateService.getAllEstimates(anyString(), any(Long.class))).thenReturn(response);
//
//        // when: 테스트할 동작 실행
//        ResponseEntity<List<EstimateListResponseDto>> result = estimateController.getAllEstimates(token, id);
//
//        // then: 기대하는 결과 확인
//        assertEquals(HttpStatus.OK, result.getStatusCode()); // 상태 코드가 200(OK)인지 확인
//        assertEquals(response, result.getBody()); // 응답 본문이 예상한 response 객체와 일치하는지 확인
//        verify(estimateService, times(1)).getAllEstimates(token, id); // getAllEstimates 메서드가 한 번 호출되었는지 확인
//    }
//}
//
