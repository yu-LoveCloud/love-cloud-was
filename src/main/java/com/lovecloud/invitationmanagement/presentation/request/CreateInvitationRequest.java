package com.lovecloud.invitationmanagement.presentation.request;

import com.lovecloud.invitationmanagement.application.command.CreateInvitationCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateInvitationRequest(
    @NotBlank Long invitationImageId,
    @NotBlank String weddingDateTime,
    @NotBlank String place,
    String content

) {


    public CreateInvitationCommand toCommand() {
        return new CreateInvitationCommand(invitationImageId, weddingDateTime, place, content);
    }
}

