package com.lovecloud.invitationmanagement.query.response;

import com.lovecloud.invitationmanagement.domain.Invitation;
import com.lovecloud.usermanagement.domain.Couple;

public record InvitationDetailResponse(
        Long invitationId,
        Long coupleId,
        String groomName,
        String brideName,
        String weddingDateTime,
        String weddingPlace,
        String content,
        String invitationImageName
) {
    public static InvitationDetailResponse from(Invitation invitation, Couple couple) {
        return new InvitationDetailResponse(
                invitation.getId(),
                couple.getId(),
                couple.getGroom().getName(),
                couple.getBride().getName(),
                invitation.getWeddingDateTime().toString(),
                invitation.getPlace(),
                invitation.getContent(),
                invitation.getInvitationImage().getImageName()
        );

    }
}
