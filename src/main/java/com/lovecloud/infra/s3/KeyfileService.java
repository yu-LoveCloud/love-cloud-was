package com.lovecloud.infra.s3;

import com.lovecloud.infra.s3.exception.KeyfileDownloadException;
import com.lovecloud.infra.s3.exception.KeyfileReadException;
import com.lovecloud.infra.s3.exception.KeyfileUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * AWS S3에 keyfile을 업로드하고, 다운로드하는 서비스 클래스입니다.
 */
@Service
@RequiredArgsConstructor
public class KeyfileService {

    private final S3Client s3Client;
    private final AwsS3Property s3Property;

    /**
     * 주어진 keyfile을 읽어 S3 버킷에 업로드합니다.
     *
     * @param keyfileName keyfile의 이름
     * @param keyfilePath keyfile이 저장된 경로
     */
    public void uploadKeyfile(String keyfileName, String keyfilePath) {
        Path path = Path.of(keyfilePath, keyfileName);

        byte[] keyfileContent = readKeyfileContent(path);

        uploadToS3(keyfileName, keyfileContent);
    }

    /**
     * 주어진 keyfile을 S3 버킷에서 다운로드하고 JSON 형식의 String으로 반환합니다.
     *
     * @param keyfileName keyfile의 이름
     * @return keyfile의 내용 (JSON 형식의 String)
     */
    public String downloadKeyfile(String keyfileName) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(s3Property.bucket())
                    .key(s3Property.keyfilePath() + keyfileName)
                    .build();

            ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
            return new String(objectBytes.asByteArray(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new KeyfileDownloadException("S3에서 keyfile 다운로드 실패: " + keyfileName, e);
        }
    }

    /**
     * 주어진 경로에서 keyfile의 내용을 읽습니다.
     *
     * @param path keyfile의 경로
     * @return keyfile의 내용 (byte 배열)
     * @throws KeyfileReadException keyfile 읽기에 실패했을 때 발생
     */
    private byte[] readKeyfileContent(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new KeyfileReadException("Keyfile 읽기 실패: " + path, e);
        }
    }

    /**
     * S3에 keyfile을 업로드합니다.
     *
     * @param keyfileName keyfile의 이름
     * @param keyfileContent keyfile의 내용 (byte 배열)
     */
    private void uploadToS3(String keyfileName, byte[] keyfileContent) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(s3Property.bucket())
                    .key(s3Property.keyfilePath() + keyfileName)
                    .acl(ObjectCannedACL.PRIVATE)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(keyfileContent));
        } catch (Exception e) {
            throw new KeyfileUploadException("S3에 keyfile 업로드 실패: " + keyfileName, e);
        }
    }
}
