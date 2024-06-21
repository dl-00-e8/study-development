package com.spring.security.domain.test.controller;

import com.spring.security.domain.test.service.TestService;
import com.spring.security.domain.user.dto.response.UserResponse;
import com.spring.security.domain.user.entity.User;
import com.spring.security.global.common.ApplicationResponse;
import com.spring.security.global.security.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping("")
    @Operation(summary = "사용자 조회", description = "토큰 제외하고, ")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "1000", description = "성공 시 반환되는 코드", content = {@Content(schema = @Schema(implementation = UserResponse.class))}),
                    @ApiResponse(responseCode = "3000", description = "토큰 정보가 유효하지 않을 떄 발생하는 에러"),
                    @ApiResponse(responseCode = "3001", description = "토큰이 필요하지만, 토큰 정보가 없이 요청이 오는 경우 발생하는 에러")
            })
    public ResponseEntity<?> getMember(@AuthUser User user) {
        return ResponseEntity
                .ok()
                .body(ApplicationResponse.ok(testService.register(user)));
    }
}
