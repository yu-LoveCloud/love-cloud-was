package com.lovecloud.productmanagement.application.command;

import com.lovecloud.productmanagement.domain.Product;
import com.lovecloud.productmanagement.domain.ProductOptions;
import lombok.Builder;

@Builder
public record CreateProductOptionsCommand(
        Long productId,
        String color,
        String modelName,
        Integer price,
        Integer stockQuantity
) {

    public ProductOptions toProductOptions(Product product) {
        return ProductOptions.builder()
                .product(product)
                .color(color)
                .modelName(modelName)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();
    }
}
