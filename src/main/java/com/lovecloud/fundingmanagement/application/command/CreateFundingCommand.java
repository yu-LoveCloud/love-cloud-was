package com.lovecloud.fundingmanagement.application.command;

import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.productmanagement.domain.ProductOptions;
import com.lovecloud.usermanagement.domain.Couple;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CreateFundingCommand(
        Long memberId,
        Long productOptionsId,
        String title,
        String message,
        LocalDateTime endDate
) {

    public Funding toFunding(ProductOptions productOptions, Couple couple) {
        return Funding.builder()
                .title(title)
                .message(message)
                .targetAmount(productOptions.getPrice())
                .endDate(endDate)
                .productOptions(productOptions)
                .couple(couple)
                .build();
    }
}
