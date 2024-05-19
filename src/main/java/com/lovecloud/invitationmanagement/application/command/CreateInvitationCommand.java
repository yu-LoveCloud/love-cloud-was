package com.lovecloud.invitationmanagement.application.command;

import com.lovecloud.invitationmanagement.domain.Invitation;

import java.time.LocalDateTime;

public record CreateInvitationCommand(
        Long userId,
        String imageName,
        String weddingDateTime,
        String place,
        String content
) {
    public Invitation toInvitation() {

        return Invitation.builder()
                .weddingDateTime(LocalDateTime.parse(weddingDateTime))
                .place(place)
                .content(content)
                .build();
    }
}
