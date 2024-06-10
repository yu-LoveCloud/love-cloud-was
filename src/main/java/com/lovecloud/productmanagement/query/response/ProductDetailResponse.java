package com.lovecloud.productmanagement.query.response;

import java.util.List;

public record ProductDetailResponse(
        Long productId,
        String productName,
        ProductOptionDetail selectedOption,
        List<OtherOptionData> otherOptions
) {

    public static record ProductOptionDetail(
            Long productOptionsId,
            String color,
            String modelName,
            long price,
            int stockQuantity,
            List<ImageData> mainImages,
            List<ImageData> descriptionImages
    ) {
        public static record ImageData(
                Long imageId,
                String imageName
        ) {

        }
    }

    public static record OtherOptionData(
            Long productOptionsId,
            String color,
            int stockQuantity
    ) {

    }
}