package com.clean.cleanroom.commission.service;

import com.clean.cleanroom.commission.dto.*;
import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.commission.repository.CommissionRepository;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.members.entity.Address;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.AddressRepository;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.partner.entity.Partner;
import com.clean.cleanroom.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCommission_Success() {
        // Given
        String email = "test@example.com";
        CommissionCreateRequestDto requestDto = mock(CommissionCreateRequestDto.class);
        Members member = mock(Members.class);
        Address address = mock(Address.class);
        Commission commission = new Commission(member, address, requestDto); // Commission 객체 생성

        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));
        when(commissionRepository.save(any(Commission.class))).thenReturn(commission); // save 메서드 모킹
        when(commissionRepository.findTopByMembersIdOrderByIdDesc(anyLong())).thenReturn(Optional.of(commission));
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

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            commissionService.createCommission(email, requestDto);
        });

        // ErrorMsg 객체 자체를 비교
        assertEquals(ErrorMsg.MEMBER_NOT_FOUND, exception.getErrorMsg());
    }


    @Test
    void updateCommission_Success() {
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
        when(commission.getMembers()).thenReturn(member); // Commission 객체의 getMembers() 모킹
        when(commission.getAddress()).thenReturn(address); // Commission 객체의 getAddress() 모킹

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

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            commissionService.updateCommission(email, commissionId, addressId, requestDto);
        });

        // ErrorMsg 객체 자체를 비교
        assertEquals(ErrorMsg.COMMISSION_NOT_FOUND_OR_UNAUTHORIZED, exception.getErrorMsg());
    }


    @Test
    void cancelCommission_Success() {
        // Given
        String email = "test@example.com";
        Long commissionId = 1L;
        Members member = mock(Members.class);
        Commission commission = mock(Commission.class);
        Address address = mock(Address.class);

        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(commissionRepository.findByIdAndMembersId(anyLong(), anyLong())).thenReturn(Optional.of(commission));
        when(commissionRepository.findByMembersId(anyLong())).thenReturn(Optional.of(List.of(commission)));
        when(commission.getMembers()).thenReturn(member); // Members 객체를 반환하도록 설정
        when(commission.getAddress()).thenReturn(address); // Address 객체를 반환하도록 설정

        // When
        List<CommissionCancelResponseDto> result = commissionService.cancelCommission(email, commissionId);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(commissionRepository).delete(any(Commission.class));
    }

