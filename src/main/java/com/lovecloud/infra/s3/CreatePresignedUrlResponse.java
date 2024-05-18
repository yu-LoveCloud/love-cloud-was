package com.lovecloud.infra.s3;

public record CreatePresignedUrlResponse(
        String imageName,
        String presignedUrl
) {
}
