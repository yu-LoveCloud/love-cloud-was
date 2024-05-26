package com.lovecloud.auth.application.command;

import com.lovecloud.auth.domain.Password;
import com.lovecloud.usermanagement.domain.Guest;
import com.lovecloud.usermanagement.domain.UserRole;


public record GuestSignUpCommand(
        String email,
        String password,
        String name,
        String phoneNumber
) {

    public Guest toGuestUser(Password password) {

        final var userRole = UserRole.GUEST;

        return Guest.builder()
                .email(email)
                .name(name)
                .userRole(userRole)
                .phoneNumber(phoneNumber)
                .password(password)
                .build();

    }
}
