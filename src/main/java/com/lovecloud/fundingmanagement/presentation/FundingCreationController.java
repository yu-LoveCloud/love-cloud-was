package com.lovecloud.fundingmanagement.presentation;

import com.lovecloud.fundingmanagement.application.FundingCreationService;
import com.lovecloud.fundingmanagement.presentation.request.CreateFundingRequest;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FundingCreationController {

    private final FundingCreationService fundingCreationService;

    @PostMapping("/fundings")
    public ResponseEntity<Long> createFunding(
            @Valid @RequestBody CreateFundingRequest request
    ) {
        Long memberId = 1L; // TODO: memberId는 @Auth로 받는다고 가정
        final Long fundingId = fundingCreationService.createFunding(request.toCommand(memberId));
        return ResponseEntity.created(URI.create("/fundings/" + fundingId)).build();
    }
}
