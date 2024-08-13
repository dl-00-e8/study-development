package org.study.hibernatetype.domain.dto;

import java.util.List;
import java.util.Map;

public record FreeLocationRequest(
        Map<String, List<String>> city
) {
}
