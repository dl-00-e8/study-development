package com.youdongsan.crm_api.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "CRM API Sever",
                description = "CRM 서비스 관련 서버 중 API 서버 명세서",
                version = "v2"
        )
)
public class SwaggerConfig {

    // TODO: JWT 인증 관련 로직 도입 시, Swagger 업데이트 필요
    private static final String tokenPrefix = "Bearer ";

    @Bean
    @Profile("!Prod") // Production 환경에서는 접근할 수 없도록 제약 조건 추가
    public OpenAPI openAPI() {
//        String jwtSchemeName = JwtTokenProvider.AUTHORIZATION_HEADER;
//        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        SecurityRequirement securityRequirement = new SecurityRequirement();
        Components components = new Components();
//                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
//                        .name(jwtSchemeName)
//                        .type(SecurityScheme.Type.HTTP)
//                        .scheme(BEARER_TOKEN_PREFIX)
//                        .bearerFormat(JwtTokenProvider.TYPE));

        // Swagger UI 접속 후, 딱 한 번만 accessToken을 입력해주면 모든 API에 토큰 인증 작업이 적용됩니다.
        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
