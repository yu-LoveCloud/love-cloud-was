package com.lovecloud.infra.s3;

import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

/**
 * AWS S3에 파일을 업로드하기 위한 Presigned URL을 생성하는 서비스 클래스이다.
 */
@Service
@RequiredArgsConstructor
public class PresignedUrlService {

    private final S3Presigner.Builder presignerBuilder;
    private final AwsS3Property s3Property;

    /**
     * 이미지 확장자를 기반으로 한 사전 서명된 URL을 생성하고 반환한다.
     *
     * @param imageExtension 이미지 파일의 확장자
     * @return 생성된 이미지 이름과 사전 서명된 URL를 포함하는 {@link CreatePresignedUrlResponse} 객체
     */
    public CreatePresignedUrlResponse create(String imageExtension) {
        String imageName = createImageName(imageExtension);
        String presignedUrl = createPresignedUrl(imageName);
        return new CreatePresignedUrlResponse(imageName, presignedUrl);
    }

    private String createImageName(String imageExtension) {
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + imageExtension;
    }

    private String createPresignedUrl(String imageName) {
        try (S3Presigner presigner = presignerBuilder.build()) {
            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(s3Property.presignedUrlExpiresMinutes()))
                    .putObjectRequest(builder -> builder
                            .bucket(s3Property.bucket())
                            .key(s3Property.imagePath() + imageName)
                            .build()
                    ).build();

            PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
            return presignedRequest.url().toExternalForm();
        }
    }
}
