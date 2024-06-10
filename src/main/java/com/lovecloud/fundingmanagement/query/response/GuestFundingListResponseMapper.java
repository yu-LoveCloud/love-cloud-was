package com.lovecloud.fundingmanagement.query.response;

import com.lovecloud.fundingmanagement.domain.GuestFunding;

public class GuestFundingListResponseMapper {

    public static GuestFundingListResponse map(
            GuestFunding guestFunding) {
        return new GuestFundingListResponse(
                guestFunding.getId(),
                guestFunding.getName(),
                guestFunding.getFundingAmount(),
                guestFunding.getMessage(),
                guestFunding.getPayment().getPaidAt()
        );
    }

}
