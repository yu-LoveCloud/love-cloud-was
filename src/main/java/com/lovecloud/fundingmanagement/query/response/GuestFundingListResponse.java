package com.lovecloud.fundingmanagement.query.response;

import java.time.LocalDateTime;

public record GuestFundingListResponse(
        Long guestFundingId,
        String name,
        long fundingAmount,
        String message,
        LocalDateTime paidAt
) {

}
