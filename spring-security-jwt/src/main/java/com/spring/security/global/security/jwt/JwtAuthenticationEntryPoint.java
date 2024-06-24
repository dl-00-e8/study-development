package com.spring.security.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.security.global.exception.ErrorCode;
import com.spring.security.global.exception.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        Object exception = request.getAttribute("exception");

        // exception에 할당된 속성이 ErrorCode일 경우, 관련된 응답 객체 정보를 삽입하도록 설정
        if (exception instanceof ErrorCode) {
            setResponse(response, (ErrorCode) exception);

            return;
        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }

    /**
     * Error 관련 응답 Response -> ServletResponse 저장하는 메소드
     * @param response ServletResponse 객체
     * @param errorCode 발생한 에러 정보를 담고 있는 객체
     * @throws IOException IO 과정에서 예외
     */
    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorCode.getHttpStatus().value());

        ExceptionResponse exceptionResponse = ExceptionResponse.of(errorCode);
        String errorJson = objectMapper.writeValueAsString(exceptionResponse);

        response.getWriter().write(errorJson);
    }
}
