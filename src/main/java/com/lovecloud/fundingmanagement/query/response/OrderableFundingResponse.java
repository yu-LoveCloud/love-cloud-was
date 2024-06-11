package com.lovecloud.fundingmanagement.query.response;

import java.util.List;

public record OrderableFundingResponse(
        Long fundingId,
        long price,
        Long productOptionId,
        String productName,
        String modelName,
        List<ImageData> mainImages
) {

    public static record ImageData(
            Long imageId,
            String imageName
    ) {

    }
}
