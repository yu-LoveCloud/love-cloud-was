package com.lovecloud.ordermanagement.application.command;

import lombok.Builder;

@Builder
public record UpdateDeliveryAddressCommand(
        Long userId,
        Long deliveryAddressId,
        String deliveryName,
        String zipCode,
        String address,
        String detailAddress,
        String deliveryMemo,
        String receiverName,
        String receiverPhoneNumber,
        boolean isDefault
) {
}
