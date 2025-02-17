package com.clean.cleanroom.exception;

import com.clean.cleanroom.util.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    // CustomException error
    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ResponseDto> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getMessage());
        return ResponseDto.toExceptionResponseEntity(e.getHttpStatus(), e.getCode());
    }

    // 정규식 error
    @ExceptionHandler({BindException.class})
    public ResponseEntity<ResponseDto> bindException(BindException e) {
        log.error("BindException occurred: ", e);
        return ResponseDto.toExceptionResponseEntity(HttpStatus.BAD_REQUEST, 1000);
    }

    // 토큰 없을 시 error
    @ExceptionHandler({MissingRequestHeaderException.class})
    public ResponseEntity<ResponseDto> missingRequestHeaderException(MissingRequestHeaderException e) {
        log.error("MissingRequestHeaderException occurred: ", e);
        return ResponseDto.toExceptionResponseEntity(HttpStatus.UNAUTHORIZED, 2000);
    }

    // QuoteNotFoundException 처리
    @ExceptionHandler({QuoteNotFoundException.class})
    public ResponseEntity<ResponseDto> handleQuoteNotFoundException(QuoteNotFoundException e) {
        log.error("QuoteNotFoundException occurred: ", e);
        return ResponseDto.toExceptionResponseEntity(
                ErrorMsg.ESTIMATE_NOT_FOUND.getHttpStatus(),
                ErrorMsg.ESTIMATE_NOT_FOUND.getCode()
        );
    }

    // InvalidQuoteException 처리
    @ExceptionHandler({InvalidQuoteException.class})
    public ResponseEntity<ResponseDto> handleInvalidQuoteException(InvalidQuoteException e) {
        log.error("InvalidQuoteException occurred: ", e);
        return ResponseDto.toExceptionResponseEntity(
                ErrorMsg.INVALID_PAYMENT_REQUEST.getHttpStatus(),
                ErrorMsg.INVALID_PAYMENT_REQUEST.getCode()
        );
    }



    // 500 error
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseDto> handleAll(final Exception ex) {
        log.error("Unexpected error occurred: ", ex);
        return ResponseDto.toExceptionResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, 9999);
    }

    @ExceptionHandler(value = {IOException.class})
    public ResponseEntity<ResponseDto> handleIOException(IOException ex) {
        log.error("IOException occurred: ", ex);
        return ResponseDto.toExceptionResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, 9998);
    }

    // UnAuthenticationException 처리
    @ExceptionHandler(value = {UnAuthenticationException.class})
    protected ResponseEntity<ResponseDto> handleUnAuthenticationException(UnAuthenticationException e) {
        log.error("UnAuthenticationException occurred: {}", e.getMessage());
        return ResponseDto.toExceptionResponseEntity(HttpStatus.UNAUTHORIZED, 4000);
    }
}
