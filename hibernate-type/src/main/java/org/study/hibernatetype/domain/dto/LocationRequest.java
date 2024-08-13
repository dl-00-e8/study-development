package org.study.hibernatetype.domain.dto;

import java.util.List;

public record LocationRequest(
    String city,
    List<DivisionRequest> divisionList
) {
    public record DivisionRequest(
            String district
    ) {
    }
}
