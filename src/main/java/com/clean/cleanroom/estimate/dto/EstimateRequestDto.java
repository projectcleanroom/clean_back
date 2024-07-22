package com.clean.cleanroom.estimate.dto;

import lombok.Getter;
import java.time.LocalDate;

@Getter
public class EstimateRequestDto {

    private Long Id;

    private int price;

    private String statement;

    private LocalDate fixedDate;

}

