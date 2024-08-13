package org.study.hibernatetype.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.study.hibernatetype.domain.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long>, LocationRepositoryCustom {
}
