package com.lovecloud.infra.s3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.web.SecurityFilterChain;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EnableConfigurationProperties(AwsS3Property.class)
class KeyfileServiceIntegrationTest {

    @MockBean
    private SecurityFilterChain securityFilterChain;

    @Autowired
    private KeyfileService keyfileService;

    @Autowired
    private AwsS3Property s3Property;

    @Autowired
    private S3Client s3Client;

    private final String keyfileName = "test-keyfile-name";
    private final String keyfileContent = "test-keyfile-content";
    private final String keyfilePath = "./";

    @Test
    void testUploadKeyfileToS3() throws IOException {
        // 로컬 테스트 파일 생성 (임시 파일 생성)
        Path filePath = Path.of(keyfilePath, keyfileName);
        Files.writeString(filePath, keyfileContent);

        // 실제 S3 버킷에 업로드
        keyfileService.uploadKeyfile(keyfileName, keyfilePath);

        // 로컬 테스트 파일 삭제
        Files.deleteIfExists(filePath);

        // S3 버킷에 해당 파일이 존재하는지 확인
        boolean isUploaded = checkIfFileExistsInS3(s3Property.keyfilePath() + keyfileName);
        assertTrue(isUploaded, "S3 버킷에 파일이 정상적으로 업로드되지 않았습니다.");

        // S3 버킷에 있는 파일의 내용이 예상한 내용과 일치하는지 확인
        boolean isContentMatching = checkIfFileContentMatchesInS3(s3Property.keyfilePath() + keyfileName, keyfileContent);
        assertTrue(isContentMatching, "S3에 저장된 파일 내용이 일치하지 않습니다.");
    }

    private boolean checkIfFileExistsInS3(String keyfileName) {
        try {
            s3Client.headObject(HeadObjectRequest.builder()
                    .bucket(s3Property.bucket())
                    .key(keyfileName)
                    .build());
            return true;
        } catch (NoSuchKeyException e) {
            return false;
        }
    }

    private boolean checkIfFileContentMatchesInS3(String keyfileName, String expectedContent) {
        try {
            // S3에서 파일을 다운로드
            ResponseBytes<GetObjectResponse> s3Object = s3Client.getObjectAsBytes(GetObjectRequest.builder()
                    .bucket(s3Property.bucket())
                    .key(keyfileName)
                    .build());

            // S3에서 받은 파일의 내용을 문자열로 변환
            String actualContent = s3Object.asUtf8String();

            // 파일 내용이 예상한 값과 일치하는지 확인
            return actualContent.equals(expectedContent);
        } catch (NoSuchKeyException e) {
            return false;
        }
    }
}