package com.clean.cleanroom.estimate.service;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.commission.repository.CommissionRepository;
import com.clean.cleanroom.estimate.dto.EstimateListResponseDto;
import com.clean.cleanroom.estimate.dto.EstimateResponseDto;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.estimate.repository.EstimateRepository;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class EstimateServiceTest {

    @InjectMocks
    private EstimateService estimateService; // 테스트할 서비스 클래스 인스턴스

    @Mock
    private EstimateRepository estimateRepository; // 모킹된 EstimateRepository 인스턴스

    @Mock
    private CommissionRepository commissionRepository; // 모킹된 CommissionRepository 인스턴스

    @Mock
    private MembersRepository membersRepository; // 모킹된 MembersRepository 인스턴스

    @Mock
    private JwtUtil jwtUtil; // 모킹된 JwtUtil 인스턴스

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Mockito 어노테이션을 초기화하여 목 객체들을 활성화
    }

    @Test
    void approveEstimate() {
        // given: 테스트 초기 조건 설정
        String token = "Bearer sampleToken";
        Long id = 1L;
        String email = "test@example.com";
        Members members = mock(Members.class); // 모킹된 Members 인스턴스
        Estimate estimate = mock(Estimate.class); // 모킹된 Estimate 인스턴스
        Commission commission = mock(Commission.class); // 모킹된 Commission 인스턴스

        // 모킹된 메서드 반환값 설정
        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(members));
        when(estimateRepository.findById(anyLong())).thenReturn(Optional.of(estimate));
        when(estimate.getCommissionId()).thenReturn(commission);
        when(commission.getMembers()).thenReturn(members);
        when(members.getId()).thenReturn(1L);

        // when: 테스트할 동작 실행
        EstimateResponseDto response = estimateService.approveEstimate(token, id);

        // then: 기대하는 결과 확인
        assertNotNull(response); // 응답이 null이 아닌지 확인
        verify(estimate).approve(); // approve 메서드가 호출되었는지 확인
        verify(estimateRepository).save(estimate); // save 메서드가 호출되었는지 확인
    }

    @Test
    void approveEstimate_ThrowsException_IfEstimateNotFound() {
        // given: 테스트 초기 조건 설정
        String token = "Bearer sampleToken";
        Long id = 1L;
        String email = "test@example.com";

        // 모킹된 메서드 반환값 설정
        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(mock(Members.class)));
        when(estimateRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then: 테스트할 동작 실행 및 예외 발생 확인
        assertThrows(CustomException.class, () -> {
            estimateService.approveEstimate(token, id);
        });
    }

    @Test
    void getAllEstimates() {
        // given: 테스트 초기 조건 설정
        String token = "Bearer sampleToken";
        Long commissionId = 1L;
        String email = "test@example.com";
        Members members = mock(Members.class); // 모킹된 Members 인스턴스
        Commission commission = mock(Commission.class); // 모킹된 Commission 인스턴스
        Estimate estimate = mock(Estimate.class); // 모킹된 Estimate 인스턴스

        // 모킹된 메서드 반환값 설정
        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(members));
        when(commissionRepository.findById(anyLong())).thenReturn(Optional.of(commission));
        when(commission.getMembers()).thenReturn(members);
        when(members.getId()).thenReturn(1L);
        when(estimateRepository.findByCommissionId(any(Commission.class))).thenReturn(List.of(estimate));

        // when: 테스트할 동작 실행
        List<EstimateListResponseDto> response = estimateService.getAllEstimates(token, commissionId);

        // then: 기대하는 결과 확인
        assertNotNull(response); // 응답이 null이 아닌지 확인
        assertFalse(response.isEmpty()); // 응답 리스트가 비어있지 않은지 확인
    }

    @Test
    void getAllEstimates_ThrowsException_IfNoEstimatesFound() {
        // given: 테스트 초기 조건 설정
        String token = "Bearer sampleToken";
        Long commissionId = 1L;
        String email = "test@example.com";
        Members members = mock(Members.class); // 모킹된 Members 인스턴스
        Commission commission = mock(Commission.class); // 모킹된 Commission 인스턴스

        // 모킹된 메서드 반환값 설정
        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(members));
        when(commissionRepository.findById(anyLong())).thenReturn(Optional.of(commission));
        when(commission.getMembers()).thenReturn(members);
        when(members.getId()).thenReturn(1L);
        when(estimateRepository.findByCommissionId(any(Commission.class))).thenReturn(Collections.emptyList());

        // when & then: 테스트할 동작 실행 및 예외 발생 확인
        assertThrows(CustomException.class, () -> {
            estimateService.getAllEstimates(token, commissionId);
        });
    }
}
