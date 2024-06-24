package com.spring.security.domain.user.controller;

import com.spring.security.domain.user.dto.request.SignInRequest;
import com.spring.security.domain.user.dto.request.SignUpRequest;
import com.spring.security.domain.user.dto.response.SignInResponse;
import com.spring.security.domain.user.dto.response.UserResponse;
import com.spring.security.domain.user.facade.AuthFacade;
import com.spring.security.global.common.ApplicationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "인증", description = "사용자 회원가입/로그인 등 인증과 관련된 API")
@Slf4j
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthFacade authFacade;

    @PostMapping("/sign-up")
    @Operation(summary = "회원가입", description = "사용자의 정보 입력 후 회원가입에 따른 성공/실패 여부 반환")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "1001", description = "성공 시 반환되는 코드", content = {@Content(schema = @Schema(implementation = UserResponse.class))}),
                    @ApiResponse(responseCode = "2000", description = "입력 값이 유효하지 않거나, 서버 내부에서 오류가 발생했을 때 반환하는 에러"),
                    @ApiResponse(responseCode = "3002", description = "비밀번호와 비밀번호 확인이 일치하지 않을 경우 반환하는 에러")
            })
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity
                .created(URI.create("v1/auth/sign-in"))
                .body(ApplicationResponse.created(authFacade.signUp(signUpRequest)));
    }

    @PostMapping("/sign-in")
    @Operation(summary = "로그인", description = "사용자의 정보 입력 후 회원가입에 따른 성공/실패 여부 반환")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "1000", description = "성공 시 반환되는 코드", content = {@Content(schema = @Schema(implementation = SignInResponse.class))}),
                    @ApiResponse(responseCode = "2000", description = "입력 값이 유효하지 않거나, 서버 내부에서 오류가 발생했을 때 반환하는 에러"),
                    @ApiResponse(responseCode = "3003", description = "비밀번호가 올바르지 않아서 발생하는 에러")
            })
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        return ResponseEntity
                .ok()
                .body(ApplicationResponse.ok(authFacade.signIn(signInRequest)));
    }
}
