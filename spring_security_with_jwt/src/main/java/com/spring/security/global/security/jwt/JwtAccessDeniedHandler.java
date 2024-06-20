package com.spring.security.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.security.global.exception.ErrorCode;
import com.spring.security.global.exception.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    /**
     * 인가 실패 관련 403 핸들링
     * @param request ServletRequest 객체
     * @param response ServletResponse 객체
     * @param accessDeniedException 접근권한 거부 예외 정보
     * @throws IOException IO 예외 가능성 처리
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        setResponse(response);
    }

    /**
     * Error 관련 응답 Response 생성 메소드
     * @param response ServletResponse 객체
     * @throws IOException IO 예외 가능성 처리
     */
    private void setResponse(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(ErrorCode.FORBIDDEN.getHttpStatus().value());

        ExceptionResponse errorResponse = ExceptionResponse.of(ErrorCode.FORBIDDEN);
        String errorJson = objectMapper.writeValueAsString(errorResponse);

        response.getWriter().write(errorJson);
    }
}
