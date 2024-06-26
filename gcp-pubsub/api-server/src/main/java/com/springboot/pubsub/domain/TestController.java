package com.springboot.pubsub.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping("/topic/create")
    public ResponseEntity<?> createTopic() {
        return ResponseEntity.ok(testService.createTopic());
    }

    @PostMapping("/publish/message")
    public ResponseEntity<?> publishMessage() {
        return ResponseEntity.ok(testService.publishMessage());
    }
}
