package com.lovecloud.fundingmanagement.application;

import com.lovecloud.blockchain.application.WeddingCrowdFundingService;
import com.lovecloud.blockchain.exception.FundingBlockchainException;
import com.lovecloud.fundingmanagement.application.command.CreateFundingCommand;
import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.fundingmanagement.domain.repository.FundingRepository;
import com.lovecloud.productmanagement.domain.ProductOptions;
import com.lovecloud.productmanagement.domain.repository.ProductOptionsRepository;
import com.lovecloud.usermanagement.domain.Couple;
import com.lovecloud.usermanagement.domain.repository.CoupleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class FundingCreationService {

    private final FundingRepository fundingRepository;
    private final ProductOptionsRepository productOptionsRepository;
    private final CoupleRepository coupleRepository;
    private final WeddingCrowdFundingService weddingCrowdFundingService;

    public Long createFunding(CreateFundingCommand command) {

        log.info("펀딩 생성 프로세스 시작: 제품 옵션 ID: {}, 회원 ID: {}", command.productOptionsId(), command.memberId());

        ProductOptions productOptions = productOptionsRepository.findByIdOrThrow(command.productOptionsId());
        Couple couple = coupleRepository.findByMemberIdOrThrow(command.memberId());

        Funding funding = command.toFunding(productOptions, couple);
        funding = fundingRepository.save(funding);
        log.info("펀딩이 저장되었습니다. 펀딩 ID: {}", funding.getId());

        try {
            log.info("블록체인에 펀딩 등록 시도 중. 펀딩 ID: {}", funding.getId());
            weddingCrowdFundingService.createCrowdfundingOnBlockchain(funding);
            log.info("블록체인에 펀딩이 성공적으로 등록되었습니다. 펀딩 ID: {}", funding.getId());
        } catch (Exception e) {
            log.error("블록체인에 펀딩 등록 중 오류 발생. 펀딩 ID: {}", funding.getId(), e);
            throw new FundingBlockchainException("블록체인에 펀딩을 등록하는 중 오류가 발생했습니다.");
        }

        return funding.getId();
    }
}
