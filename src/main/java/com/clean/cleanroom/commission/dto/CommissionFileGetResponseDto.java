package com.clean.cleanroom.commission.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CommissionFileGetResponseDto {
    @Schema(description = "파일")
    private String file;
    private String message;
    @Schema(description = "파일 업로드 일짜", example = "2024-09-05T10:00:00")
    private byte[] fileData;

    public CommissionFileGetResponseDto(String file, String message, byte[] fileData) {
        this.file = file;
        this.message = message;
        this.fileData = fileData;
    }
}
