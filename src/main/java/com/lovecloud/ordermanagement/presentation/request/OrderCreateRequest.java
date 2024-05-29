package com.lovecloud.ordermanagement.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderCreateRequest(
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
}
