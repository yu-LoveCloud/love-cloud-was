package com.lovecloud.fundingmanagement.presentation.request;

import com.lovecloud.fundingmanagement.application.command.CreateFundingCommand;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record CreateFundingRequest(
        @NotNull Long productOptionsId,
        @NotBlank @Size(max = 100) String title,
        @NotBlank @Size(max = 300) String message,
        @NotNull @Future LocalDateTime endDate
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
