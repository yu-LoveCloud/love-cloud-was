package com.lovecloud.fundingmanagement.presentation.request;

import com.lovecloud.fundingmanagement.application.command.CompleteParticipationCommand;
import jakarta.validation.constraints.NotNull;

public record CompleteParticipationRequest(
        @NotNull Long paymentId
) {

    public CompleteParticipationCommand toCommand(Long participationId, Long memberId) {
        return CompleteParticipationCommand.builder()
                .participationId(participationId)
                .memberId(memberId)
                .paymentId(paymentId)
                .build();
    }
}
