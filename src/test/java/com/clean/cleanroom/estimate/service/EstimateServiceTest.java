package com.clean.cleanroom.estimate.service;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.commission.repository.CommissionRepository;
import com.clean.cleanroom.estimate.dto.EstimateListResponseDto;
import com.clean.cleanroom.estimate.dto.EstimateResponseDto;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.estimate.repository.EstimateRepository;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.partner.entity.Partner;
import com.clean.cleanroom.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EstimateServiceTest {

    @InjectMocks
    private EstimateService estimateService;

    @Mock
    private EstimateRepository estimateRepository;

    @Mock
    private CommissionRepository commissionRepository;

    @Mock
    private MembersRepository membersRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private Estimate estimate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Mockito 어노테이션을 초기화하여 모킹된 객체들을 활성화
    }

    @Test
    void approveEstimate() {
        // given: 테스트 초기 조건 설정
        String token = "Bearer sampleToken";
        Long id = 1L;
        String email = "test@example.com";
        Members members = mock(Members.class); // 모킹된 Members 인스턴스
        Commission commission = mock(Commission.class); // 모킹된 Commission 인스턴스

        // Estimate 객체를 생성자를 통해 초기화
        Estimate estimate = new Estimate(null, commission, mock(Partner.class), 10000, LocalDateTime.now(), "Test Statement", false);

        // 모킹된 메서드 반환값 설정
        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(members));
        when(estimateRepository.findById(anyLong())).thenReturn(Optional.of(estimate));
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
    void getAllEstimates() {
        // given: 테스트 초기 조건 설정
        String token = "Bearer sampleToken";
        Long commissionId = 1L;
        String email = "test@example.com";
        Members members = mock(Members.class); // 모킹된 Members 인스턴스
        Commission commission = mock(Commission.class); // 모킹된 Commission 인스턴스
        Estimate estimate = new Estimate(null, commission, mock(Partner.class), 10000, LocalDateTime.now(), "Test Statement", false);

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
}
