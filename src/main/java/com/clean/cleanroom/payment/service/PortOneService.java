package com.clean.cleanroom.payment.service;

import com.clean.cleanroom.payment.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class PortOneService {

    private final RestTemplate restTemplate;

    public PortOneService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // PortOne 토큰 발급
    private PortOneTokenResponseDto getPortOneAccessToken() {
        String url = "https://api.iamport.kr/users/getToken"; // PortOne 토큰 발급 URL
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Port One의 API 키와 시크릿을 요청 본문에 설정
        PortOneTokenRequestDto requestDto = new PortOneTokenRequestDto("your_imp_key", "your_imp_secret");
        HttpEntity<PortOneTokenRequestDto> entity = new HttpEntity<>(requestDto, headers);

        return restTemplate.postForObject(url, entity, PortOneTokenResponseDto.class);
    }

    // 결제 금액 사전 등록
    public PaymentPrepareResponseDto preparePayment(String accessToken, PaymentPrepareRequestDto requestDto) {
        String url = "https://api.iamport.kr/payments/prepare?_token=" + accessToken;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PaymentPrepareRequestDto> entity = new HttpEntity<>(requestDto, headers);

        return restTemplate.postForObject(url, entity, PaymentPrepareResponseDto.class);
    }

    // 결제 취소
    public CancelPaymentResponseDto cancelPayment(String accessToken, CancelPaymentRequestDto requestDto) {
        String url = "https://api.iamport.kr/payments/prepare?_token=" + accessToken;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CancelPaymentRequestDto> entity = new HttpEntity<>(requestDto, headers);

        return restTemplate.postForObject(url, entity, CancelPaymentResponseDto.class);
    }

    // 결제 내역 조회
    public PaymentDetailResponseDto getPaymentDetails(String accessToken, String impUid) {
        String url = "https://api.iamport.kr/payments/" + impUid + "?_token=" + accessToken;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<PaymentDetailResponseDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, PaymentDetailResponseDto.class);
        return response.getBody();
    }
}

