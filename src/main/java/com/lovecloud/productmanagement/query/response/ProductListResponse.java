package com.lovecloud.productmanagement.query.response;

import java.util.List;

public record ProductListResponse(
        Long productId,
        String productName,
        List<ProductOptionSummary> options
) {

    public static record CategoryData(
            Long categoryId,
            String categoryName
    ) {

    }

    public static record ProductOptionSummary(
            Long productOptionsId,
            String color,
            String modelName,
            long price,
            int stockQuantity,
            List<ImageData> mainImages
    ) {
        public static record ImageData(
                Long imageId,
                String imageName
        ) {

        }
    }
}
