package com.lovecloud.productmanagement.presentation.request;

import com.lovecloud.productmanagement.application.command.CreateProductCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateProductRequest(
        @NotBlank @Size(max = 100) String productName,
        @NotNull Long categoryId
) {

    public CreateProductCommand toCommand() {
        return CreateProductCommand.builder()
                .productName(productName)
                .categoryId(categoryId)
                .build();
    }
}
