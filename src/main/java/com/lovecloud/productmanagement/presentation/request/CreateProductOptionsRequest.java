package com.lovecloud.productmanagement.presentation.request;

import com.lovecloud.productmanagement.application.command.CreateProductOptionsCommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProductOptionsRequest(
        @NotBlank String color,
        @NotBlank String modelName,
        @NotNull @Min(1) Integer price,
        @NotNull @Min(0) Integer stockQuantity
) {

    public CreateProductOptionsCommand toCommand(Long productId) {
        return CreateProductOptionsCommand.builder()
                .productId(productId)
                .color(color)
                .modelName(modelName)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();
    }
}
