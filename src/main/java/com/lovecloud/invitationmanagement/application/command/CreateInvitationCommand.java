package com.lovecloud.invitationmanagement.application.command;

import com.lovecloud.invitationmanagement.domain.Invitation;
import com.lovecloud.invitationmanagement.domain.InvitationImage;

import java.time.LocalDateTime;

public record CreateInvitationCommand(
        Long userId,
        Long invitationImageId,
        String weddingDateTime,
        String place,
        String content
) {
    public Invitation toInvitation(InvitationImage invitationImage) {

        return Invitation.builder()
                .weddingDateTime(LocalDateTime.parse(weddingDateTime))
                .place(place)
                .content(content)
                .invitationImage(invitationImage)
                .build();
    }
}
