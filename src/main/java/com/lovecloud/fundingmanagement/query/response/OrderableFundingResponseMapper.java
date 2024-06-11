package com.lovecloud.fundingmanagement.query.response;

import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.productmanagement.domain.ProductOptions;

public class OrderableFundingResponseMapper {

    public static OrderableFundingResponse map(Funding funding) {
        ProductOptions productOptions = funding.getProductOptions();
        return new OrderableFundingResponse(
                funding.getId(),
                funding.getTargetAmount(),
                productOptions.getId(),
                productOptions.getProduct().getProductName(),
                productOptions.getModelName(),
                productOptions.getMainImages().stream()
                        .map(image -> new OrderableFundingResponse.ImageData(
                                image.getId(),
                                image.getMainImageName()))
                        .toList()
        );
    }
}
