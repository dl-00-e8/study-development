package org.study.hibernatetype.domain.vo;

import java.util.List;

public record City(
        String city,
        List<Division> divisionList
) {

    public record Division(
            String district
    ) {
    }
}
