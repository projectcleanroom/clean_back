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

import java.time.LocalDateTime;
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

        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(members));
        when(estimateRepository.findById(anyLong())).thenReturn(Optional.of(estimate));
        when(estimate.getCommission()).thenReturn(commission);
        when(commission.getMembers()).thenReturn(members);
        when(members.getId()).thenReturn(1L);

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

//    @Test
//    void getAllEstimates() {
//        String token = "Bearer sampleToken";
//        Long commissionId = 1L;
//        String email = "test@example.com";
//        Members members = mock(Members.class);
//        Commission commission = mock(Commission.class);
//
//        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
//        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(members));
//        when(commissionRepository.findById(anyLong())).thenReturn(Optional.of(commission));
//        when(commission.getMembers()).thenReturn(members);
//        when(members.getId()).thenReturn(1L);
//        when(estimateRepository.findByCommissionId(any(Commission.class))).thenReturn(List.of(estimate));
//
//        List<EstimateListResponseDto> response = estimateService.getAllEstimates(token, commissionId);
//
//        assertNotNull(response);
//        assertFalse(response.isEmpty());
//    }
//
//    @Test
//    void getAllEstimates_ThrowsException_IfNoEstimatesFound() {
//        String token = "Bearer sampleToken";
//        Long commissionId = 1L;
//        String email = "test@example.com";
//        Members members = mock(Members.class);
//        Commission commission = mock(Commission.class);
//
//        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
//        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(members));
//        when(commissionRepository.findById(anyLong())).thenReturn(Optional.of(commission));
//        when(commission.getMembers()).thenReturn(members);
//        when(members.getId()).thenReturn(1L);
//        when(estimateRepository.findByCommissionId(any(Commission.class))).thenReturn(Collections.emptyList());
//
//        assertThrows(CustomException.class, () -> {
//            estimateService.getAllEstimates(token, commissionId);
//        });
//    }
}

