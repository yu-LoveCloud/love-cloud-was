package com.lovecloud.infra.s3.presentation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreatePresignedUrlRequest(
        @NotBlank
        @Pattern(regexp = "^(jpeg|jpg|png|gif|bmp)$")
        String imageExtension
) {
}
