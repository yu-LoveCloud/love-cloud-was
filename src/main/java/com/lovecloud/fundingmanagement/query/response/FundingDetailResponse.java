package com.lovecloud.fundingmanagement.query.response;

import com.lovecloud.fundingmanagement.domain.FundingStatus;
import com.lovecloud.usermanagement.domain.WeddingRole;
import java.time.LocalDateTime;
import java.util.List;

public record FundingDetailResponse(
        Long fundingId,
        String title,
        String message,
        long targetAmount,
        long currentAmount,
        FundingStatus status,
        LocalDateTime endDate,
        int participantCount,
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
            Long coupleId,
            List<Person> people
    ) {

        public static record Person(
                String phoneNumber,
                WeddingRole weddingRole,
                String name
        ) {

        }
    }
}
