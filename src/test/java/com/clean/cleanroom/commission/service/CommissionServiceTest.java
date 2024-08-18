package com.clean.cleanroom.commission.service;

import com.clean.cleanroom.commission.dto.*;
import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.commission.repository.CommissionRepository;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.members.entity.Address;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.AddressRepository;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.partner.entity.Partner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CommissionServiceTest {

    @InjectMocks
    private CommissionService commissionService;

    @Mock
    private CommissionRepository commissionRepository;

    @Mock
    private MembersRepository membersRepository;

    @Mock
    private AddressRepository addressRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCommission() {
        // Given
        String email = "test@example.com";
        CommissionCreateRequestDto requestDto = mock(CommissionCreateRequestDto.class);
        Members member = mock(Members.class);
        Address address = mock(Address.class);

        // Commission 객체 자체가 아니라 그 내부의 Members와 Address 객체를 설정
        Commission commission = new Commission(member, address, requestDto);

        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));
        when(commissionRepository.save(any(Commission.class))).thenReturn(commission);
        when(commissionRepository.findByMembersId(anyLong())).thenReturn(Optional.of(List.of(commission)));

        // When
        List<CommissionCreateResponseDto> result = commissionService.createCommission(email, requestDto);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(commissionRepository).save(any(Commission.class));
    }

    @Test
    void createCommission_ThrowsException_IfMemberNotFound() {
        // Given
        String email = "test@example.com";
        CommissionCreateRequestDto requestDto = mock(CommissionCreateRequestDto.class);

        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Then
        assertThrows(CustomException.class, () -> {
            // When
            commissionService.createCommission(email, requestDto);
        });
    }


    @Test
    void updateCommission() {
        // Given
        String email = "test@example.com";
        Long commissionId = 1L;
        Long addressId = 1L;
        CommissionUpdateRequestDto requestDto = mock(CommissionUpdateRequestDto.class);
        Members member = mock(Members.class);
        Address address = mock(Address.class);
        Commission commission = mock(Commission.class);

        // Mocking - 필요에 따라 설정 추가
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(commissionRepository.findByIdAndMembersId(anyLong(), anyLong())).thenReturn(Optional.of(commission));
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));
        when(commissionRepository.findByMembersId(anyLong())).thenReturn(Optional.of(List.of(commission)));

        // Commission의 getMembers 메서드가 mock된 Members 객체를 반환하도록 설정
        when(commission.getMembers()).thenReturn(member);

        // Commission의 getAddress 메서드가 mock된 Address 객체를 반환하도록 설정
        when(commission.getAddress()).thenReturn(address);

        // When
        List<CommissionUpdateResponseDto> result = commissionService.updateCommission(email, commissionId, addressId, requestDto);

        // Then
        assertNotNull(result);
        verify(commission).update(any(CommissionUpdateRequestDto.class), any(Address.class)); // 업데이트 메서드 호출 확인
    }



    @Test
    void cancelCommission() {
        // Given
        String email = "test@example.com";
        Long commissionId = 1L;
        Members member = mock(Members.class);
        Address address = mock(Address.class);
        Commission commission = mock(Commission.class);

        // Mock 설정
        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(commissionRepository.findByIdAndMembersId(anyLong(), anyLong())).thenReturn(Optional.of(commission));
        when(commissionRepository.findByMembersId(anyLong())).thenReturn(Optional.of(List.of(commission)));

        // Commission의 getMembers 메서드가 mock된 Members 객체를 반환하도록 설정
        when(commission.getMembers()).thenReturn(member);

        // Commission의 getAddress 메서드가 mock된 Address 객체를 반환하도록 설정
        when(commission.getAddress()).thenReturn(address);

        // When
        List<CommissionCancelResponseDto> result = commissionService.cancelCommission(email, commissionId);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(commissionRepository).delete(any(Commission.class));
    }


    @Test
    void getMemberCommissionsByEmail() {
        // Given
        String email = "test@example.com";
        Members member = mock(Members.class);
        Address address = mock(Address.class);

        // Commission 객체 내부의 Members와 Address를 설정
        Commission commission = new Commission(member, address, mock(CommissionCreateRequestDto.class));

        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(commissionRepository.findByMembersId(anyLong())).thenReturn(Optional.of(List.of(commission)));

        // When
        List<CommissionCreateResponseDto> result = commissionService.getMemberCommissionsByEmail(email, CommissionCreateResponseDto.class);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(commissionRepository).findByMembersId(anyLong());
    }


    @Test
    void getAllCommissions() {
        // Given
        Members member = mock(Members.class);
        Address address = mock(Address.class);

        // Commission 객체 내부의 Members와 Address를 설정
        Commission commission = new Commission(member, address, mock(CommissionCreateRequestDto.class));

        when(commissionRepository.findAll()).thenReturn(List.of(commission));

        // When
        List<CommissionCreateResponseDto> result = commissionService.getAllCommissions();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(commissionRepository).findAll();
    }

    @Test
    void getCommissionConfirmList_Success() {
        // Given
        String email = "test@example.com";
        Members member = mock(Members.class);
        Commission commission = mock(Commission.class);
        Estimate estimate = mock(Estimate.class);
        Partner partner = mock(Partner.class);

        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(commissionRepository.findByMembers(any(Members.class))).thenReturn(List.of(commission));
        when(commission.getEstimates()).thenReturn(List.of(estimate));
        when(estimate.getPartner()).thenReturn(partner); // 모킹된 Partner 객체를 반환하도록 설정합니다.
        when(partner.getId()).thenReturn(1L);

        // When
        List<CommissionConfirmListResponseDto> result = commissionService.getCommissionConfirmList(email);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(membersRepository, times(1)).findByEmail(email);
        verify(commissionRepository, times(1)).findByMembers(member);
    }

    @Test
    void getCommissionDetailConfirm_Success() {
        // Given
        Long estimateId = 1L;
        Long commissionId = 1L;
        Commission commission = mock(Commission.class);

        when(commissionRepository.findByEstimatesIdAndId(anyLong(), anyLong())).thenReturn(commission);

        // When
        CommissionConfirmDetailResponseDto result = commissionService.getCommissionDetailConfirm(estimateId, commissionId);

        // Then
        assertNotNull(result);
        verify(commissionRepository, times(1)).findByEstimatesIdAndId(estimateId, commissionId);
    }
}
