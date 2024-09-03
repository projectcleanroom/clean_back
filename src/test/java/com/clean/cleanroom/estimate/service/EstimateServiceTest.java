package com.clean.cleanroom.estimate.service;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.commission.repository.CommissionRepository;
import com.clean.cleanroom.estimate.dto.EstimateDetailResponseDto;
import com.clean.cleanroom.estimate.dto.EstimateListResponseDto;
import com.clean.cleanroom.estimate.dto.EstimateResponseDto;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.estimate.repository.EstimateRepository;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.partner.entity.Partner;
import com.clean.cleanroom.partner.repository.PartnerRepository;
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
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void approveEstimate() {
        String token = "Bearer sampleToken";
        Long id = 1L;
        String email = "test@example.com";
        Members members = mock(Members.class);
        Commission commission = mock(Commission.class);
        Partner partner =  mock(Partner.class);

        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(members));
        when(estimateRepository.findById(anyLong())).thenReturn(Optional.of(estimate));
        when(estimate.getCommission()).thenReturn(commission);

        //연관관계 필드 가져오기
        when(commission.getMembers()).thenReturn(members);
        when(members.getId()).thenReturn(1L);

        when(estimate.getPartner()).thenReturn(partner);
        when(partner.getId()).thenReturn(1L);

        EstimateResponseDto response = estimateService.approveEstimate(token, id);

        assertNotNull(response);
        verify(estimate).approve();
        verify(estimateRepository).save(estimate);
    }

    @Test
    void approveEstimate_ThrowsException_IfEstimateNotFound() {
        String token = "Bearer sampleToken";
        Long id = 1L;
        String email = "test@example.com";

        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(mock(Members.class)));
        when(estimateRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> {
            estimateService.approveEstimate(token, id);
        });
    }

    @Test
    void approveEstimate_ThrowsException_IfCommissionMemberMismatch() {
        // Given
        String token = "Bearer sampleToken";
        Long id = 1L;
        String email = "test@example.com";
        Members members = mock(Members.class);
        Members otherMembers = mock(Members.class);
        Commission commission = mock(Commission.class);

        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(members));
        when(estimateRepository.findById(anyLong())).thenReturn(Optional.of(estimate));
        when(estimate.getCommission()).thenReturn(commission);
        when(commission.getMembers()).thenReturn(otherMembers);
        when(otherMembers.getId()).thenReturn(2L); // 다른 회원 ID 설정
        when(members.getId()).thenReturn(1L); // 현재 로그인된 회원의 ID 설정

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            estimateService.approveEstimate(token, id);
        });

        // Then
        assertEquals(2001, exception.getCode());
    }




    @Test
    void getAllEstimates_Success() {

        // Given
        String token = "Bearer sampleToken";
        Long commissionId = 1L;
        String email = "test@example.com";
        Members member = mock(Members.class);
        Commission commission = mock(Commission.class);
        Estimate estimate = mock(Estimate.class);
        List<Estimate> estimates = List.of(estimate);

        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(commissionRepository.findById(anyLong())).thenReturn(Optional.of(commission));
        when(commission.getMembers()).thenReturn(member);
        when(member.getId()).thenReturn(1L);
        when(estimateRepository.findByCommissionId(commission)).thenReturn(estimates);
        when(estimate.getCommission()).thenReturn(commission);

        // When
        List<EstimateListResponseDto> result = estimateService.getAllEstimates(token, commissionId);

        // Then
        assertEquals(1, result.size());
        verify(estimateRepository, times(1)).findByCommissionId(commission);
    }

    @Test
    void getAllEstimates_ThrowsException_IfCommissionNotFound() {

        // Given
        String token = "Bearer sampleToken";
        Long commissionId = 1L;
        String email = "test@example.com";

        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(mock(Members.class)));
        when(commissionRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CustomException.class, () -> {
            estimateService.getAllEstimates(token, commissionId);
        });
    }

    @Test
    void getAllEstimates_ThrowsException_IfUnauthorizedMember() {

        // Given
        String token = "Bearer sampleToken";
        Long commissionId = 1L;
        String email = "test@example.com";
        Members members = mock(Members.class);
        Members otherMember = mock(Members.class);
        Commission commission = mock(Commission.class);

        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(members));
        when(commissionRepository.findById(anyLong())).thenReturn(Optional.of(commission));
        when(commission.getMembers()).thenReturn(otherMember);
        when(otherMember.getId()).thenReturn(2L);
        when(members.getId()).thenReturn(1L);

        // When & Then
        assertThrows(CustomException.class, () -> {
            estimateService.getAllEstimates(token, commissionId);
        });
    }

    @Test
    void getAllEstimates_ThrowsException_IfNoEstimatesFound() {
        // Given
        String token = "Bearer sampleToken";
        Long commissionId = 1L;
        String email = "test@example.com";
        Members members = mock(Members.class);
        Commission commission = mock(Commission.class);

        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(members));
        when(commissionRepository.findById(anyLong())).thenReturn(Optional.of(commission));
        when(commission.getMembers()).thenReturn(members);
        when(members.getId()).thenReturn(1L);
        when(estimateRepository.findByCommissionId(commission)).thenReturn(Collections.emptyList());

        // When & Then
        assertThrows(CustomException.class, () -> {
            estimateService.getAllEstimates(token, commissionId);
        });
    }

    @Test
    void getEstimateById_Success() {
        // Given
        String token = "Bearer sampleToken";
        Long estimateId = 1L;
        String email = "test@example.com";
        Long memberId = 1L;
        Long partnerId = 1L;

        Members members = mock(Members.class);
        Commission commission = mock(Commission.class);
        Estimate estimate = mock(Estimate.class);
        Partner partner = mock(Partner.class);

        // JWT에서 이메일 추출
        when(jwtUtil.extractEmail(anyString())).thenReturn(email);

        // 이메일로 회원 정보 조회
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(members));
        when(members.getId()).thenReturn(memberId);

        // 견적 ID로 견적 정보 조회
        when(estimateRepository.findById(anyLong())).thenReturn(Optional.of(estimate));
        when(estimate.getCommission()).thenReturn(commission);

        // 의뢰 정보에서 회원 정보 조회
        when(commission.getMembers()).thenReturn(members);

        // Partner 객체 반환 설정
        when(estimate.getPartner()).thenReturn(partner);
        when(partner.getId()).thenReturn(partnerId);

        // When
        EstimateDetailResponseDto response = estimateService.getEstimateById(token, estimateId);

        // Then
        assertNotNull(response);
        verify(estimateRepository, times(1)).findById(estimateId);
        verify(estimate, times(1)).getCommission();
        verify(commission, times(1)).getMembers();
        verify(partner, times(1)).getId(); // Partner 객체의 ID 반환 확인
        assertEquals(partnerId, partner.getId());
    }


    @Test
    void getEstimateById_ThrowsException_IfEstimateNotFound() {
        // Given
        String token = "Bearer sampleToken";
        Long estimateId = 1L;
        String email = "test@example.com";

        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(mock(Members.class)));
        when(estimateRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            estimateService.getEstimateById(token, estimateId);
        });

        assertEquals(ErrorMsg.ESTIMATE_NOT_FOUND.getCode(), exception.getErrorMsg().getCode());
    }

    @Test
    void getEstimateById_ThrowsException_IfUnauthorizedMember() {
        // Given
        String token = "Bearer sampleToken";
        Long estimateId = 1L;
        String email = "test@example.com";
        Members members = mock(Members.class);
        Members otherMember = mock(Members.class);
        Long memberId = 1L;
        Long otherMemberId = 2L;

        Commission commission = mock(Commission.class); // Commission 객체를 여기서 선언
        Estimate estimate = mock(Estimate.class); // Estimate 객체를 여기서 선언

        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(members));
        when(members.getId()).thenReturn(memberId);
        when(estimateRepository.findById(anyLong())).thenReturn(Optional.of(estimate));
        when(estimate.getCommission()).thenReturn(commission);
        when(commission.getMembers()).thenReturn(otherMember);
        when(otherMember.getId()).thenReturn(otherMemberId);

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            estimateService.getEstimateById(token, estimateId);
        });

        assertEquals(ErrorMsg.UNAUTHORIZED_MEMBER.getCode(), exception.getErrorMsg().getCode());
    }
}

