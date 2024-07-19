package org.spring.presignedurl.domain;

import lombok.RequiredArgsConstructor;
import org.spring.presignedurl.global.gcs.GcsService;
import org.spring.presignedurl.global.s3.S3Service;
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
    private final S3Service s3Service;

    @PostMapping("/generate-signed-url")
    public ResponseEntity<?> generateSignedURL(@RequestParam("file") String objectName) {
        return ResponseEntity.ok().body(gcsService.generateSignedURL(objectName));
    }

    @PostMapping("/generate-presigned-url")
    public ResponseEntity<?> generatePresignedURL(@RequestParam("file") String objectName) {
        return ResponseEntity.ok().body(s3Service.generatePreSignedUrl(objectName));
    }
}
