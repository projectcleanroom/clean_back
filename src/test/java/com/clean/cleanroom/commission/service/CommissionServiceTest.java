package com.clean.cleanroom.commission.service;

import com.clean.cleanroom.commission.dto.*;
import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.commission.repository.CommissionRepository;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.members.entity.Address;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.AddressRepository;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommissionServiceTest {

    @Mock
    private CommissionRepository commissionRepository;

    @Mock
    private MembersRepository membersRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private CommissionService commissionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCommission_Success() {
        // Given
        String email = "test@example.com";
        CommissionCreateRequestDto requestDto = mock(CommissionCreateRequestDto.class); // DTO 객체를 모킹

        Members member = mock(Members.class);
        Address address = mock(Address.class);

        when(membersRepository.findByEmail(email)).thenReturn(Optional.of(member));
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        when(commissionRepository.save(any(Commission.class))).thenReturn(new Commission());

        // When
        CommissionCreateResponseDto response = commissionService.createCommission(email, requestDto);

        // Then
        assertNotNull(response);
        verify(commissionRepository, times(1)).save(any(Commission.class));
    }

    @Test
    void createCommission_MemberNotFound() {
        // Given
        String email = "test@example.com";
        CommissionCreateRequestDto requestDto = mock(CommissionCreateRequestDto.class);

        when(membersRepository.findByEmail(email)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CustomException.class, () -> commissionService.createCommission(email, requestDto));
    }

    @Test
    void createCommission_AddressNotFound() {
        // Given
        String email = "test@example.com";
        CommissionCreateRequestDto requestDto = mock(CommissionCreateRequestDto.class);

        Members member = mock(Members.class);

        when(membersRepository.findByEmail(email)).thenReturn(Optional.of(member));
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CustomException.class, () -> commissionService.createCommission(email, requestDto));
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

        when(membersRepository.findByEmail(email)).thenReturn(Optional.of(member));
        when(commissionRepository.findByIdAndMembersId(commissionId, member.getId())).thenReturn(Optional.of(commission));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));

        // When
        CommissionUpdateResponseDto response = commissionService.updateCommission(email, commissionId, addressId, requestDto);

        // Then
        assertNotNull(response);
        verify(commission, times(1)).update(requestDto, address);
    }

    @Test
    void updateCommission_CommissionNotFound() {
        // Given
        String email = "test@example.com";
        Long commissionId = 1L;
        Long addressId = 1L;
        CommissionUpdateRequestDto requestDto = mock(CommissionUpdateRequestDto.class); //DTO 클래스 자체를 모킹

        Members member = mock(Members.class);

        when(membersRepository.findByEmail(email)).thenReturn(Optional.of(member));
        when(commissionRepository.findByIdAndMembersId(commissionId, member.getId())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CustomException.class, () -> commissionService.updateCommission(email, commissionId, addressId, requestDto));
    }

    @Test
    void cancelCommission_Success() {
        // Given
        String email = "test@example.com";
        Long commissionId = 1L;

        Members member = mock(Members.class);
        Commission commission = mock(Commission.class);

        when(membersRepository.findByEmail(email)).thenReturn(Optional.of(member));
        when(commissionRepository.findByIdAndMembersId(commissionId, member.getId())).thenReturn(Optional.of(commission));

        // When
        CommissionCancelResponseDto response = commissionService.cancelCommission(email, commissionId);

        // Then
        assertNotNull(response);
        verify(commissionRepository, times(1)).delete(commission);
    }

    @Test
    void cancelCommission_CommissionNotFound() {
        // Given
        String email = "test@example.com";
        Long commissionId = 1L;

        Members member = mock(Members.class);

        when(membersRepository.findByEmail(email)).thenReturn(Optional.of(member));
        when(commissionRepository.findByIdAndMembersId(commissionId, member.getId())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CustomException.class, () -> commissionService.cancelCommission(email, commissionId));
    }

    @Test
    void imgUpload_Success() throws IOException {
        // Given
        String token = "testToken";
        MultipartFile file = mock(MultipartFile.class);
        String email = "test@example.com";

        when(jwtUtil.extractEmail(token)).thenReturn(email);
        when(file.getContentType()).thenReturn("image/jpeg");
        when(file.getOriginalFilename()).thenReturn("test.jpg");

        // When
        CommissionFileResponseDto response = commissionService.imgUpload(token, file);

        // Then
        assertEquals("File uploaded successfully", response.getMessage());
        assertEquals("test.jpg", response.getFileName());
    }

    @Test
    void imgUpload_InvalidFileType() {
        // Given
        String token = "testToken";
        MultipartFile file = mock(MultipartFile.class);
        String email = "test@example.com";

        when(jwtUtil.extractEmail(token)).thenReturn(email);
        when(file.getContentType()).thenReturn("text/plain");

        // When
        CommissionFileResponseDto response = commissionService.imgUpload(token, file);

        // Then
        assertEquals("Only image files are allowed.", response.getMessage());
        assertNull(response.getFileName());
    }

    @Test
    void imgGet_Success() throws IOException {
        // Given
        String token = "testToken";
        String fileName = "test.jpg";
        String email = "test@example.com";

        when(jwtUtil.extractEmail(token)).thenReturn(email);
        Path filePath = mock(Path.class);
        when(filePath.toAbsolutePath()).thenReturn(Paths.get("/uploads/" + fileName));
        when(Files.exists(filePath)).thenReturn(true);
        when(Files.readAllBytes(filePath)).thenReturn(new byte[]{1, 2, 3});

        // When
        CommissionFileGetResponseDto response = commissionService.imgGet(token, fileName);

        // Then
        assertEquals("File retrieved successfully", response.getMessage());
        assertArrayEquals(new byte[]{1, 2, 3}, response.getFileData());
    }

    @Test
    void imgGet_FileNotFound() throws IOException {
        // Given
        String token = "testToken";
        String fileName = "test.jpg";
        String email = "test@example.com";

        when(jwtUtil.extractEmail(token)).thenReturn(email);
        Path filePath = mock(Path.class);
        when(filePath.toAbsolutePath()).thenReturn(Paths.get("/uploads/" + fileName));
        when(Files.exists(filePath)).thenReturn(false);

        // When
        CommissionFileGetResponseDto response = commissionService.imgGet(token, fileName);

        // Then
        assertEquals("File not found", response.getMessage());
        assertNull(response.getFileData());
    }
}
