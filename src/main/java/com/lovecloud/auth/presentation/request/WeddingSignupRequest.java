package com.lovecloud.auth.presentation.request;

import com.lovecloud.auth.application.command.WeddingSignupCommand;
import com.lovecloud.usermanagement.domain.UserRole;
import com.lovecloud.usermanagement.domain.WeddingRole;
import jakarta.validation.constraints.NotBlank;

public record WeddingSignupRequest(
        @NotBlank String email,
        @NotBlank String name,
        @NotBlank String password,
        @NotBlank WeddingRole weddingRole
) {
    public WeddingSignupCommand toCommand() {
        return new WeddingSignupCommand(
                email,
                password,
                name,
                weddingRole
        );
    }
}
