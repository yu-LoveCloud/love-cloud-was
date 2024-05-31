package com.lovecloud.payment.domain;

import com.lovecloud.payment.exception.InvalidPaymentStatusException;

public enum PaymentStatus {
    READY, PAID, CANCELED, FAILED;

    public static PaymentStatus fromString(String status) {
        return switch (status.toLowerCase()) {
            case "ready" -> READY;
            case "failed" -> FAILED;
            case "paid" -> PAID;
            case "canceled" -> CANCELED;
            default -> throw new InvalidPaymentStatusException(status);
        };
    }
}
