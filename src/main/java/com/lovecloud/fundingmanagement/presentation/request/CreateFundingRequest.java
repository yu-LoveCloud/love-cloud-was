package com.lovecloud.fundingmanagement.presentation.request;

import com.lovecloud.fundingmanagement.application.command.CreateFundingCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CreateFundingRequest(
        @NotNull Long productOptionsId,
        @NotBlank String title,
        @NotBlank String message,
        @NotNull LocalDateTime endDate
) {

    public CreateFundingCommand toCommand(Long memberId) {
        return CreateFundingCommand.builder()
                .memberId(memberId)
                .productOptionsId(productOptionsId)
                .title(title)
                .message(message)
                .endDate(endDate)
                .build();
    }
}
