package com.clean.cleanroom.redis;

import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    // 인증 코드 저장
    public void setCode(String email, String code) {
        ValueOperations<String, Object> valOperations = redisTemplate.opsForValue();
        // 인증 코드를 5분(300초) 동안 저장
        valOperations.set(email, code, 300, TimeUnit.SECONDS);
    }

    // 인증 코드 조회
    public String getCode(String email) {
        ValueOperations<String, Object> valOperations = redisTemplate.opsForValue();
        Object code = valOperations.get(email);
        if (code == null) {
            throw new CustomException(ErrorMsg.INVALID_VERIFICATION_CODE);
        }
        return code.toString();
    }

    // 인증 완료 플래그 설정
    public void setVerifiedFlag(String email) {
        ValueOperations<String, Object> valOperations = redisTemplate.opsForValue();
        // 인증이 완료되면 인증 플래그를 Redis에 영구적으로 저장
        valOperations.set(email + "_verified", "true", 10, TimeUnit.MINUTES); // 만료 시간을 원하면 설정 가능
    }

    // 인증 완료 여부 확인
    public boolean isVerified(String email) {
        ValueOperations<String, Object> valOperations = redisTemplate.opsForValue();
        Object verifiedFlag = valOperations.get(email + "_verified");
        return verifiedFlag != null && verifiedFlag.equals("true");
    }
}