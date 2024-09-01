package com.clean.cleanroom.members.service;

import com.clean.cleanroom.enums.LoginType;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.members.dto.*;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.entity.VerificationCode;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.clean.cleanroom.members.repository.VerificationCodeRepository;
import com.clean.cleanroom.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;


@Service
public class MembersService {
    private final MembersRepository membersRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final JwtUtil jwtUtil;

    public MembersService(MembersRepository membersRepository, VerificationCodeRepository verificationCodeRepository, JwtUtil jwtUtil) {
        this.membersRepository = membersRepository;
        this.verificationCodeRepository = verificationCodeRepository;
        this.jwtUtil = jwtUtil;
    }

    // 이메일 인증 코드를 생성하고 데이터베이스에 저장하거나 업데이트하는 메서드
    @Transactional
    public String generateEmailVerificationCode(String email) {
        // 6자리 랜덤 인증 코드 생성
        String verificationCode = generateVerificationCode();
        // 인증 코드의 만료 시간을 현재 시간으로부터 10분 후로 설정
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10);

        // 이메일로 기존에 저장된 인증 코드가 있는지 확인
        Optional<VerificationCode> optionalVerificationCode = verificationCodeRepository.findByEmail(email);
        if (optionalVerificationCode.isPresent()) {
            // 기존 코드가 있으면 코드와 만료 시간을 업데이트
            VerificationCode existingCode = optionalVerificationCode.get();
            existingCode.updateCodeAndExpiration(verificationCode, expirationTime);
            verificationCodeRepository.save(existingCode);
        } else {
            // 기존 코드가 없으면 새로운 인증 코드 생성
            VerificationCode newCode = new VerificationCode(email, verificationCode, expirationTime);
            verificationCodeRepository.save(newCode);
        }

        return verificationCode;
    }

    // 랜덤한 6자리 숫자 인증 코드를 생성하는 메서드
    private String generateVerificationCode() {
        // 0부터 999999 사이의 랜덤 숫자를 생성하여 6자리 문자열로 반환
        return String.format("%06d", new Random().nextInt(999999));
    }

    // 사용자가 입력한 이메일과 인증 코드를 검증하는 메서드
    @Transactional
    public void verifyEmail(String email, String code) {
        // 이메일에 해당하는 인증 코드 조회, 없으면 예외 발생
        VerificationCode storedCode = verificationCodeRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorMsg.INVALID_VERIFICATION_CODE));

        // 인증 코드가 만료되었는지 확인
        if (storedCode.isExpired()) {
            throw new CustomException(ErrorMsg.EXPIRED_VERIFICATION_CODE);
        }

        // 사용자가 입력한 코드가 저장된 코드와 일치하는지 확인
        if (!storedCode.getCode().equals(code)) {
            throw new CustomException(ErrorMsg.INVALID_VERIFICATION_CODE);
        }

        // 인증 완료 상태로 변경
        storedCode.markAsVerified();
        verificationCodeRepository.save(storedCode);
    }

    // 사용자의 이메일이 인증되었는지 확인하는 메서드
    public boolean isEmailVerified(String email) {
        // 이메일에 해당하는 인증 코드가 존재하고, 인증되었는지 여부를 반환
        return verificationCodeRepository.findByEmail(email)
                .map(VerificationCode::isVerified)
                .orElse(false);
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
        String email = jwtUtil.extractEmail(token);
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
        String email = jwtUtil.extractEmail(token);
        Members members = membersRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorMsg.INVALID_ID)
        );
        return new MembersGetProfileResponseDto(members);
    }
}
