package com.lovecloud.auth.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record WeddingLoginRequest(
        @NotBlank String email,
        @NotBlank String password
) {
}
