package com.spring.security.domain.user.repository;

import com.spring.security.domain.user.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<Credential, Long> {
}
