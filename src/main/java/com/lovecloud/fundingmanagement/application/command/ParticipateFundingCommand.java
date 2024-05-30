package com.lovecloud.fundingmanagement.application.command;

import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.fundingmanagement.domain.GuestFunding;
import com.lovecloud.usermanagement.domain.Guest;
import lombok.Builder;

@Builder
public record ParticipateFundingCommand(
        Long memberId,
        Long fundingId,
        String name,
        String phoneNumber,
        String email,
        Long fundingAmount,
        String message
) {

    public GuestFunding toGuestFunding(Guest guest, Funding funding, String merchantUid) {
        return GuestFunding.builder()
                .guest(guest)
                .funding(funding)
                .merchantUid(merchantUid)
                .name(name)
                .phoneNumber(phoneNumber)
                .email(email)
                .fundingAmount(fundingAmount)
                .message(message)
                .build();
    }
}
