package com.clean.cleanroom.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final int code;
    private final String details;  // 추가된 필드

    // ErrorMsg를 반환하는 메서드 추가
    private final ErrorMsg errorMsg;  // ErrorMsg 필드 추가

    public CustomException(ErrorMsg errorMsg) {
        super(errorMsg.getDetails());
        this.httpStatus = errorMsg.getHttpStatus();
        this.code = errorMsg.getCode();
        this.details = errorMsg.getDetails();  // 추가된 필드 초기화
        this.errorMsg = errorMsg;
    }
}
