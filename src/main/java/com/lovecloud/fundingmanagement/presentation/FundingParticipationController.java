package com.lovecloud.fundingmanagement.presentation;

import com.lovecloud.fundingmanagement.application.FundingParticipationService;
import com.lovecloud.fundingmanagement.presentation.request.CompleteParticipationRequest;
import com.lovecloud.fundingmanagement.presentation.request.ParticipateFundingRequest;
import com.lovecloud.fundingmanagement.query.response.ParticipateFundingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    private final RequestContextFilter requestContextFilter;

    @PostMapping("/fundings/{fundingId}/participations")
    public ResponseEntity<ParticipateFundingResponse> participateInFunding(
            @PathVariable Long fundingId,
            @Valid @RequestBody ParticipateFundingRequest request
    ) {
        Long memberId = 3L; // TODO: memberId는 @Auth로 받는다고 가정
        ParticipateFundingResponse response = fundingParticipationService.participateInFunding(
                request.toCommand(fundingId, memberId));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/participations/{guestFundingId}/complete")
    public ResponseEntity<Void> completeParticipation(
            @PathVariable Long participationId,
            @Valid @RequestBody CompleteParticipationRequest request
    ) {
        Long memberId = 3L; // TODO: memberId는 @Auth로 받는다고 가정
        fundingParticipationService.completeParticipation(
                request.toCommand(participationId, memberId));
        return ResponseEntity.ok().build();
    }
}
