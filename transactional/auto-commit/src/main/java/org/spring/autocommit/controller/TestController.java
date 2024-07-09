package org.spring.autocommit.controller;

import lombok.RequiredArgsConstructor;
import org.spring.autocommit.service.TestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping("/{data}")
    public ResponseEntity<?> create(@PathVariable("data") String data) {
        return ResponseEntity.ok().body(testService.create(data));
    }
}
