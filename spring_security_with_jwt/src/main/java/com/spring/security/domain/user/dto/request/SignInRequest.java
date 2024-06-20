package com.spring.security.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record SignInRequest(
        @NotEmpty
        @Schema(description = "아이디", example = "test@test.com")
        String email,

        @NotEmpty
        @Schema(description = "비밀번호", example = "testest123")
        String password
) {
}
