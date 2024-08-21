package com.clean.cleanroom.commission.dto;

import lombok.Getter;

@Getter
public class CommissionFileGetResponseDto {
    private String file;
    private String message;
    private byte[] fileData;

    public CommissionFileGetResponseDto(String file, String message, byte[] fileData) {
        this.file = file;
        this.message = message;
        this.fileData = fileData;
    }
}
