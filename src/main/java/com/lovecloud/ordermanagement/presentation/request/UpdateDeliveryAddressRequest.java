package com.lovecloud.ordermanagement.presentation.request;

import com.lovecloud.ordermanagement.application.command.UpdateDeliveryAddressCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateDeliveryAddressRequest(
        @NotBlank
        String deliveryName,

        @NotBlank
        String zipCode,

        @NotBlank
        String address,

        @NotBlank
        String detailAddress,

        String deliveryMemo,

        @NotBlank
        String receiverName,

        @NotBlank
        String receiverPhoneNumber,

        @NotNull
        Boolean isDefault
) {
    public UpdateDeliveryAddressCommand toCommand(Long userId, Long deliveryAddressId) {
        return UpdateDeliveryAddressCommand.builder()
                .userId(userId)
                .deliveryAddressId(deliveryAddressId)
                .deliveryName(deliveryName())
                .zipCode(zipCode())
                .address(address())
                .detailAddress(detailAddress())
                .deliveryMemo(deliveryMemo())
                .receiverName(receiverName())
                .receiverPhoneNumber(receiverPhoneNumber())
                .isDefault(isDefault())
                .build();
    }
}
