package com.lovecloud.ordermanagement.application.command;

import com.lovecloud.ordermanagement.domain.DeliveryAddress;
import com.lovecloud.usermanagement.domain.Couple;
import lombok.Builder;

@Builder
public record CreateDeliveryAddressCommand(
        Long userId,
        String deliveryName,
        String zipCode,
        String address,
        String detailAddress,
        String deliveryMemo
) {
    public DeliveryAddress toDeliveryAddress(Couple couple) {
        return DeliveryAddress.builder()
                .deliveryName(deliveryName())
                .zipCode(zipCode())
                .address(address())
                .detailAddress(detailAddress())
                .deliveryMemo(deliveryMemo())
                .couple(couple)
                .build();
    }
}
