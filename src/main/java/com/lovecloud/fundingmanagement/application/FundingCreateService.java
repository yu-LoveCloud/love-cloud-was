package com.lovecloud.fundingmanagement.application;

import com.lovecloud.fundingmanagement.application.command.CreateFundingCommand;
import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.fundingmanagement.domain.repository.FundingRepository;
import com.lovecloud.productmanagement.domain.ProductOptions;
import com.lovecloud.productmanagement.domain.repository.ProductOptionsRepository;
import com.lovecloud.usermanagement.domain.Couple;
import com.lovecloud.usermanagement.domain.repository.CoupleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class FundingCreateService {

    private final FundingRepository fundingRepository;
    private final ProductOptionsRepository productOptionsRepository;
    private final CoupleRepository coupleRepository;

    public Long createFunding(CreateFundingCommand command) {
        ProductOptions productOptions = productOptionsRepository.findByIdOrThrow(command.productOptionsId());
        Couple couple = coupleRepository.findByMemberIdOrThrow(command.memberId());
        Funding funding = command.toFunding(productOptions, couple);
        return fundingRepository.save(funding).getId();
    }
}
