package com.spring.security.domain.user.facade;

import com.spring.security.domain.user.dto.response.UserResponse;
import com.spring.security.domain.user.entity.User;
// import com.youdongsan.crm_api.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

//    private final UserService userService;

    public UserResponse getUser(User user) {
        return UserResponse.of(user);
    }
}
