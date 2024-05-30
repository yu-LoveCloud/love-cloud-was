package com.lovecloud.payment.query.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PaymentResponse(
        String impUid,
        String payMethod,
        Long amount,
        String name,
        String status,
        LocalDateTime paidAt
) {
}
