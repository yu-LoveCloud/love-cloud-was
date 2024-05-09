package com.lovecloud.invitationmanagement.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateInvitationRequest(
    @NotNull Long imageId,
    @NotBlank String weddingDateTime,
    @NotBlank String place,
    String content

) {


    }

