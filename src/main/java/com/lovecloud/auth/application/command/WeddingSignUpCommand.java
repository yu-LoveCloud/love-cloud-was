package com.lovecloud.auth.application.command;

import com.lovecloud.auth.domain.Password;

import com.lovecloud.global.util.UUIDUtil;
import com.lovecloud.usermanagement.domain.*;

public record WeddingSignUpCommand(
    String email,
    String password,
    String name,
    String phoneNumber,
    WeddingRole weddingRole
) {

    public WeddingUser toWeddingUser(Password password) {

        final var userRole = UserRole.WEDDING_USER;
        String invitationCode = UUIDUtil.generateShortUUID();

        return WeddingUser.builder()
                .email(email)
                .name(name)
                .userRole(userRole)
                .phoneNumber(phoneNumber)
                .password(password)
                .accountStatus(AccountStatus.ACTIVE)
                .weddingRole(weddingRole)
                .oauthId(null)
                .oauth(null)
                .invitationCode(invitationCode)
                .build();

    }

}
