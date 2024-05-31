package com.lovecloud.fundingmanagement.presentation;

import com.lovecloud.fundingmanagement.application.FundingQueryService;
import com.lovecloud.fundingmanagement.query.response.FundingDetailResponse;
import com.lovecloud.fundingmanagement.query.response.FundingListResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FundingQueryController {

    private final FundingQueryService fundingQueryService;

    @GetMapping("/couples/{coupleId}/fundings")
    public ResponseEntity<List<FundingListResponse>> listFundings(
            @PathVariable Long coupleId
    ) {
        final List<FundingListResponse> fundings = fundingQueryService.findAllByCoupleId(coupleId);
        return ResponseEntity.ok(fundings);
    }

    @GetMapping("/fundings/{fundingId}")
    public ResponseEntity<FundingDetailResponse> detailFunding(
            @PathVariable Long fundingId
    ) {
        final FundingDetailResponse funding = fundingQueryService.findById(fundingId);
        return ResponseEntity.ok(funding);
    }
}
