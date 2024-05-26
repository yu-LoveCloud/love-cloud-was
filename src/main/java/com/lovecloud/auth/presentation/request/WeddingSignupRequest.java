package com.lovecloud.auth.presentation.request;

import com.lovecloud.auth.application.command.WeddingSignupCommand;
import com.lovecloud.usermanagement.domain.WeddingRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WeddingSignupRequest(
        @NotBlank String email,
        @NotBlank String name,
        @NotBlank String password,
        @NotBlank String phoneNumber,
        @NotNull WeddingRole weddingRole
) {
    public WeddingSignupCommand toCommand() {
        return new WeddingSignupCommand(
                email,
                password,
                name,
                phoneNumber,
                weddingRole
        );
    }
}
