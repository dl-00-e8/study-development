package com.spring.security.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.security.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(title = "로그인 후 사용자 정보 및 토큰을 반환하는 DTO")
public record SignInResponse(
        @Schema()
        Long id,

        @Schema()
        String name,

        @Schema()
        String email,

        @Schema(description = "사용자 권한 (GENERAL: 일반 | ADMIN: 관리자)", example = "GENERAL")
        String role,

        @Schema(description = "액세스 토큰")
        String token
) {
    public static SignInResponse of(User user, String token) {
        return SignInResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().getKoreanName())
                .token(token)
                .build();
    }
}
