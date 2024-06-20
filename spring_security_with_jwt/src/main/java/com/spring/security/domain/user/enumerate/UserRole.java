package com.spring.security.domain.user.enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    GENERAL("일반 사용자"),
    ADMIN("관리자");

    private final String koreanName;
}
