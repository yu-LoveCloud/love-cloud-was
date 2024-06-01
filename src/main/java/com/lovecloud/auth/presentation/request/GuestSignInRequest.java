package com.lovecloud.auth.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record GuestSignInRequest(
        @NotBlank String email,
        @NotBlank String password
) {
}
