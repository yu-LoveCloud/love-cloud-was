package com.lovecloud.fundingmanagement.presentation;

import com.lovecloud.fundingmanagement.application.FundingCreationService;
import com.lovecloud.fundingmanagement.presentation.request.CreateFundingRequest;
import com.lovecloud.global.usermanager.SecurityUser;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FundingCreationController {

    private final FundingCreationService fundingCreationService;

    @PreAuthorize("hasRole('ROLE_WEDDING_USER')")
    @PostMapping("/fundings")
    public ResponseEntity<Long> createFunding(
            @AuthenticationPrincipal SecurityUser securityUser,
            @Valid @RequestBody CreateFundingRequest request
    ) {
        final Long userId = securityUser.user().getId();
        final Long fundingId = fundingCreationService.createFunding(request.toCommand(userId));
        return ResponseEntity.created(URI.create("/fundings/" + fundingId)).build();
    }
}
