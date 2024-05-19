package com.lovecloud.infra.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cloud.aws.s3")
public record AwsS3Property(
        String bucket,
        String imagePath,
        int presignedUrlExpiresMinutes
) {
}
