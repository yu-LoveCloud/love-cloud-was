package com.lovecloud.fundingmanagement.presentation;

import com.lovecloud.fundingmanagement.application.FundingParticipationService;
import com.lovecloud.fundingmanagement.presentation.request.ParticipateFundingRequest;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FundingParticipationController {

    private final FundingParticipationService fundingParticipationService;

    @PostMapping("/fundings/{fundingId}/participations")
    public ResponseEntity<Void> participateInFunding(
            @PathVariable Long fundingId,
            @Valid @RequestBody ParticipateFundingRequest request
    ) {
        Long memberId = 3L; // TODO: memberId는 @Auth로 받는다고 가정
        Long guestFundingId = fundingParticipationService.participateInFunding(
                request.toCommand(fundingId, memberId));
        return ResponseEntity.created(
                URI.create("/participations/" + guestFundingId)).build();
    }

    @PatchMapping("/participations/{participationId}/complete")
    public ResponseEntity<Void> completeParticipation(
            @PathVariable Long participationId
    ) {
        Long memberId = 3L; // TODO: memberId는 @Auth로 받는다고 가정
        fundingParticipationService.completeParticipation(participationId, memberId);
        return ResponseEntity.ok().build();
    }
}
