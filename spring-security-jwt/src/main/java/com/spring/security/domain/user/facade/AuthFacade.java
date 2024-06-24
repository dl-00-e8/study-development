package com.spring.security.domain.user.facade;

import com.spring.security.domain.user.dto.request.SignInRequest;
import com.spring.security.domain.user.dto.request.SignUpRequest;
import com.spring.security.domain.user.dto.response.SignInResponse;
import com.spring.security.domain.user.dto.response.UserResponse;
import com.spring.security.domain.user.entity.Credential;
import com.spring.security.domain.user.entity.User;
import com.spring.security.domain.user.service.CredentialService;
import com.spring.security.domain.user.service.UserService;
import com.spring.security.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthFacade {

    private final UserService userService;
    private final CredentialService credentialService;
    private final JwtProvider jwtProvider;

    @Transactional
    public UserResponse signUp(SignUpRequest signUpRequest) {
        Credential credential = credentialService.createCredential(signUpRequest);
        User user = userService.createUser(credential, signUpRequest);

        return UserResponse.of(user);
    }

    @Transactional
    public SignInResponse signIn(SignInRequest signInRequest) {
        User user = userService.getUserByEmail(signInRequest.email());
        credentialService.checkPassword(user, signInRequest.password());
        String token = jwtProvider.generateToken(user);

        return SignInResponse.of(user, token);
    }
}
