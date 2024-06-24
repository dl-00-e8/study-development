package com.spring.security.global.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ExceptionResponse(
        LocalDateTime timestamp,
        Integer code,
        String message
) {

    public static ExceptionResponse of(ErrorCode errorCode) {
        return ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

    }

    public static ExceptionResponse of(String message) {
        return ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .code(ErrorCode.INTERNAL_SERVER_ERROR.getCode())
                .message(message)
                .build();

    }
}
