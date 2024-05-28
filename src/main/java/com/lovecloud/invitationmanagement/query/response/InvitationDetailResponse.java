package com.lovecloud.invitationmanagement.query.response;

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
}
