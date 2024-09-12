package com.clean.cleanroom.payment.service;

import com.clean.cleanroom.enums.PayMethod;
import com.clean.cleanroom.enums.PaymentStatusType;
import com.clean.cleanroom.payment.dto.*;
import com.clean.cleanroom.payment.entity.PaymentEntity;
import com.clean.cleanroom.payment.repository.PaymentRepository;
import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.commission.repository.CommissionRepository;
import com.clean.cleanroom.estimate.repository.EstimateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Slf4j
@Service
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final EstimateRepository estimateRepository;
    private final CommissionRepository commissionRepository;
    private final PortOneService portOneService;
    private final RestTemplate restTemplate;

    public PaymentService(PaymentRepository paymentRepository, EstimateRepository estimateRepository,
                          CommissionRepository commissionRepository, PortOneService portOneService, RestTemplate restTemplate) {
        this.paymentRepository = paymentRepository;
        this.estimateRepository = estimateRepository;
        this.commissionRepository = commissionRepository;
        this.portOneService = portOneService;
        this.restTemplate = restTemplate;
    }

    public PaymentPrepareResponseDto preparePayment(String accessToken, PaymentPrepareRequestDto requestDto) {
        // merchant_uid 생성
        String merchantUid = "order_" + UUID.randomUUID().toString();

        // requestDto에 merchant_uid 설정
        requestDto = new PaymentPrepareRequestDto(merchantUid, requestDto.getAmount());

        // HTTP 헤더에 Authorization 추가
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);
        log.info("Authorization Header: Bearer {}", accessToken);

        HttpEntity<PaymentPrepareRequestDto> entity = new HttpEntity<>(requestDto, headers);

//        // 포트원 API 호출하여 결제 사전 등록
//        return restTemplate.postForObject("https://api.iamport.kr/payments/prepare", entity, PaymentPrepareResponseDto.class);
//    }
        // 포트원 API 호출하여 결제 사전 등록
        try {
            return restTemplate.postForObject("https://api.iamport.kr/payments/prepare", entity, PaymentPrepareResponseDto.class);
        } catch (HttpClientErrorException e) {
            log.error("HttpClientErrorException: {}", e.getMessage());
            throw e;  // 예외를 다시 던져서 글로벌 예외 처리기에 잡히도록 함
        }
    }



    public EstimateAndCommissionResponseDto getEstimateAndCommissionData(Long estimateId, Long commissionId) {
        Estimate estimate = estimateRepository.findById(estimateId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 견적입니다."));
        Commission commission = commissionRepository.findById(commissionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 의뢰입니다."));

        return new EstimateAndCommissionResponseDto(
                estimate.getId(), commission.getId(), estimate.getPrice(),
                commission.getMembers().getNick(), commission.getMembers().getPhoneNumber(), commission.getMembers().getEmail()
        );
    }

    public PaymentResponseDto completePayment(String accessToken, String impUid, PaymentRequestDto requestDto) {
        // 로그 추가: impUid 및 Access Token 확인
        log.info("Requesting Payment Details with impUid: {}", impUid);
        String bearerToken = "Bearer " + accessToken;
        log.info("Using Access Token: {}", bearerToken);

        PaymentDetailResponseDto portOneResponse = portOneService.getPaymentDetails(bearerToken, impUid);
        if (portOneResponse == null || !portOneResponse.getResponse().getStatus().equals("paid")) {
            throw new IllegalArgumentException("유효하지 않은 결제 정보입니다.");
        }

        PaymentEntity paymentEntity = paymentRepository.findByImpUid(impUid)
                .orElseGet(() -> PaymentEntity.builder()
                        .impUid(impUid)
                        .pg_provider(portOneResponse.getResponse().getPg_provider())
                        .name(portOneResponse.getResponse().getName())
                        .amount(portOneResponse.getResponse().getAmount())
                        .merchant_uid(portOneResponse.getResponse().getMerchant_uid())
                        .status(PaymentStatusType.valueOf(portOneResponse.getResponse().getStatus().toUpperCase()))
                        .pay_method(PayMethod.valueOf(requestDto.getPay_method().toUpperCase()))
                        .transaction_date(portOneResponse.getResponse().getTransaction_date())
                        .buyer_email(requestDto.getBuyer_email())
                        .buyer_tel(requestDto.getBuyer_tel())
                        .is_request_cancelled(false)
                        .build());

        paymentEntity.completePayment();
        paymentRepository.save(paymentEntity);

        PaymentResponseDto.PaymentDetail paymentDetail = new PaymentResponseDto.PaymentDetail(
                paymentEntity.getImpUid(),
                paymentEntity.getMerchant_uid(),
                paymentEntity.getStatus().name(),
                paymentEntity.getAmount(),
                paymentEntity.getPay_method().name()
        );

        return new PaymentResponseDto(0, "결제 완료", paymentDetail);
    }

    public CancelPaymentResponseDto cancelPayment(String accessToken, String impUid, CancelPaymentRequestDto requestDto) {
        CancelPaymentResponseDto response = portOneService.cancelPayment(accessToken, requestDto);

        PaymentEntity paymentEntity = paymentRepository.findByImpUid(impUid)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 결제입니다."));
        paymentEntity.cancelRequest();
        paymentRepository.save(paymentEntity);

        return response;
    }

    public PaymentDetailResponseDto getPaymentDetails(String accessToken, String impUid) {
        return portOneService.getPaymentDetails(accessToken, impUid);
    }

    private PaymentEntity verifyAndSavePayment(String impUid, PaymentRequestDto requestDto) {
        PaymentEntity paymentEntity = paymentRepository.findByImpUid(impUid)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 결제입니다."));

        paymentEntity.completePayment();
        return paymentEntity;
    }
}