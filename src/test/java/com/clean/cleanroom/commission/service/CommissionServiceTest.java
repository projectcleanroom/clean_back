package com.clean.cleanroom.commission.service;

import com.clean.cleanroom.commission.dto.*;
import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.commission.repository.CommissionRepository;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.members.entity.Address;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.AddressRepository;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.redis.RedisService;
import com.clean.cleanroom.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
    private RedisService redisService;

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
        Long addressId = 1L;
        CommissionCreateRequestDto requestDto = mock(CommissionCreateRequestDto.class);

        Members member = mock(Members.class);
        Address address = mock(Address.class);

        when(requestDto.getAddressId()).thenReturn(addressId);
        when(membersRepository.findByEmail(email)).thenReturn(Optional.of(member));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        when(commissionRepository.save(any(Commission.class))).thenReturn(new Commission());

        // When
        CommissionCreateResponseDto response = commissionService.createCommission(email, requestDto);

        // Then
        assertNotNull(response);
        verify(commissionRepository, times(1)).save(any(Commission.class));
        verify(addressRepository, times(1)).findById(addressId);
        verify(redisService, times(1)).deleteObject(email);
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
        Long addressId = 1L;
        CommissionCreateRequestDto requestDto = mock(CommissionCreateRequestDto.class);

        Members member = mock(Members.class);

        when(requestDto.getAddressId()).thenReturn(addressId);
        when(membersRepository.findByEmail(email)).thenReturn(Optional.of(member));
        when(addressRepository.findById(addressId)).thenReturn(Optional.empty());

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
        when(member.getId()).thenReturn(1L); // 추가 설정: member.getId() 호출 시 1L 반환
        when(commissionRepository.findByIdAndMembersId(commissionId, member.getId())).thenReturn(Optional.of(commission));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));

        // 추가 설정: Commission 객체의 getMembers()와 getAddress() 호출 시 member와 address 반환
        when(commission.getMembers()).thenReturn(member);
        when(commission.getAddress()).thenReturn(address); // getAddress()가 address를 반환하도록 설정
        when(member.getNick()).thenReturn("TestNick"); // 필요한 경우 닉네임 설정
        when(address.getId()).thenReturn(1L); // Address 객체의 getId() 호출 시 1L 반환

        // When
        CommissionUpdateResponseDto response = commissionService.updateCommission(email, commissionId, addressId, requestDto);

        // Then
        assertNotNull(response);
        verify(commission, times(1)).update(requestDto, address);
        verify(redisService, times(1)).deleteObject(email);
    }



    @Test
    void updateCommission_CommissionNotFound() {
        // Given
        String email = "test@example.com";
        Long commissionId = 1L;
        Long addressId = 1L;
        CommissionUpdateRequestDto requestDto = mock(CommissionUpdateRequestDto.class);

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
        verify(redisService, times(1)).deleteObject(email);
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
        String token = "header.payload.signature"; // JWT 형식의 가짜 토큰을 설정
        MultipartFile file = mock(MultipartFile.class);
        String email = "test@example.com";

        // JwtUtil.extractEmail() 메서드를 모킹하여 올바른 email을 반환하도록 설정
        mockStatic(JwtUtil.class);
        when(JwtUtil.extractEmail(token)).thenReturn(email);

        when(file.getContentType()).thenReturn("image/jpeg");
        when(file.getOriginalFilename()).thenReturn("test.jpg");

        // When
        CommissionFileResponseDto response = commissionService.imgUpload(token, file);

        // Then
        assertEquals("File uploaded successfully", response.getMessage());
        assertEquals("test.jpg", response.getFileName());
    }



//    @Test
//    void imgUpload_InvalidFileType() {
//        // Given
//        String token = "header.payload.signature"; // JWT 형식의 가짜 토큰을 설정
//        MultipartFile file = mock(MultipartFile.class);
//        String email = "test@example.com";
//
//        try (MockedStatic<JwtUtil> jwtUtilMock = mockStatic(JwtUtil.class)) {
//            jwtUtilMock.when(() -> JwtUtil.extractEmail(token)).thenReturn(email);
//            when(file.getContentType()).thenReturn("text/plain");
//
//            // When
//            CommissionFileResponseDto response = commissionService.imgUpload(token, file);
//
//            // Then
//            assertEquals("Only image files are allowed.", response.getMessage());
//            assertNull(response.getFileName());
//        }
//    }
//
//    @Test
//    void imgGet_FileNotFound() {
//        // Given
//        String token = "header.payload.signature"; // JWT 형식의 가짜 토큰을 설정
//        String fileName = "test.jpg";
//        String email = "test@example.com";
//        Path filePath = Paths.get("/uploads/" + fileName);
//
//        try (MockedStatic<JwtUtil> jwtUtilMock = mockStatic(JwtUtil.class);
//             MockedStatic<Files> filesMock = mockStatic(Files.class)) { // Files 클래스에 대한 모킹 추가
//
//            jwtUtilMock.when(() -> JwtUtil.extractEmail(token)).thenReturn(email);
//
//            // 파일이 존재하지 않는다고 설정
//            filesMock.when(() -> Files.exists(filePath)).thenReturn(false);
//
//            // When
//            CommissionFileGetResponseDto response = commissionService.imgGet(token, fileName);
//
//            // Then
//            assertEquals("File not found", response.getMessage());
//            assertNull(response.getFileData());
//        }
//    }
}
