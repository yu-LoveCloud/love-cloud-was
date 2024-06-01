package com.lovecloud.fundingmanagement.presentation.request;

import com.lovecloud.fundingmanagement.application.command.ParticipateFundingCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ParticipateFundingRequest(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Size(max = 20) String phoneNumber,
        @NotBlank @Email @Size(max = 100) String email,
        @NotNull Long fundingAmount,
        @NotBlank @Size(max = 300) String message
) {

    public ParticipateFundingCommand toCommand(Long fundingId, Long memberId) {
        return ParticipateFundingCommand.builder()
                .fundingId(fundingId)
                .memberId(memberId)
                .name(name)
                .phoneNumber(phoneNumber)
                .email(email)
                .fundingAmount(fundingAmount)
                .message(message)
                .build();
    }
}
