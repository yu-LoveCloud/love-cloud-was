package com.lovecloud.fundingmanagement.presentation;

import com.lovecloud.fundingmanagement.application.FundingParticipationService;
import com.lovecloud.fundingmanagement.presentation.request.CompleteParticipationRequest;
import com.lovecloud.fundingmanagement.presentation.request.ParticipateFundingRequest;
import com.lovecloud.fundingmanagement.query.response.ParticipateFundingResponse;
import com.lovecloud.global.usermanager.SecurityUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.RequestContextFilter;

@RestController
@RequiredArgsConstructor
public class FundingParticipationController {

    private final FundingParticipationService fundingParticipationService;

    @PreAuthorize("hasRole('ROLE_GUEST')")
    @PostMapping("/fundings/{fundingId}/participations")
    public ResponseEntity<ParticipateFundingResponse> participateInFunding(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long fundingId,
            @Valid @RequestBody ParticipateFundingRequest request
    ) {
        final Long userId = securityUser.user().getId();
        ParticipateFundingResponse response = fundingParticipationService.participateInFunding(
                request.toCommand(fundingId, userId));
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_GUEST')")
    @PatchMapping("/participations/{guestFundingId}/complete")
    public ResponseEntity<Void> completeParticipation(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long guestFundingId,
            @Valid @RequestBody CompleteParticipationRequest request
    ) {
        final Long userId = securityUser.user().getId();
        fundingParticipationService.completeParticipation(
                request.toCommand(guestFundingId, userId));
        return ResponseEntity.ok().build();
    }
}
