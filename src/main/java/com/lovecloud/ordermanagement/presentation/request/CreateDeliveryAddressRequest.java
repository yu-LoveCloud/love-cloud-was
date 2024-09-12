package com.lovecloud.ordermanagement.presentation.request;

import com.lovecloud.ordermanagement.application.command.CreateDeliveryAddressCommand;
import jakarta.validation.constraints.NotBlank;

public record CreateDeliveryAddressRequest(
        @NotBlank
        String deliveryName,
        @NotBlank
        String zipCode,
        @NotBlank
        String address,
        @NotBlank
        String detailAddress,
        String deliveryMemo
) {
        public CreateDeliveryAddressCommand toCommand(Long userId) {
                return CreateDeliveryAddressCommand.builder()
                        .userId(userId)
                        .deliveryName(deliveryName())
                        .zipCode(zipCode())
                        .address(address())
                        .detailAddress(detailAddress())
                        .deliveryMemo(deliveryMemo())
                        .build();
        }

}
