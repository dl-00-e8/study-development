package com.springboot.pubsub.domain;

public record EmailReq(
        String title,
        String body,
        String receiver
) {

    public Email from() {
        return new Email(
                title,
                body,
                receiver
        );
    }
}
