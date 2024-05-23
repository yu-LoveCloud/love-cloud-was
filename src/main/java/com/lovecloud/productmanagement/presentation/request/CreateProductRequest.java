package com.lovecloud.productmanagement.presentation.request;

import com.lovecloud.productmanagement.application.command.CreateProductCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProductRequest(
        @NotBlank String productName,
        @NotNull Long categoryId
) {

    public CreateProductCommand toCommand() {
        return CreateProductCommand.builder()
                .productName(productName)
                .categoryId(categoryId)
                .build();
    }
}
