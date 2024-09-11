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
        // email 유무
        Members members = membersRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorMsg.INVALID_ID));
        // Nick 존재 유무
        if (!members.getNick().equals(requestDto.getNick()) &&
                membersRepository.existsByNick(requestDto.getNick())){
            throw new CustomException(ErrorMsg.DUPLICATE_NICK);
        }

        // phoneNumber 존재 유무
        if (requestDto.getPhoneNumber() != null &&
                !requestDto.getPhoneNumber().equals(members.getPhoneNumber()) &&
                membersRepository.existsByPhoneNumber(requestDto.getPhoneNumber())) {
            throw new CustomException(ErrorMsg.DUPLICATE_PHONENUMBER);
        }
        // 비밀번호 일치 확인
//        if (!members.checkPassword(requestDto.getPassword())) {
//            throw new CustomException(ErrorMsg.PASSWORD_INCORRECT);
//        }

        if (requestDto.getPassword() != null && !requestDto.getPassword().isEmpty()) {
            members.setPassword(requestDto.getPassword());
        }
        members.updateMembers(requestDto);
        membersRepository.save(members);
        return new MembersProfileResponseDto(members);
    }


    public MembersGetProfileResponseDto getProfile(String token) {
        // email 존재 유무
        String email = JwtUtil.extractEmail(token);
        Members members = membersRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorMsg.INVALID_ID)
        );
        return new MembersGetProfileResponseDto(members);
    }
}
