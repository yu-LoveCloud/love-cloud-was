package com.lovecloud.productmanagement.query.response;

import java.util.List;

public record ProductDetailResponse(
        Long productId,
        String productName,
        CategoryData category,
        ProductOptionDetail selectedOption,
        List<OtherOptionData> otherOptions
) {

    public static record CategoryData(
            Long categoryId,
            String categoryName
    ) {

    }

    public static record ProductOptionDetail(
            Long productOptionsId,
            String color,
            String modelName,
            int price,
            int stockQuantity,
            List<ImageData> mainImages,
            List<ImageData> descriptionImages
    ) {

    }

    public static record OtherOptionData(
            Long productOptionsId,
            String color,
            int stockQuantity
    ) {

    }

    public static record ImageData(
            Long imageId,
            String imageName
    ) {

    }
}