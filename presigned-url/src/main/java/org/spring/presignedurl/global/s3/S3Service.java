package org.spring.presignedurl.global.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public String generatePreSignedUrl(String fileName) {
        // AWS Presigned URL 발급을 위한 요청 객체 생성
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, fileName)
                        .withMethod(HttpMethod.PUT) // 업로드이므로 PUT
                        .withExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000)); // URL 유효기간 설정 (10분)
        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL, // ACL 설정
                CannedAccessControlList.PublicRead.toString()); // 공개 읽기 권한 적용

        // Presigned URL 발급
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }
}