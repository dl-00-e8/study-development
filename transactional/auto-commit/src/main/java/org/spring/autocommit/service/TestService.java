package org.spring.autocommit.service;

import lombok.RequiredArgsConstructor;
import org.spring.autocommit.entity.Test;
import org.spring.autocommit.repository.TestJPARepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TestService {

    private final TestJPARepository testJPARepository;

    @Transactional
    public String create(String data) {
        // Business Logic
        Test test = Test.builder()
                .data(data)
                .build();
        Test savedTest = testJPARepository.save(test);

        // Response
        return savedTest.getData();
    }
}
