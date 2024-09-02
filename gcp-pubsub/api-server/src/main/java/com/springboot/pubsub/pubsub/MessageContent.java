package com.springboot.pubsub.pubsub;

public record MessageContent(
        Long id,
        String title,
        String body,
        String receiver
) {
}
