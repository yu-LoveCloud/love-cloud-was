package com.lovecloud.ordermanagement.query.response;

import com.lovecloud.ordermanagement.domain.DeliveryAddress;

public record DeliveryAddressResponse(
        Long id,
        String deliveryName,
        String zipCode,
        String address,
        String detailAddress,
        String deliveryMemo,
        String receiverName,
        String receiverPhoneNumber,
        boolean isDefault
) {
    public static DeliveryAddressResponse from(DeliveryAddress deliveryAddress) {
        return new DeliveryAddressResponse(
                deliveryAddress.getId(),
                deliveryAddress.getDeliveryName(),
                deliveryAddress.getZipCode(),
                deliveryAddress.getAddress(),
                deliveryAddress.getDetailAddress(),
                deliveryAddress.getDeliveryMemo(),
                deliveryAddress.getReceiverName(),
                deliveryAddress.getReceiverPhoneNumber(),
                deliveryAddress.isDefault()
        );
    }
}
