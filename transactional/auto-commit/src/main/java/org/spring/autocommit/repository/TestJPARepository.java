package org.spring.autocommit.repository;

import org.spring.autocommit.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestJPARepository extends JpaRepository<Test, Long> {
}
