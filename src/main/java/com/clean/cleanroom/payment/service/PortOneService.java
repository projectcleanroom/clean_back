package com.clean.cleanroom.payment.service;

import com.clean.cleanroom.config.PortOneConfig;
import com.clean.cleanroom.payment.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class PortOneService {

    private final PortOneConfig portOneConfig;
    private final RestTemplate restTemplate;
    private String currentAccessToken;


    public PortOneService(PortOneConfig portOneConfig, RestTemplate restTemplate) {
        this.portOneConfig = portOneConfig;
        this.restTemplate = restTemplate;

    }

    // PortOne 토큰 발급
    public PortOneTokenResponseDto getPortOneAccessToken() {
        String url = "https://api.iamport.kr/users/getToken"; // PortOne 토큰 발급 URL
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Port One의 API 키와 시크릿을 요청 본문에 설정
        PortOneTokenRequestDto requestDto = new PortOneTokenRequestDto(portOneConfig.getApiKey(), portOneConfig.getApiSecret());
        // 요청 객체와 JSON 직렬화된 결과 로그 출력
        try {
            log.info("Request URL: {}", url);
            log.info("Request Headers: {}", headers);
            log.info("Request DTO: {}", new ObjectMapper().writeValueAsString(requestDto));
        } catch (JsonProcessingException e) {
            log.error("Error serializing request DTO: {}", e.getMessage());
        }

        HttpEntity<PortOneTokenRequestDto> entity = new HttpEntity<>(requestDto, headers);

        return restTemplate.postForObject(url, entity, PortOneTokenResponseDto.class);

    }

    // 액세스 토큰을 가져오는 메서드
    private String getAccessToken() {
        // 현재 토큰이 없거나 만료된 경우 새 토큰을 발급
        if (currentAccessToken == null) {
            log.info("No current access token or token expired. Requesting new token...");
            PortOneTokenResponseDto responseDto = getPortOneAccessToken();
            if (responseDto != null && responseDto.getResponse() != null && responseDto.getResponse().getAccessToken() != null) {
                currentAccessToken = responseDto.getResponse().getAccessToken();
                log.info("Access Token 발급 완료: {}", currentAccessToken);
            } else {
                log.error("Failed to retrieve the access token.");
                throw new IllegalStateException("Failed to retrieve the access token.");
            }
        } else {
            log.info("Using cached Access Token: {}", currentAccessToken);
        }
        return currentAccessToken;
    }

    // 결제 금액 사전 등록
    public PaymentPrepareResponseDto preparePayment(PaymentPrepareRequestDto requestDto) {
        String accessToken = getAccessToken();  // 내부에서 토큰 관리
        String url = portOneConfig.getApiUrl() + "/payments/prepare?_token=" + accessToken;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PaymentPrepareRequestDto> entity = new HttpEntity<>(requestDto, headers);

        return restTemplate.postForObject(url, entity, PaymentPrepareResponseDto.class);
    }

    // 결제 취소
    public CancelPaymentResponseDto cancelPayment(CancelPaymentRequestDto requestDto) {
        String accessToken = getAccessToken();  // 내부에서 토큰 관리
        String url = portOneConfig.getApiUrl() + "/payments/prepare?_token=" + accessToken;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CancelPaymentRequestDto> entity = new HttpEntity<>(requestDto, headers);

        return restTemplate.postForObject(url, entity, CancelPaymentResponseDto.class);
    }

    // 결제 내역 조회
    public PaymentDetailResponseDto getPaymentDetails(String impUid) {
        String accessToken = getAccessToken();  // 내부에서 토큰 관리
        String url = portOneConfig.getApiUrl() + "/payments/" + impUid + "?_token=" + accessToken;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<PaymentDetailResponseDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, PaymentDetailResponseDto.class);
        return response.getBody();
    }
}

