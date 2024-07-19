package org.spring.presignedurl.domain;

import lombok.RequiredArgsConstructor;
import org.spring.presignedurl.global.gcs.GcsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
public class TestController {

    private final GcsService gcsService;

    @PostMapping("/generate-signed-url")
    public ResponseEntity<?> generateSignedURL(@RequestParam("file") String objectName) {
        return ResponseEntity.ok().body(gcsService.generateSignedURL(objectName));
    }
}
