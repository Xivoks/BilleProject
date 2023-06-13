package com.example.demo.exception;

import com.example.demo.dto.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDto> handleCustomException(CustomException exception) {
        ErrorDto errorDto = new ErrorDto(exception.getErrorCode(), exception.getMessage());
        return ResponseEntity.status(exception.getHttpStatus()).body(errorDto);
    }
}
