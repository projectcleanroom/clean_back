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

import static org.junit.jupiter.api.Assertions.*;
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

        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(commissionRepository.findByIdAndMembersId(anyLong(), anyLong())).thenReturn(Optional.of(commission));
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));
        when(commissionRepository.findByMembersId(anyLong())).thenReturn(Optional.of(List.of(commission)));

        when(commission.getMembers()).thenReturn(member);
        when(commission.getAddress()).thenReturn(address);

        // When
        List<CommissionUpdateResponseDto> result = commissionService.updateCommission(email, commissionId, addressId, requestDto);

        // Then
        assertNotNull(result);
        verify(commission).update(any(CommissionUpdateRequestDto.class), any(Address.class));
    }

    @Test
    void updateCommission_ThrowsException_IfCommissionNotFound() {
        // Given
        String email = "test@example.com";
        Long commissionId = 1L;
        Long addressId = 1L;
        CommissionUpdateRequestDto requestDto = mock(CommissionUpdateRequestDto.class);
        Members member = mock(Members.class);

        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(commissionRepository.findByIdAndMembersId(anyLong(), anyLong())).thenReturn(Optional.empty());

        // Then
        assertThrows(CustomException.class, () -> {
            // When
            commissionService.updateCommission(email, commissionId, addressId, requestDto);
        });
    }

    @Test
    void cancelCommission() {
        // Given
        String email = "test@example.com";
        Long commissionId = 1L;
        Members member = mock(Members.class);
        Address address = mock(Address.class);
        Commission commission = mock(Commission.class);

        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(commissionRepository.findByIdAndMembersId(anyLong(), anyLong())).thenReturn(Optional.of(commission));
        when(commissionRepository.findByMembersId(anyLong())).thenReturn(Optional.of(List.of(commission)));

        when(commission.getMembers()).thenReturn(member);
        when(commission.getAddress()).thenReturn(address);

        // When
        List<CommissionCancelResponseDto> result = commissionService.cancelCommission(email, commissionId);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(commissionRepository).delete(any(Commission.class));
    }

    @Test
    void cancelCommission_ThrowsException_IfCommissionNotFound() {
        // Given
        String email = "test@example.com";
        Long commissionId = 1L;
        Members member = mock(Members.class);

        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(commissionRepository.findByIdAndMembersId(anyLong(), anyLong())).thenReturn(Optional.empty());

        // Then
        assertThrows(CustomException.class, () -> {
            // When
            commissionService.cancelCommission(email, commissionId);
        });
    }

    @Test
    void getMemberCommissionsByEmail() {
        // Given
        String email = "test@example.com";
        Members member = mock(Members.class);
        Address address = mock(Address.class);

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
    void getMemberCommissionsByEmail_ThrowsException_IfMemberNotFound() {
        // Given
        String email = "test@example.com";

        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Then
        assertThrows(CustomException.class, () -> {
            // When
            commissionService.getMemberCommissionsByEmail(email, CommissionCreateResponseDto.class);
        });
    }

    @Test
    void getAllCommissions() {
        // Given
        Members member = mock(Members.class);
        Address address = mock(Address.class);

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
    void getAllCommissions_EmptyList() {
        // Given
        when(commissionRepository.findAll()).thenReturn(List.of());

        // When
        List<CommissionCreateResponseDto> result = commissionService.getAllCommissions();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(commissionRepository).findAll();
    }


    @Test
    void getCommissionConfirmList_Success() {
        // Given
        String email = "test@example.com";
        Long commissionId = 1L;
        Members member = mock(Members.class);
        Commission commission = mock(Commission.class);
        Estimate estimate = mock(Estimate.class);
        Partner partner = mock(Partner.class);
        Address address = mock(Address.class);

        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(commissionRepository.findByIdAndMembersId(anyLong(), anyLong())).thenReturn(Optional.of(commission));
        when(commission.getEstimates()).thenReturn(List.of(estimate));
        when(estimate.getPartner()).thenReturn(partner);
        when(partner.getId()).thenReturn(100L);
        when(commission.getAddress()).thenReturn(address);
        when(address.getId()).thenReturn(200L);

        // When
        CommissionConfirmListResponseDto result = commissionService.getCommissionConfirmList(email, commissionId);

        // Then
        assertNotNull(result);
        verify(membersRepository, times(1)).findByEmail(email);
        verify(commissionRepository, times(1)).findByIdAndMembersId(commissionId, member.getId());
    }


    @Test
    void getCommissionConfirmList_EmptyEstimates() {
        // Given
        String email = "test@example.com";
        Long commissionId = 1L;
        Members member = mock(Members.class);
        Commission commission = mock(Commission.class);
        Address address = mock(Address.class);

        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(commissionRepository.findByIdAndMembersId(anyLong(), anyLong())).thenReturn(Optional.of(commission));
        when(commission.getEstimates()).thenReturn(List.of());
        when(commission.getAddress()).thenReturn(address);
        when(address.getId()).thenReturn(200L);

        // When
        CommissionConfirmListResponseDto result = commissionService.getCommissionConfirmList(email, commissionId);

        // Then
        assertNotNull(result);
        assertTrue(result.getEstimates().isEmpty());
        verify(membersRepository, times(1)).findByEmail(email);
        verify(commissionRepository, times(1)).findByIdAndMembersId(commissionId, member.getId());
    }

    @Test
    void getCommissionDetailConfirm_Success() {
        // Given
        String email = "test@example.com";
        Long commissionId = 1L;
        Members member = mock(Members.class);
        Commission commission = mock(Commission.class);
        Address address = mock(Address.class);

        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(commissionRepository.findByIdAndMembersId(anyLong(), anyLong())).thenReturn(Optional.of(commission));
        when(commission.getMembers()).thenReturn(member); // Members 객체 반환하도록 모킹
        when(commission.getAddress()).thenReturn(address); // Address 객체 반환하도록 모킹
        when(address.getId()).thenReturn(200L); // Address의 ID 값 모킹

        // When
        CommissionConfirmDetailResponseDto result = commissionService.getCommissionDetailConfirm(email, commissionId);

        // Then
        assertNotNull(result);
        verify(commissionRepository, times(1)).findByIdAndMembersId(commissionId, member.getId());
    }

}
