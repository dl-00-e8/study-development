package com.spring.security.domain.test.service;

import com.spring.security.domain.test.entity.Test;
import com.spring.security.domain.test.repository.TestRepository;
import com.spring.security.domain.user.entity.User;
import com.spring.security.domain.user.enumerate.UserRole;
import com.spring.security.global.exception.ApplicationException;
import com.spring.security.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestService {

    private final TestRepository testRepository;

    @Transactional
    public Test register(User user) {
        // Validation
        if(user.getRole() != UserRole.ADMIN) {
            throw new ApplicationException(ErrorCode.UNAUTHORIZED);
        }

        // Business Logic
        Test test = Test.builder()
                .email(user.getEmail())
                .build();

        // Response
        return testRepository.save(test);
    }
}
