package com.lovecloud.payment.query.response;

import lombok.Builder;

@Builder
public record PaymentResponse(
        String impUid,
        Long amount,
        String name,
        String status
) {
}
