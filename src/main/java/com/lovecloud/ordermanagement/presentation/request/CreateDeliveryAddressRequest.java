package com.lovecloud.ordermanagement.presentation.request;

import com.lovecloud.ordermanagement.application.command.CreateDeliveryAddressCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateDeliveryAddressRequest(
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
        public CreateDeliveryAddressCommand toCommand(Long userId) {
                return CreateDeliveryAddressCommand.builder()
                        .userId(userId)
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
