package com.lovecloud.productmanagement.application.command;

import com.lovecloud.productmanagement.domain.DescriptionImage;
import com.lovecloud.productmanagement.domain.MainImage;
import com.lovecloud.productmanagement.domain.Product;
import com.lovecloud.productmanagement.domain.ProductOptions;
import java.util.List;
import lombok.Builder;

@Builder
public record CreateProductOptionsCommand(
        Long productId,
        String color,
        String modelName,
        Integer price,
        Integer stockQuantity,
        List<String> mainImageNames,
        List<String> descriptionImageNames
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

    public List<MainImage> toMainImages(ProductOptions productOptions) {
        return mainImageNames.stream()
                .map(mainImageName -> MainImage.builder()
                        .productOptions(productOptions)
                        .mainImageName(mainImageName)
                        .build())
                .toList();
    }

    public List<DescriptionImage> toDescriptionImages(ProductOptions productOptions) {
        return descriptionImageNames.stream()
                .map(descriptionImageName -> DescriptionImage.builder()
                        .productOptions(productOptions)
                        .descriptionImageName(descriptionImageName)
                        .build())
                .toList();
    }
}
