package com.lovecloud.ordermanagement.application.command;

import lombok.Builder;

import java.util.List;

@Builder
public record CreateOrderCommand(
        Long userId,
        List<Long> fundingIds,
        String ordererName,
        String ordererPhoneNumber,
        String ordererMemo,
        String receiverName,
        String receiverPhoneNumber,
        String deliveryName,
        String zipCode,
        String address,
        String detailAddress,
        String deliveryMemo
) {
}
