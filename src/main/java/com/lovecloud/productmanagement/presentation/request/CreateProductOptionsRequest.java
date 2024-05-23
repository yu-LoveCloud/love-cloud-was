package com.lovecloud.productmanagement.presentation.request;

import com.lovecloud.productmanagement.application.command.CreateProductOptionsCommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateProductOptionsRequest(
        @NotBlank String color,
        @NotBlank String modelName,
        @NotNull @Min(1) Integer price,
        @NotNull @Min(0) Integer stockQuantity,
        @NotNull List<String> mainImageNames,
        @NotNull List<String> descriptionImageNames
) {

    public CreateProductOptionsCommand toCommand(Long productId) {
        return CreateProductOptionsCommand.builder()
                .productId(productId)
                .color(color)
                .modelName(modelName)
                .price(price)
                .stockQuantity(stockQuantity)
                .mainImageNames(mainImageNames)
                .descriptionImageNames(descriptionImageNames)
                .build();
    }
}
