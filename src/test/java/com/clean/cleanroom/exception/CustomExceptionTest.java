package com.clean.cleanroom.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomExceptionTest {

    @Test
    void testCustomExceptionCreation() {
        // Given
        ErrorMsg errorMsg = ErrorMsg.COMMISSION_NOT_FOUND; // 예시로 하나의 ErrorMsg 사용

        // When
        CustomException exception = new CustomException(errorMsg);

        // Then
        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus()); // ErrorMsg에 정의된 HttpStatus와 일치하는지 확인
        assertEquals(4003, exception.getCode()); // ErrorMsg에 정의된 코드와 일치하는지 확인
    }
}

/*
예외처리 클래스 테스트 : 해당 예외가 올바르게 생성되고, 필요한 속성들이 올바르게 설정되는지를 확인함
생성자와 getter가 올바르게 작동하는지 확인한다
 */