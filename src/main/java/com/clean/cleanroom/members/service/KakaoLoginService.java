package com.clean.cleanroom.members.service;

import com.clean.cleanroom.enums.LoginType;
import com.clean.cleanroom.exception.CustomException;
import com.clean.cleanroom.exception.ErrorMsg;
import com.clean.cleanroom.members.dto.*;
import com.clean.cleanroom.members.entity.Members;
import com.clean.cleanroom.members.repository.MembersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoLoginService {

    private final MembersRepository membersRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final MembersLoginService membersLoginService;

    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";
    private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String CLIENT_ID = "65f1cfe772375248de10b233e85b8203";
    private static final String REDIRECT_URI = "http://localhost:5173/oauth/kakao/callback";

    public void socialKakaoLogin(KakaoAuthCodeRequestDto kakaoAuthCodeRequestDto, HttpServletResponse response) throws IOException {
        // 1. 카카오 서버로부터 액세스 토큰을 요청
        OAuthTokenDto oauthToken = requestKakaoToken(kakaoAuthCodeRequestDto.getCode());

        // 2. 액세스 토큰을 사용해 사용자 정보 요청
        KakaoUserInfoRequestDto kakaoUserInfoRequestDto = requestKakaoUserInfo(oauthToken.getAccess_token());

        // 3. 사용자 정보로 멤버 조회 또는 신규 가입 처리
        Members kakaoMember = findOrCreateKakaoMember(kakaoUserInfoRequestDto);

        // 4. 로그인 로직 호출 - 카카오 유저는 비밀번호 없이 로그인
        membersLoginService.kakaoLogin(new MembersLoginRequestDto(kakaoMember.getEmail(), null), response);
    }

    private OAuthTokenDto requestKakaoToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", CLIENT_ID);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                KAKAO_TOKEN_URL,
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        try {
            return objectMapper.readValue(response.getBody(), OAuthTokenDto.class);
        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorMsg.FAILED_TO_PARSE_KAKAO_RESPONSE);
        }
    }

    private KakaoUserInfoRequestDto requestKakaoUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> kakaoProfileRequest = new HttpEntity<>(headers);

        ResponseEntity<String> profileResponse = restTemplate.exchange(
                KAKAO_USER_INFO_URL,
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        try {
            JsonNode root = objectMapper.readTree(profileResponse.getBody());
            String email = root.path("kakao_account").path("email").asText();
            String nick = root.path("properties").path("nickname").asText();
            String kakaoId = root.path("id").asText();

            return new KakaoUserInfoRequestDto(kakaoId, email, nick);
        } catch (Exception e) {
            throw new CustomException(ErrorMsg.FAILED_TO_PARSE_KAKAO_RESPONSE);
        }
    }
    private Members findOrCreateKakaoMember(KakaoUserInfoRequestDto kakaoUserInfoRequestDto) {
        // 이메일로 회원을 찾음
        Optional<Members> existingMember = membersRepository.findByEmail(kakaoUserInfoRequestDto.getEmail());

        if (existingMember.isPresent()) {
            // 이미 존재하는 회원이 있을 경우 해당 회원을 반환
            return existingMember.get();
        } else {
            // 새로운 회원을 생성
            Members newKakaoMember = new Members(
                    kakaoUserInfoRequestDto.getEmail(),
                    kakaoUserInfoRequestDto.getNick(),
                    null, // 초기 phoneNumber는 null로 설정
                    kakaoUserInfoRequestDto.getKakaoId(),
                    LoginType.KAKAO // LoginType을 명시적으로 설정
            );

            // 회원을 저장하고 반환
            membersRepository.save(newKakaoMember);
            return newKakaoMember;
        }
    }
    // private Members findOrCreateKakaoMember(KakaoUserInfoRequestDto kakaoUserInfoRequestDto) {
    //     return membersRepository.findByEmail(kakaoUserInfoRequestDto.getEmail())
    //             .orElseGet(() -> {
    //                 Members newKakaoMember = new Members(
    //                         kakaoUserInfoRequestDto.getEmail(),
    //                         kakaoUserInfoRequestDto.getNick(),
    //                         null, // 초기 phoneNumber는 null로 설정
    //                         kakaoUserInfoRequestDto.getKakaoId(),
    //                         LoginType.KAKAO // loginType을 명시적으로 설정
    //                 );
    //                 membersRepository.save(newKakaoMember);
    //                 return newKakaoMember;
    //             });
    // }
}
