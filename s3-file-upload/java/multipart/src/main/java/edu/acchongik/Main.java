package edu.acchongik;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String bucketName = "acc-hongik-bucket";
        String filePath = "/Users/jeongjin/Desktop/Git/study-development/s3-file-upload/acc-hongik.txt";
        String accessKey = "";
        String secretKey = "";


        // 파일 이름 추출 (경로에서 파일명만 가져오기)
        String fileName = new File(filePath).getName();

        try {
            // 파일 읽기
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);

            // S3 클라이언트 생성
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.AP_NORTHEAST_2) // 서울 리전
                    .withCredentials(new AWSStaticCredentialsProvider(
                            new BasicAWSCredentials(accessKey, secretKey)))
                    .build();

            // 메타데이터 설정
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.length());

            // 파일 타입 설정 (선택사항)
            String contentType = "text/plain";  // txt 파일의 경우
            objectMetadata.setContentType(contentType);

            // S3에 업로드
            PutObjectRequest request = new PutObjectRequest(
                    bucketName,
                    fileName,
                    fileInputStream,
                    objectMetadata
            );

            s3Client.putObject(request);
            System.out.println("File uploaded successfully: " + fileName);

            // 리소스 정리
            fileInputStream.close();
        } catch (AmazonServiceException e) {
            System.err.println("Amazon S3 couldn't process the file");
            e.printStackTrace();
        } catch (SdkClientException e) {
            System.err.println("Couldn't connect to Amazon S3");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading the file");
            e.printStackTrace();
        }
    }
}