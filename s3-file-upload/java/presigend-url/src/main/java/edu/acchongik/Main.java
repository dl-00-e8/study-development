package edu.acchongik;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import java.net.URL;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        String bucketName = "acc-hongik-bucket";
        String fileName = "acc-hongik.txt";
        String accessKey = "";
        String secretKey = "";

        // S3 클라이언트 생성
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_2) // 서울 리전
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();

        // AWS Presigned URL 발급을 위한 요청 객체 생성
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, fileName)
                        .withMethod(HttpMethod.PUT) // 업로드이므로 PUT
                        .withExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000)); // URL 유효기간 설정 (10분)
        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL, // ACL 설정
                CannedAccessControlList.Private.toString()); // Private 권한 적용

        // Presigned URL 발급
        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

        System.out.println(url.toString());
    }
}