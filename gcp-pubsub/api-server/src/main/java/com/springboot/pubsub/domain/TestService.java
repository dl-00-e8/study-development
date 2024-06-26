package com.springboot.pubsub.domain;

import com.springboot.pubsub.pubsub.PubSubClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    private final PubSubClient pubSubClient;

    public String createTopic() {
        return "test";
    }

    public String publishMessage() {
        return "test";
    }
}
