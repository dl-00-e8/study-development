package com.spring.security.domain.user.service;

import com.spring.security.domain.user.dto.request.SignUpRequest;
import com.spring.security.domain.user.entity.Credential;
import com.spring.security.domain.user.entity.User;
import com.spring.security.domain.user.enumerate.UserRole;
import com.spring.security.domain.user.repository.UserRepository;
import com.spring.security.global.exception.ApplicationException;
import com.spring.security.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void checkAdmin(User user) {
        if(!user.getRole().equals(UserRole.ADMIN)) {
            throw new ApplicationException(ErrorCode.UNAUTHORIZED);
        }
    }

    public User createUser(Credential credential, SignUpRequest signUpRequest) {
        // Business Logic + Response
        User user = signUpRequest.from(credential);
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        // Business Logic + Response
        return userRepository.findByEmail(email).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
    }
}
