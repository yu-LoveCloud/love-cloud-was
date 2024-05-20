package com.lovecloud.auth.application.command;

import com.lovecloud.auth.domain.Password;
import com.lovecloud.usermanagement.domain.*;

public record WeddingSignupCommand(
    String email,
    String password,
    String name,
    WeddingRole weddingRole
) {

    public WeddingUser toWeddingUser(Password password) {

        final var userRole = UserRole.WEDDING_USER;

        return WeddingUser.builder()
                .email(email)
                .name(name)
                .userRole(userRole)
                .phoneNumber(null)
                .password(password)
                .accountStatus(AccountStatus.ACTIVE)
                .weddingRole(weddingRole)
                .oauthId(null)
                .oauth(null)
                .invitationCode(null)
                .build();

    }

}
