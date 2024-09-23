package com.clean.cleanroom.payment.service;

import com.clean.cleanroom.commission.entity.Commission;
import com.clean.cleanroom.commission.repository.CommissionRepository;
import com.clean.cleanroom.enums.CleanType;
import com.clean.cleanroom.enums.PaymentStatusType;
import com.clean.cleanroom.estimate.entity.Estimate;
import com.clean.cleanroom.estimate.repository.EstimateRepository;
import com.clean.cleanroom.payment.dto.*;
import com.clean.cleanroom.payment.entity.PaymentEntity;
import com.clean.cleanroom.payment.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public PaymentPrepareResponseDto preparePayment(PaymentPrepareRequestDto requestDto) {
        // merchant_uid 중복 검사
        if (paymentRepository.existsByMerchantUid(requestDto.getMerchantUid())) {
            throw new IllegalArgumentException("이미 사용 중인 merchant_uid입니다.");
        }

        // 포트원 API 호출하여 결제 사전 등록
        try {
            return portOneService.preparePayment(requestDto);
        } catch (HttpClientErrorException e) {
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
                commission.getMembers().getNick(), commission.getMembers().getPhoneNumber(), commission.getMembers().getEmail(), commission.getCleanType()
        );
    }

    public PaymentResponseDto completePayment(String impUid, PaymentRequestDto requestDto) {
        // PortOneService에서 accessToken을 내부에서 처리하여 결제 내역을 가져옴
        PaymentDetailResponseDto portOneResponse = portOneService.getPaymentDetails(impUid);

        if (portOneResponse == null || !portOneResponse.getResponse().getStatus().equals("paid")) {
            throw new IllegalArgumentException("유효하지 않은 결제 정보입니다.");
        }
        String transactionDateString = portOneResponse.getResponse().getTransactionDate();
        Date transactionDate;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 포맷을 변경 필요시 변경
            transactionDate = formatter.parse(transactionDateString);
        } catch (ParseException e) {
            throw new RuntimeException("날짜 형식이 잘못되었습니다: " + transactionDateString, e);
        }

        PaymentEntity paymentEntity = paymentRepository.findByImpUid(impUid)
                .orElseGet(() -> PaymentEntity.builder()
                        .impUid(impUid)
                        .pgProvider(portOneResponse.getResponse().getPgProvider())
                        .name(CleanType.valueOf(portOneResponse.getResponse().getName().toUpperCase()))  // 수정: cleanType 대신 name 필드 사용
                        .amount(portOneResponse.getResponse().getAmount())
                        .merchantUid(portOneResponse.getResponse().getMerchantUid())
                        .status(PaymentStatusType.valueOf(portOneResponse.getResponse().getStatus().toUpperCase()))
                        .payMethod(requestDto.getPayMethod())
                        .transactionDate(transactionDate)
                        .buyerEmail(requestDto.getBuyerEmail())
                        .buyerTel(requestDto.getBuyerTel())
                        .isRequestCancelled(false)
                        .build());

        paymentEntity.completePayment();
        paymentRepository.save(paymentEntity);

        PaymentResponseDto.PaymentDetail paymentDetail = new PaymentResponseDto.PaymentDetail(
                paymentEntity.getImpUid(),
                paymentEntity.getMerchantUid(),
                paymentEntity.getStatus().name(),
                paymentEntity.getAmount(),
                paymentEntity.getPayMethod().name()
        );

        return new PaymentResponseDto(0, "결제 완료", paymentDetail);
    }

    public CancelPaymentResponseDto cancelPayment(String impUid, CancelPaymentRequestDto requestDto) {
        CancelPaymentResponseDto response = portOneService.cancelPayment(requestDto);

        PaymentEntity paymentEntity = paymentRepository.findByImpUid(impUid)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 결제입니다."));
        paymentEntity.cancelRequest();
        paymentRepository.save(paymentEntity);

        return response;
    }

    public PaymentDetailResponseDto getPaymentDetails(String impUid) {
        return portOneService.getPaymentDetails(impUid);
    }

    private PaymentEntity verifyAndSavePayment(String impUid, PaymentRequestDto requestDto) {
        PaymentEntity paymentEntity = paymentRepository.findByImpUid(impUid)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 결제입니다."));

        paymentEntity.completePayment();
        return paymentEntity;
    }
}