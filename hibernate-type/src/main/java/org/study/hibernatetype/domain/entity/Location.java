package org.study.hibernatetype.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.study.hibernatetype.domain.vo.City;

import java.util.List;
import java.util.Map;

@Getter
@Entity
@Table(name = "location")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city", columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private City city;

    @Column(name = "free_city", columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, List<String>> freeCity;

    @Builder
    public Location(City city, Map<String, List<String>> freeCity) {
        this.city = city;
        this.freeCity = freeCity;
    }
}
