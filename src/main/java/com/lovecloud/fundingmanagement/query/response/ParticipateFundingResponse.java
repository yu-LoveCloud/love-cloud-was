package com.lovecloud.fundingmanagement.query.response;

import com.lovecloud.fundingmanagement.domain.GuestFunding;

public record ParticipateFundingResponse(
        Long guestFundingId,
        String merchantUid,
        String name,
        Long amount
) {

    public static ParticipateFundingResponse from(GuestFunding guestFunding) {
        return new ParticipateFundingResponse(
                guestFunding.getId(),
                guestFunding.getMerchantUid(),
                guestFunding.getFunding().getTitle(),
                guestFunding.getFundingAmount()
        );
    }

}
