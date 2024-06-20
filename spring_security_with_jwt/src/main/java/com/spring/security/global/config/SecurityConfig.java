package com.spring.security.global.config;

import com.spring.security.global.security.ExceptionFilter;
import com.spring.security.global.security.jwt.JwtAccessDeniedHandler;
import com.spring.security.global.security.jwt.JwtAuthenticationEntryPoint;
import com.spring.security.global.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final ExceptionFilter exceptionFilter;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);

        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));// Session 미사용

        // httpBasic, httpFormLogin 비활성화
        http.httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);

        // JWT 관련 필터 설정 및 예외 처리
        http.exceptionHandling((exceptionHandling) ->
                exceptionHandling
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(exceptionFilter, JwtFilter.class);

        // 요청 URI별 권한 설정
        http.authorizeHttpRequests((authorize) ->
                // Swagger UI 외부 접속 허용
                authorize.requestMatchers( "/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        // 로그인 로직 접속 허용
                        .requestMatchers("/v1/auth/**").permitAll()
                        // DefaultExceptionHandler 처리를 위한 error PermitAll
                        .requestMatchers("/error/**").permitAll()
                        // 이외의 모든 요청은 인증 정보 필요
                        .anyRequest().authenticated());

        return http.build();
    }

    /**
     * CORS 허용하도록 커스터마이징 진행
     * @return - 변경된 CORS 정책 정보 반환
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // 인증정보 주고받도록 허용
        config.setAllowCredentials(true);
        // 허용할 주소
        config.setAllowedOrigins(List.of("*"));
        // 허용하고자 하는 HTTP Method
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        // 허용할 헤더 정보
        config.setAllowedHeaders(List.of("*"));
        // 노출시킬 헤더 정보
        config.setExposedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 비밀번호 함호화 방식 설정
        // TODO: Elixir에서 사용한 암호화 방식 확인 필요 및 해당 암호화 방식으로 적용
        return new BCryptPasswordEncoder();
    }
}
