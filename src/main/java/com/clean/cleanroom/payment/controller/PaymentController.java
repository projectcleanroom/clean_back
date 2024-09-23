package com.clean.cleanroom.payment.controller;

import com.clean.cleanroom.payment.dto.*;
import com.clean.cleanroom.payment.service.PaymentService;
import com.clean.cleanroom.payment.service.PortOneService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;
    private final PortOneService portOneService;

    public PaymentController(PaymentService paymentService, PortOneService portOneService) {
        this.paymentService = paymentService;
        this.portOneService = portOneService;
    }

    /**
     * PortOne 액세스 토큰 발급 테스트 API
     * GET /api/payments/token
     * Port One의 API를 호출하여 액세스 토큰을 발급받아 확인하는 테스트 엔드포인트
     *
     * @return 발급된 PortOneTokenResponseDto 객체
     */
    @GetMapping("/token")
    public ResponseEntity<PortOneTokenResponseDto> getAccessToken() {
        PortOneTokenResponseDto tokenResponse = portOneService.getPortOneAccessToken();
        return ResponseEntity.ok(tokenResponse);
    }

    /**
     * 견적 및 의뢰 정보를 프론트엔드로 제공하는 API
     * GET /api/payments/data?estimateId=11&commissionId=22
     * @param estimateId   견적 ID
     * @param commissionId 의뢰 ID
     * @return EstimateAndCommissionResponseDto 견적 및 의뢰 정보
     */
    @GetMapping("/data")
    public ResponseEntity<EstimateAndCommissionResponseDto> getEstimateAndCommissionData(
            @RequestParam Long estimateId, @RequestParam Long commissionId) {

        EstimateAndCommissionResponseDto data = paymentService.getEstimateAndCommissionData(estimateId, commissionId);
        return ResponseEntity.ok(data);
    }

    /**
     * 결제 금액 사전 등록 API
     * /payments/prepare?_token=
     * Port One의 API를 호출하여 결제 금액을 사전 등록합니다.
     *
     * @param paymentPrepareRequestDto 결제 금액 사전 등록 요청 데이터
     * @return PaymentPrepareResponseDto 결제 금액 사전 등록에 대한 응답 데이터
     */
    @PostMapping("/prepare")
    public ResponseEntity<PaymentPrepareResponseDto> preparePayment(
            @Valid @RequestBody PaymentPrepareRequestDto paymentPrepareRequestDto) {

        PaymentPrepareResponseDto paymentPrepareResponseDto = paymentService.preparePayment(paymentPrepareRequestDto);
        return ResponseEntity.ok(paymentPrepareResponseDto);
    }

    /**
     * 결제 완료 처리 API
     * <p>
     * 결제가 완료된 후, 클라이언트에서 전달된 impUid를 사용하여 결제 상태를 확인하고
     * 결제 완료를 처리하는 엔드포인트입니다.
     *
     * @param impUid            PortOne의 고유 거래 ID
     * @param paymentRequestDto 결제 요청 데이터
     * @return PaymentResponseDto 결제 완료에 대한 응답 데이터
     */
    @PostMapping("/complete/{impUid}")
    public ResponseEntity<PaymentResponseDto> completePayment(
            @PathVariable("impUid") String impUid,
            @Valid @RequestBody PaymentRequestDto paymentRequestDto) {

        PaymentResponseDto paymentResponseDto = paymentService.completePayment(impUid, paymentRequestDto);
        return ResponseEntity.ok(paymentResponseDto);
    }

    /**
     * 결제 취소 처리 API
     * <p>
     * 결제를 취소하는 API 엔드포인트입니다.
     *
     * @param impUid           Port One의 고유 거래 ID
     * @param cancelRequestDto 결제 취소 요청 데이터
     * @return CancelPaymentResponseDto 결제 취소에 대한 응답 데이터
     */
    @PostMapping("/cancel/{impUid}")
    public ResponseEntity<CancelPaymentResponseDto> cancelPayment(
            @PathVariable("impUid") String impUid,
            @Valid @RequestBody CancelPaymentRequestDto cancelRequestDto) {

        CancelPaymentResponseDto cancelPaymentResponseDto = paymentService.cancelPayment(impUid, cancelRequestDto);
        return ResponseEntity.ok(cancelPaymentResponseDto);
    }

    /**
     * 결제 내역 조회 API
     * <p>
     * Port One의 API를 호출하여 결제 내역을 조회합니다.
     *
     * @param impUid      Port One의 고유 거래 ID
     * @return PaymentDetailResponseDto 결제 내역에 대한 응답 데이터
     */
    @GetMapping("/{impUid}")
    public ResponseEntity<PaymentDetailResponseDto> getPaymentDetails(
            @PathVariable("impUid") String impUid) {

        PaymentDetailResponseDto paymentDetail = paymentService.getPaymentDetails(impUid);
        return ResponseEntity.ok(paymentDetail);
    }

}


