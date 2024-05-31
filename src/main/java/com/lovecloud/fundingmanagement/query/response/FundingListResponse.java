package com.lovecloud.fundingmanagement.query.response;

import com.lovecloud.fundingmanagement.domain.FundingStatus;
import java.time.LocalDateTime;
import java.util.List;

public record FundingListResponse(
        Long fundingId,
        String title,
        int targetAmount,
        int currentAmount,
        FundingStatus status,
        LocalDateTime endDate,
        ProductOptionsSummary productOptions,
        CoupleSummary couple
) {

    public static record ProductOptionsSummary(
            Long productOptionsId,
            List<ImageData> mainImages
    ) {

        public static record ImageData(
                Long imageId,
                String imageName
        ) {

        }
    }

    public static record CoupleSummary(
            Long coupleId
    ) {

    }
}
