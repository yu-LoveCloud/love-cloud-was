package com.lovecloud.fundingmanagement.query.response;

import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.productmanagement.domain.MainImage;
import java.util.List;
import java.util.stream.Collectors;

public class FundingListResponseMapper {

    public static FundingListResponse mapFundingToFundingListResponse(Funding funding,
            List<MainImage> mainImages) {
        FundingListResponse.ProductOptionsSummary productOptionsSummary = new FundingListResponse.ProductOptionsSummary(
                funding.getProductOptions().getId(),
                mainImages.stream()
                        .map(image -> new FundingListResponse.ProductOptionsSummary.ImageData(
                                image.getId(), image.getMainImageName()))
                        .collect(Collectors.toList())
        );

        FundingListResponse.CoupleSummary coupleSummary = new FundingListResponse.CoupleSummary(
                funding.getCouple().getId()
        );

        return new FundingListResponse(
                funding.getId(),
                funding.getTitle(),
                funding.getTargetAmount(),
                funding.getCurrentAmount(),
                funding.getStatus(),
                funding.getEndDate(),
                productOptionsSummary,
                coupleSummary
        );
    }
}
