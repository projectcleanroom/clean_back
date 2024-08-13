package com.clean.cleanroom.exception;

import com.clean.cleanroom.util.ResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingRequestHeaderException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleCustomException() {
        // Given: 테스트 데이터 준비
        CustomException customException = new CustomException(ErrorMsg.COMMISSION_NOT_FOUND);

        // When: 테스트 대상 메서드 호출
        ResponseEntity<ResponseDto> response = globalExceptionHandler.handleCustomException(customException);

        // Then: 테스트 결과 검증
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(4003, response.getBody().getCode());
    }

    @Test
    void bindException() {
        // Given
        BindException bindException = mock(BindException.class);

        // When
        ResponseEntity<ResponseDto> response = globalExceptionHandler.bindException(bindException);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(1000, response.getBody().getCode());
    }

    @Test
    void missingRequestHeaderException() {
        // Given
        MissingRequestHeaderException exception = mock(MissingRequestHeaderException.class);

        // When
        ResponseEntity<ResponseDto> response = globalExceptionHandler.missingRequestHeaderException(exception);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(2000, response.getBody().getCode());
    }

    @Test
    void handleAll() {
        // Given
        Exception exception = new Exception("General exception");

        // When
        ResponseEntity<ResponseDto> response = globalExceptionHandler.handleAll(exception);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(9999, response.getBody().getCode());
    }

    @Test
    void handleIOException() {
        // Given
        IOException ioException = new IOException("IO Exception");

        // When
        ResponseEntity<ResponseDto> response = globalExceptionHandler.handleIOException(ioException);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(9998, response.getBody().getCode());
    }
}
