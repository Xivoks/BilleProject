package com.example.demo.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ErrorDto {
    private int errorCode;
    private String errorMessage;
    private LocalDateTime errorDate;

    public ErrorDto(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorDate = LocalDateTime.now();
    }
}
