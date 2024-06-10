package com.lovecloud.fundingmanagement.query.response;

import com.lovecloud.fundingmanagement.domain.Funding;
import java.util.stream.Collectors;


public class FundingListResponseMapper {

    public static FundingListResponse map(Funding funding, int participantCount) {
        FundingListResponse.ProductOptionsSummary productOptionsSummary = new FundingListResponse.ProductOptionsSummary(
                funding.getProductOptions().getId(),
                funding.getProductOptions().getMainImages().stream()
                        .map(image -> new FundingListResponse.ProductOptionsSummary.ImageData(
                                image.getId(), image.getMainImageName()))
                        .collect(Collectors.toList())
        );

        return new FundingListResponse(
                funding.getId(),
                funding.getTitle(),
                funding.getTargetAmount(),
                funding.getCurrentAmount(),
                funding.getStatus(),
                funding.getEndDate(),
                participantCount,
                productOptionsSummary
        );
    }
}
