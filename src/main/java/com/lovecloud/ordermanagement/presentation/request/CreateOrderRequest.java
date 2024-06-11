package com.lovecloud.ordermanagement.presentation.request;

import com.lovecloud.ordermanagement.application.command.CreateOrderCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(
        @NotNull
        List<Long> fundingIds,

        @NotBlank
        String ordererName,
        @NotBlank
        String ordererPhoneNumber,
        String ordererMemo,

        @NotBlank
        String receiverName,
        @NotBlank
        String receiverPhoneNumber,

        @NotBlank
        String deliveryName,
        @NotBlank
        String zipcode,
        @NotBlank
        String address,

        @NotBlank
        String detailAddress,
        String deliveryMemo

) {
    public CreateOrderCommand toCommand(Long userId) {
        return CreateOrderCommand.builder()
                .userId(userId)
                .fundingIds(fundingIds())
                .ordererName(ordererName())
                .ordererPhoneNumber(ordererPhoneNumber())
                .ordererMemo(ordererMemo())
                .receiverName(receiverName())
                .receiverPhoneNumber(receiverPhoneNumber())
                .deliveryName(deliveryName())
                .zipCode(zipcode())
                .address(address())
                .detailAddress(detailAddress())
                .deliveryMemo(deliveryMemo())
                .build();
    }
}
