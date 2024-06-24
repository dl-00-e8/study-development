package com.spring.security.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.security.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(title= "사용자 정보 DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse(
        @Schema()
        Long id,

        @Schema()
        String name,

        @Schema()
        String email,

        @Schema(description = "사용자 권한 (GENERAL: 일반 | ADMIN: 관리자)", example = "GENERAL")
        String role
) {
    public static UserResponse of(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().getKoreanName())
                .build();
    }
}