//    @Test
//    void cancelCommission_ThrowsException_IfCommissionNotFound() {
//        // Given
//        String email = "test@example.com";
//        Long commissionId = 1L;
//        Members member = mock(Members.class);
//
//        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
//        when(commissionRepository.findByIdAndMembersId(anyLong(), anyLong())).thenReturn(Optional.empty());
//
//        // Then
//        CustomException exception = assertThrows(CustomException.class, () -> {
//            // When
//            commissionService.cancelCommission(email, commissionId);
//        });
//
//        assertEquals(ErrorMsg.COMMISSION_NOT_FOUND_OR_UNAUTHORIZED, exception.getMessage());
//    }

    @Test
    void cancelCommission_ThrowsException_IfCommissionNotFound() {
        // Given
        String email = "test@example.com";
        Long commissionId = 1L;
        Members member = mock(Members.class);

        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(commissionRepository.findByIdAndMembersId(anyLong(), anyLong())).thenReturn(Optional.empty());

        // Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            // When
            commissionService.cancelCommission(email, commissionId);
        });

        assertEquals("청소의뢰가 존재하지 않거나 권한이 없습니다.", exception.getMessage());
    }



    @Test
    void getMemberCommissionsByEmail_Success() {
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
        CustomException exception = assertThrows(CustomException.class, () -> {
            // When
            commissionService.getMemberCommissionsByEmail(email, CommissionCreateResponseDto.class);
        });

        // 기대 메시지를 실제 한글 메시지로 수정
        assertEquals("사용자를 찾을 수 없습니다.", exception.getMessage());
    }


    @Test
    void getAllCommissions_Success() {
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
        when(commission.getAddress()).thenReturn(address);

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
        Address address = mock(Address.class); // Address 객체 추가

        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(commissionRepository.findByIdAndMembersId(anyLong(), anyLong())).thenReturn(Optional.of(commission));
        when(commission.getEstimates()).thenReturn(List.of());
        when(commission.getAddress()).thenReturn(address); // Commission에서 Address 반환하도록 설정

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
        Address address = mock(Address.class); // Address 객체 추가

        when(membersRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        when(commissionRepository.findByIdAndMembersId(anyLong(), anyLong())).thenReturn(Optional.of(commission));
        when(commission.getMembers()).thenReturn(member); // Commission에서 Members 반환하도록 설정
        when(commission.getAddress()).thenReturn(address); // Commission에서 Address 반환하도록 설정

        // When
        CommissionConfirmDetailResponseDto result = commissionService.getCommissionDetailConfirm(email, commissionId);

        // Then
        assertNotNull(result);
        verify(commissionRepository, times(1)).findByIdAndMembersId(commissionId, member.getId());
    }


    @Test
    void imgUpload_Success() throws IOException {
        // Given
        String token = "test-token";
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("test.jpg");
        when(file.getContentType()).thenReturn("image/jpeg");
        when(jwtUtil.extractEmail(anyString())).thenReturn("test@example.com");

        // When
        CommissionFileResponseDto responseDto = commissionService.imgUpload(token, file);

        // Then
        assertNotNull(responseDto);
        assertEquals("File uploaded successfully", responseDto.getMessage());
    }

    @Test
    void imgUpload_Failure_NonImageFile() {
        // Given
        String token = "test-token";
        MultipartFile file = mock(MultipartFile.class);
        when(file.getContentType()).thenReturn("application/pdf");
        when(jwtUtil.extractEmail(anyString())).thenReturn("test@example.com");

        // When
        CommissionFileResponseDto responseDto = commissionService.imgUpload(token, file);

        // Then
        assertNotNull(responseDto);
        assertEquals("Only image files are allowed.", responseDto.getMessage());
    }

    @Test
    void imgUpload_Failure_IOException() throws IOException {
        // Given
        String token = "test-token";
        MultipartFile file = mock(MultipartFile.class);
        when(file.getContentType()).thenReturn("image/jpeg");
        when(file.getOriginalFilename()).thenReturn("test.jpg");
        doThrow(new IOException("Test IOException")).when(file).transferTo(any(File.class));
        when(jwtUtil.extractEmail(anyString())).thenReturn("test@example.com");

        // When
        CommissionFileResponseDto responseDto = commissionService.imgUpload(token, file);

        // Then
        assertNotNull(responseDto);
        assertEquals("File upload failed", responseDto.getMessage());
    }

//
//    @Test
//    void imgGet_Success() throws IOException {
//
//    // Given
//        String token = "test-token";
//        String fileName = "test.jpg";
//        Path filePath = Paths.get("/uploads/" + fileName);
//
//        try (MockedStatic<Files> filesMock = mockStatic(Files.class)) {
//            filesMock.when(() -> Files.exists(filePath)).thenReturn(true);
//            filesMock.when(() -> Files.readAllBytes(filePath)).thenReturn(new byte[10]);
//
//            when(jwtUtil.extractEmail(anyString())).thenReturn("test@example.com");
//
//            // When
//            CommissionFileGetResponseDto responseDto = commissionService.imgGet(token, fileName);
//
//            // Then
//            assertNotNull(responseDto);
//            assertEquals("File retrieved successfully", responseDto.getMessage());
//        }
//    }
//
//    @Test
//    void imgGet_Failure_FileNotFound() {
//        // Given
//        String token = "test-token";
//        String fileName = "test.jpg";
//        Path filePath = Paths.get("/uploads/" + fileName);
//        when(jwtUtil.extractEmail(anyString())).thenReturn("test@example.com");
//        when(Files.exists(filePath)).thenReturn(false);
//
//        // When
//        CommissionFileGetResponseDto responseDto = commissionService.imgGet(token, fileName);
//
//        // Then
//        assertNotNull(responseDto);
//        assertEquals("File not found", responseDto.getMessage());
//    }
//
//    @Test
//    void imgGet_Failure_IOException() throws IOException {
//        // Given
//        String token = "test-token";
//        String fileName = "test.jpg";
//        Path filePath = Paths.get("/uploads/" + fileName);
//        when(jwtUtil.extractEmail(anyString())).thenReturn("test@example.com");
//        when(Files.exists(filePath)).thenReturn(true);
//        when(Files.readAllBytes(filePath)).thenThrow(new IOException("Test IOException"));
//
//        // When
//        CommissionFileGetResponseDto responseDto = commissionService.imgGet(token, fileName);
//
//        // Then
//        assertNotNull(responseDto);
//        assertEquals("Failed to retrieve file", responseDto.getMessage());
//    }
}
