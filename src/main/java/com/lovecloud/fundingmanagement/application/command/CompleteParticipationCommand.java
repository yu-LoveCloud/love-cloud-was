package com.lovecloud.fundingmanagement.application.command;

import lombok.Builder;

@Builder
public record CompleteParticipationCommand(
        Long memberId,
        Long guestFundingId,
        Long paymentId
) {

}
