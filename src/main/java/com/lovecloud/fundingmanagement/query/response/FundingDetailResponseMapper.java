package com.lovecloud.fundingmanagement.query.response;

import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.productmanagement.domain.MainImage;
import com.lovecloud.usermanagement.domain.WeddingUser;
import java.util.List;
import java.util.stream.Collectors;

public class FundingDetailResponseMapper {

    public static FundingDetailResponse map(Funding funding, int participantCount) {
        FundingDetailResponse.ProductOptionsSummary productOptionsSummary = mapToProductOptionsSummary(
                funding.getProductOptions().getId(), funding.getProductOptions().getMainImages());

        FundingDetailResponse.CoupleSummary coupleSummary = mapToCoupleSummary(funding);

        return new FundingDetailResponse(
                funding.getId(),
                funding.getTitle(),
                funding.getMessage(),
                funding.getTargetAmount(),
                funding.getCurrentAmount(),
                funding.getStatus(),
                funding.getEndDate(),
                participantCount,
                productOptionsSummary,
                coupleSummary
        );
    }

    private static FundingDetailResponse.ProductOptionsSummary mapToProductOptionsSummary(
            Long productOptionsId, List<MainImage> mainImages) {
        return new FundingDetailResponse.ProductOptionsSummary(
                productOptionsId,
                mainImages.stream()
                        .map(image -> new FundingDetailResponse.ProductOptionsSummary.ImageData(
                                image.getId(), image.getMainImageName()))
                        .collect(Collectors.toList())
        );
    }

    private static FundingDetailResponse.CoupleSummary mapToCoupleSummary(Funding funding) {
        WeddingUser groom = funding.getCouple().getGroom();
        WeddingUser bride = funding.getCouple().getBride();

        List<FundingDetailResponse.CoupleSummary.Person> people = List.of(
                new FundingDetailResponse.CoupleSummary.Person(
                        groom.getPhoneNumber(),
                        groom.getWeddingRole(),
                        groom.getName()
                ),
                new FundingDetailResponse.CoupleSummary.Person(
                        bride.getPhoneNumber(),
                        bride.getWeddingRole(),
                        bride.getName()
                )
        );

        return new FundingDetailResponse.CoupleSummary(
                funding.getCouple().getId(),
                people
        );
    }
}
