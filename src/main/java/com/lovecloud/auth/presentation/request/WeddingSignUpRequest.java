package com.lovecloud.auth.presentation.request;

import com.lovecloud.auth.application.command.WeddingSignUpCommand;
import com.lovecloud.usermanagement.domain.WeddingRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WeddingSignUpRequest(
        @NotBlank String email,
        @NotBlank String name,
        @NotBlank String password,
        @NotBlank String phoneNumber,
        @NotNull WeddingRole weddingRole
) {
    public WeddingSignUpCommand toCommand() {
        return new WeddingSignUpCommand(
                email,
                password,
                name,
                phoneNumber,
                weddingRole
        );
    }
}
