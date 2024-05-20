package com.lovecloud.productmanagement.application.command;

import com.lovecloud.productmanagement.domain.Category;
import com.lovecloud.productmanagement.domain.DescriptionImage;
import com.lovecloud.productmanagement.domain.MainImage;
import com.lovecloud.productmanagement.domain.Product;
import java.util.List;
import lombok.Builder;

@Builder
public record CreateProductCommand(
        String productName,
        Long categoryId,
        List<String> mainImageNames,
        List<String> descriptionImageNames
) {

    public Product toProduct(Category category) {
        return Product.builder()
                .productName(productName)
                .category(category)
                .build();
    }

    public List<MainImage> toMainImages(Product product) {
        return mainImageNames.stream()
                .map(mainImageName -> MainImage.builder()
                        .product(product)
                        .mainImageName(mainImageName)
                        .build())
                .toList();
    }

    public List<DescriptionImage> toDescriptionImages(Product product) {
        return descriptionImageNames.stream()
                .map(descriptionImageName -> DescriptionImage.builder()
                        .product(product)
                        .descriptionImageName(descriptionImageName)
                        .build())
                .toList();
    }
}
