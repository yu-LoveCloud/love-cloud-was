package com.lovecloud.ordermanagement.presentation.request;

import com.lovecloud.ordermanagement.application.command.CreateOrderCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(
        @NotNull
        Long userId,
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
        String receiverZipCode,
        @NotBlank
        String receiverAddress,
        String receiverAddressDetail,
        String deliveryMemo

) {
    public CreateOrderCommand toCommand(Long userId) {
        return new CreateOrderCommand(
                userId,
                fundingIds,
                ordererName,
                ordererPhoneNumber,
                ordererMemo,
                receiverName,
                receiverPhoneNumber,
                receiverZipCode,
                receiverAddress,
                receiverAddressDetail,
                deliveryMemo
        );
    }
}
