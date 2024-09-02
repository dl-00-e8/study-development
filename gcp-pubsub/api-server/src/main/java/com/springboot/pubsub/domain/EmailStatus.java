package com.springboot.pubsub.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailStatus {

    PENDING("발송 대기 중"),
    SENT("발송 완료"),
    FAILED("발송 실패");

    private final String status;
}
