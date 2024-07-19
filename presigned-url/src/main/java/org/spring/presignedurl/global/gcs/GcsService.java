package org.spring.presignedurl.global.gcs;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.HttpMethod;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class GcsService {

    private final Storage storage;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    public String generateSignedURL(String objectName) {
        BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, objectName)).build();

        Map<String, String> extensionHeaders = new HashMap<>();
        String contentType = getContentType(objectName);

        extensionHeaders.put("Content-Type", contentType);


        URL url = storage.signUrl(blobInfo,
                15,
                TimeUnit.MINUTES,
                Storage.SignUrlOption.httpMethod(HttpMethod.PUT),
                Storage.SignUrlOption.withExtHeaders(extensionHeaders),
                Storage.SignUrlOption.withV4Signature());

        return url.toString();
    }

    private static String getContentType(String objectName) {
        String contentType;
        // 파일 이름에서 확장자 추출
        int lastDotIndex = objectName.lastIndexOf('.');
        if (lastDotIndex != -1) {
            String extension = objectName.substring(lastDotIndex + 1).toLowerCase();
            contentType = switch (extension) {
                case "pdf" -> "application/pdf";
                case "jpg", "jpeg" -> "image/jpeg";
                case "png" -> "image/png";
                default -> "application/octet-stream"; // 기본값
            };
        } else {
            contentType = "application/octet-stream"; // 확장자가 없는 경우 기본값
        }
        return contentType;
    }
}
