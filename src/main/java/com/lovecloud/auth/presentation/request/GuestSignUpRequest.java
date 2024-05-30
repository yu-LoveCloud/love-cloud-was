package com.lovecloud.auth.presentation.request;

import com.lovecloud.auth.application.command.GuestSignUpCommand;
import jakarta.validation.constraints.NotBlank;

public record GuestSignUpRequest(
        @NotBlank String email,
        @NotBlank String name,
        @NotBlank String password,
        @NotBlank String phoneNumber
) {
    public GuestSignUpCommand toCommand() {
        return new GuestSignUpCommand(
                email,
                password,
                name,
                phoneNumber
        );
    }
}
