package com.spring.security.domain.user.service;

import com.spring.security.domain.user.dto.request.SignUpRequest;
import com.spring.security.domain.user.entity.Credential;
import com.spring.security.domain.user.entity.User;
import com.spring.security.domain.user.repository.CredentialRepository;
import com.spring.security.global.exception.ApplicationException;
import com.spring.security.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class CredentialService {

    private final PasswordEncoder passwordEncoder;
    private final CredentialRepository credentialRepository;

    public Credential createCredential(SignUpRequest signUpRequest) {
        // Validation
        if(!signUpRequest.password().equals(signUpRequest.passwordCheck())) {
            throw new ApplicationException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        // Business Logic
        String hashedPassword = passwordEncoder.encode(signUpRequest.password());
        Credential credential = Credential.builder()
                .hashedPassword(hashedPassword)
                .build();

        // Response
        return credentialRepository.save(credential);
    }

    public void checkPassword(User user, String password) {
        // Validation
        Credential credential = user.getCredential();

        System.out.println(credential.getPassword());
        System.out.println(password);
        // Business Logic
        if(!passwordEncoder.matches(password, credential.getPassword())) {
            throw new ApplicationException(ErrorCode.PASSWORD_INVALID);
        }
    }
}
