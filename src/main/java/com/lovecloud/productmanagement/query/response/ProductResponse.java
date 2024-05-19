package com.lovecloud.productmanagement.query.response;

import java.util.List;

public record ProductResponse(
        Long productId,
        String productName,
        CategoryData category,
        List<ImageData> mainImages,
        List<ImageData> descriptionImages,
        List<ProductOptionsData> productOptions
) {

    public static record CategoryData(
            Long categoryId,
            String categoryName
    ) {

    }

    public static record ImageData(
            Long imageId,
            String imageName
    ) {

    }

    public static record ProductOptionsData(
            Long productOptionsId,
            String color,
            String modelName,
            Boolean isDeleted,
            int price,
            int stockQuantity
    ) {

    }
}
