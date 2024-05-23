package com.lovecloud.productmanagement.application.command;

import com.lovecloud.productmanagement.domain.Category;
import com.lovecloud.productmanagement.domain.Product;
import lombok.Builder;

@Builder
public record CreateProductCommand(
        String productName,
        Long categoryId
) {

    public Product toProduct(Category category) {
        return Product.builder()
                .productName(productName)
                .category(category)
                .build();
    }
}
