package com.spring.security.global.security;

import com.spring.security.domain.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public record CustomUserDetails(User user) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_" + user.getRole().toString());

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return ""; // 비밀번호는 별도로 관리하고 있으므로, 해당 메소드는 빈 문자열을 반환하도록 설정
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // JWT 인증방식의므로 접근 가능하도록 설정
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // JWT 인증방식의므로 접근 가능하도록 설정
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // JWT 인증방식의므로 접근 가능하도록 설정
    }

    @Override
    public boolean isEnabled() {
        return true; // JWT 인증방식의므로 접근 가능하도록 설정
    }
}
