package com.lovecloud.productmanagement.presentation.request;

import com.lovecloud.productmanagement.application.command.CreateProductCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateProductRequest(
        @NotBlank String productName,
        @NotNull Long categoryId,
        @NotNull List<String> mainImageNames,
        @NotNull List<String> descriptionImageNames
) {

    public CreateProductCommand toCommand() {
        return CreateProductCommand.builder()
                .productName(productName)
                .categoryId(categoryId)
                .mainImageNames(mainImageNames)
                .descriptionImageNames(descriptionImageNames)
                .build();
    }
}
