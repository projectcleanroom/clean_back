package com.clean.cleanroom.members.service;

import com.clean.cleanroom.enums.LoginType;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.exception.UnAuthenticationException;
import com.clean.cleanroom.members.dto.*;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.redis.RedisService;
import com.clean.cleanroom.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.concurrent.TimeUnit;


@Service
public class MembersService {

    private final MembersRepository membersRepository;
    private final RedisService redisService;

    public MembersService(MembersRepository membersRepository, RedisService redisService) {
        this.membersRepository = membersRepository;
        this.redisService = redisService;
    }

    // 이메일 인증 코드를 생성하고 Redis에 저장하는 메서드
    @Transactional
    public String generateEmailVerificationCode(String email) {
        // 6자리 랜덤 인증 코드 생성
        String verificationCode = generateVerificationCode();

        // Redis에 인증 코드 저장 (3분 동안 유효)
        redisService.setCode(email, verificationCode);

        return verificationCode;
    }

    // 랜덤한 6자리 숫자 인증 코드를 생성하는 메서드
    private String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    @Transactional
    public void verifyEmail(String email, String code) {
        // Redis에서 인증 코드 조회
        String storedCode = redisService.getCode(email);

        // 사용자가 입력한 코드와 Redis에 저장된 코드가 일치하는지 확인
        if (!storedCode.equals(code)) {
            throw new CustomException(ErrorMsg.INVALID_VERIFICATION_CODE);
        }
        // 이메일 인증이 완료되었다고 Redis에 플래그 저장
        redisService.setVerifiedFlag(email);
    }

    // 사용자의 이메일이 인증되었는지 확인하는 메서드
    public boolean isEmailVerified(String email) {
        // Redis에서 이메일 인증이 완료된 플래그가 있는지 확인
        return redisService.isVerified(email);
    }

    @Transactional
    public MembersSignupResponseDto signup(MembersSignupRequestDto requestDto) {
        // 일반 유저만 처리
        if (requestDto.getLoginType() != LoginType.REGULAR) {
            throw new CustomException(ErrorMsg.INVALID_SIGNUP_REQUEST);
        }
        // email 유무
        if (membersRepository.existsByEmail(requestDto.getEmail())){
            throw new CustomException(ErrorMsg.DUPLICATE_EMAIL);
        }
        // Nick 존재 유무
        if (membersRepository.existsByNick(requestDto.getNick())){
            throw new CustomException(ErrorMsg.DUPLICATE_NICK);
        }
        // phoneNumber 존재 유무
        if (requestDto.getPhoneNumber() != null && membersRepository.existsByPhoneNumber(requestDto.getPhoneNumber())) {
            throw new CustomException(ErrorMsg.DUPLICATE_PHONENUMBER);
        }

        // 이메일 인증 여부 확인
        if (!isEmailVerified(requestDto.getEmail())) {
            throw new CustomException(ErrorMsg.EMAIL_NOT_VERIFIED);
        }

        // 일반 회원으로 등록 (비밀번호는 필수이므로 바로 설정)
        Members member = new Members(requestDto);
        member.setPassword(requestDto.getPassword());

        membersRepository.save(member);
        return new MembersSignupResponseDto(member);
    }

    @Transactional
    public MembersProfileResponseDto profile(String token, MembersUpdateProfileRequestDto requestDto) {
        String email = JwtUtil.extractEmail(token);

        // email 존재 여부 확인
        Members members = membersRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorMsg.INVALID_ID));

        // 닉네임 중복 여부 확인
        if (!members.getNick().equals(requestDto.getNick()) &&
                membersRepository.existsByNick(requestDto.getNick())) {
            throw new CustomException(ErrorMsg.DUPLICATE_NICK);
        }

        // 휴대폰 번호 중복 여부 확인
        if (requestDto.getPhoneNumber() != null &&
                !requestDto.getPhoneNumber().equals(members.getPhoneNumber()) &&
                membersRepository.existsByPhoneNumber(requestDto.getPhoneNumber())) {
            throw new CustomException(ErrorMsg.DUPLICATE_PHONENUMBER);
        }

        // 비밀번호 업데이트 (필요 시)
        if (requestDto.getPassword() != null && !requestDto.getPassword().isEmpty()) {
            members.setPassword(requestDto.getPassword());
        }

        // 회원 정보 업데이트
        members.updateMembers(requestDto);
        membersRepository.save(members);

        // Redis 캐시 갱신
        String cacheKey = "profile_" + email;
        MembersGetProfileResponseDto updatedProfileResponse = new MembersGetProfileResponseDto(members);

        // 새로운 데이터를 Redis에 갱신
        redisService.setObject(cacheKey, updatedProfileResponse, 5, TimeUnit.MINUTES);

        return new MembersProfileResponseDto(members);
    }



    public MembersGetProfileResponseDto getProfile(String token) {
        String email = JwtUtil.extractEmail(token);
        String cacheKey = "profile_" + email;

        // Redis에서 캐시된 프로필 조회
        MembersGetProfileResponseDto cachedProfile = redisService.getObject(cacheKey, MembersGetProfileResponseDto.class);

        if (cachedProfile != null) {
            // 캐시된 데이터가 있으면 반환
            return cachedProfile;
        }
        System.out.println("캐시 데이터 없음....");
        // 캐시된 데이터가 없으면 DB에서 프로필 조회
        Members members = membersRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorMsg.INVALID_ID));

        MembersGetProfileResponseDto profileResponse = new MembersGetProfileResponseDto(members);

        // 조회한 데이터를 Redis에 캐시 (5분 동안 유효)
        redisService.setObject(cacheKey, profileResponse, 5, TimeUnit.MINUTES);

        return profileResponse;
    }
}
