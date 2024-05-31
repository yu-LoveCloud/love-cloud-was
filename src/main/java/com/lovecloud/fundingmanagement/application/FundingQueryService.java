package com.lovecloud.fundingmanagement.application;

import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.fundingmanagement.domain.repository.FundingRepository;
import com.lovecloud.fundingmanagement.query.response.FundingDetailResponse;
import com.lovecloud.fundingmanagement.query.response.FundingDetailResponseMapper;
import com.lovecloud.fundingmanagement.query.response.FundingListResponse;
import com.lovecloud.fundingmanagement.query.response.FundingListResponseMapper;
import com.lovecloud.productmanagement.domain.repository.MainImageRepository;
import com.lovecloud.usermanagement.domain.repository.CoupleRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FundingQueryService {

    private final MainImageRepository mainImageRepository;
    private final FundingRepository fundingRepository;
    private final CoupleRepository coupleRepository;

    public List<FundingListResponse> findAllByCoupleId(Long coupleId) {
        coupleRepository.findByIdOrThrow(coupleId);
        List<Funding> fundings = fundingRepository.findByCoupleId(coupleId);
        if (fundings.isEmpty()) {
            return List.of();
        }
        return fundings.stream()
                .map(funding -> FundingListResponseMapper.mapFundingToFundingListResponse(
                        funding,
                        mainImageRepository.findByProductOptionsId(
                                funding.getProductOptions().getId())
                ))
                .collect(Collectors.toList());
    }

    public FundingDetailResponse findById(Long fundingId) {
        Funding funding = fundingRepository.findByIdOrThrow(fundingId);
        return FundingDetailResponseMapper.mapFundingToFundingDetailResponse(
                funding,
                mainImageRepository.findByProductOptionsId(funding.getProductOptions().getId())
        );
    }
}
