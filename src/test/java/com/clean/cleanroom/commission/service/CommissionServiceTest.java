package com.clean.cleanroom.commission.service;

import com.clean.cleanroom.commission.dto.CommissionCreateRequestDto;
import com.clean.cleanroom.commission.dto.MyCommissionResponseDto;
import com.clean.cleanroom.commission.repository.CommissionRepository;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.HouseType;
import com.clean.cleanroom.members.repository.MembersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@EnableCaching
public class CommissionServiceTest {

    private static final Logger log = LoggerFactory.getLogger(CommissionServiceTest.class);
    @Autowired
    private CommissionService commissionService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private MembersRepository membersRepository;

    @Autowired
    private CommissionRepository commissionRepository;

    private CommissionCreateRequestDto requestDto;

    private String testEmail = "aaaaa@naver.com";

    @BeforeEach
    void setUp() {
//        requestDto = new CommissionCreateRequestDto();
//        requestDto.setAddressId(1L); // 예시 데이터
//        requestDto.setCleanType(CleanType.NORMAL); // 예시 데이터
//        requestDto.setHouseType(HouseType.APT); // 예시 데이터
//        requestDto.setSize(100); // 예시 데이터
//        requestDto.setDesiredDate(LocalDateTime.now().plusDays(3)); // 예시 데이터
//        requestDto.setSignificant("Test request"); // 예시 데이터
//        requestDto.setImage("test_image.png"); // 예시 데이터
    }

    @Test
    @Transactional
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void testCachingOnMemberCommissionRetrieval() {

        // 1. 처음 청소의뢰 내역 조회 -> DB에서 조회되어야 함
        List<MyCommissionResponseDto> commissions = commissionService.getMemberCommissionsByEmail(testEmail);
        assertNotNull(commissions);
        log.info("처음 청소의뢰 내역 조회 -> DB에서 조회되어야 함");

        // 2. 캐시에 저장되었는지 확인
        List<MyCommissionResponseDto> cachedCommissions = cacheManager.getCache("commissionCache")
                .get(testEmail, List.class);
        assertNotNull(cachedCommissions);
        assertEquals(commissions.size(), cachedCommissions.size());
        log.info("캐시에 저장되었는지 확인");

        // 3. 다시 청소의뢰 내역 조회 -> 캐시에서 조회되어야 함
        List<MyCommissionResponseDto> cachedResult = commissionService.getMemberCommissionsByEmail(testEmail);
        assertEquals(commissions.size(), cachedResult.size());
        log.info("다시 청소의뢰 내역 조회 -> 캐시에서 조회되어야 함");

        // 4. 캐시 무효화 테스트 (예: 청소의뢰 생성)
        commissionService.createCommission(testEmail, requestDto);
        List<MyCommissionResponseDto> newCommissions = commissionService.getMemberCommissionsByEmail(testEmail);
        log.info("캐시 무효화 테스트 (예: 청소의뢰 생성)");

        // 5. 캐시가 무효화된 후 새로 조회된 데이터와 기존 캐시 데이터가 일치하지 않아야 함
        assertEquals(newCommissions.size(), commissionRepository.findByMembersId(membersRepository.findMemberIdByEmailNative(testEmail).getId()).get().size());
        log.info("캐시가 무효화된 후 새로 조회된 데이터와 기존 캐시 데이터가 일치하지 않아야 함");
    }
}
