package com.spring.security.domain.user.dto.request;

import com.spring.security.domain.user.entity.Credential;
import com.spring.security.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
@Schema(title = "회원가입 요청 DTO")
public record SignUpRequest(
        @NotEmpty
        @Schema(description = "사용자 이름", example = "유재석")
        String name,

        @NotEmpty
        @Schema(description = "사용지 이메일(ID로 사용)", example = "test@test.com")
        String email,

        @NotEmpty
        @Schema(description = "사용자 비밀번호", example = "testest123")
        String password,

        @NotEmpty
        @Schema(description = "사용자 비밀번호 확인", example = "testest123")
        String passwordCheck
) {

        public User from(Credential credential) {
                return User.builder()
                        .credential(credential)
                        .name(this.name)
                        .email(this.email)
                        .build();
        }
}
