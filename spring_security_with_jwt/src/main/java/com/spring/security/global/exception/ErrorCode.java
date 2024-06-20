package com.spring.security.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Success
    SUCCESS(HttpStatus.OK, 1000, "정상적인 요청입니다"),
    CREATED(HttpStatus.CREATED, 1001, "정상적으로 생성되었습니다."),

    // Common
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,2000, "서버에서 오류가 발생했습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, 2001, "허용되지 않은 요청입니다"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 2002, "권한이 없는 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, 2003, "정보를 찾을 수 없습니다."),
    EXCEL_EXTENSION(HttpStatus.BAD_REQUEST, 2004, "엑셀 파일 확장자가 일치하지 않습니다."),
    EXCEL_CONVERT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 2005, "엑셀 변환 중 오류가 발생했습니다."),
    EXCEL_HEADER(HttpStatus.BAD_REQUEST, 2006, "엑셀 헤더가 잘못되었습니다."),
    INVALID_VALUE(HttpStatus.BAD_REQUEST, 2007, "유효하지 않은 입력입니다."),

    // Authentication
    TOKEN_INVALID(HttpStatus.BAD_REQUEST, 3000, "유효하지 않은 토큰입니다"),
    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, 3001, "인증 정보가 없는 요청입니다"),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, 3002, "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    PASSWORD_INVALID(HttpStatus.BAD_REQUEST, 3003, "비밀번호가 올바르지 않습니다."),

    // User
    ALREADY_EXIST(HttpStatus.BAD_REQUEST, 4000, "이미 존재하는 사용자입니다."),

    // Customer
    LEVEL_INVALID(HttpStatus.BAD_REQUEST, 5000,"해당하는 레벨이 없습니다."),

    // Email
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, 6000, "발송하고자 하는 메일 수신자가 중복되어 있습니다.");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;
}
