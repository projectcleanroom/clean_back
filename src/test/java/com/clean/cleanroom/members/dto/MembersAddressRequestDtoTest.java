package com.clean.cleanroom.members.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MembersAddressRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void givenNullValues_whenValidate_thenViolations() {
        // Given: 모든 필드를 기본값(null)으로 초기화
        MembersAddressRequestDto requestDto = new MembersAddressRequestDto();

        // When: 유효성 검사 수행
        Set<ConstraintViolation<MembersAddressRequestDto>> violations = validator.validate(requestDto);

        // Then: address 필드에 대한 유효성 검사 실패를 확인
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
                violation.getMessage().equals("주소는 필수 입력 항목입니다.")
        );
    }

    @Test
    void givenEmptyAddress_whenValidate_thenViolationOccurs() {
        // Given: 필드가 빈 문자열일 때 기본 생성자로 객체를 생성
        MembersAddressRequestDto requestDto = new MembersAddressRequestDto();

        // When: 유효성 검사 수행
        Set<ConstraintViolation<MembersAddressRequestDto>> violations = validator.validate(requestDto);

        // Then: address 필드에 대한 유효성 검사 실패를 확인
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
                violation.getMessage().equals("주소는 필수 입력 항목입니다.")
        );
    }
}
