package com.spring.security.global.config;

import com.spring.security.global.security.AuditAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class AuditConfig {

    @Bean
    public AuditorAware<Long> auditorProvider() {
        return new AuditAwareImpl();
    }
}
