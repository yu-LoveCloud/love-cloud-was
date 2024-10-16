package com.lovecloud.fundingmanagement.application;

import com.lovecloud.blockchain.application.WeddingCrowdFundingService;
import com.lovecloud.blockchain.domain.Wallet;
import com.lovecloud.blockchain.exception.FundingBlockchainException;
import com.lovecloud.blockchain.exception.NotFoundWalletException;
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

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

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

        Funding funding = createFundingEntity(command);
        registerFundingOnBlockchain(funding);

        fundingRepository.save(funding);

        return funding.getId();
    }

    private Funding createFundingEntity(CreateFundingCommand command) {
        ProductOptions productOptions = productOptionsRepository.findByIdOrThrow(command.productOptionsId());
        Couple couple = coupleRepository.findByMemberIdOrThrow(command.memberId());

        validateWalletExists(couple);

        return command.toFunding(productOptions, couple);
    }

    private void registerFundingOnBlockchain(Funding funding) {
        try {
            BigInteger goal = BigInteger.valueOf(funding.getTargetAmount());
            BigInteger duration = calculateDuration(funding);
            validateDuration(duration);

            String keyfileName = funding.getCouple().getWallet().getKeyfile();

            log.info("블록체인에 펀딩 등록 시작. Target Amount: {}, Duration: {}, Keyfile: {}", goal, duration, keyfileName);
            BigInteger blockchainFundingId = weddingCrowdFundingService.createCrowdfundingOnBlockchain(goal, duration, keyfileName);
            log.info("블록체인 펀딩 등록 완료. Blockchain Funding ID: {}", blockchainFundingId);

            funding.assignBlockchainFundingId(blockchainFundingId);
        } catch (Exception e) {
            log.error("블록체인에 펀딩 등록 중 오류 발생: {}", e.getMessage(), e);
            throw new FundingBlockchainException("블록체인에 펀딩을 등록하는 중 오류가 발생했습니다.");
        }
    }

    private void validateWalletExists(Couple couple) {
        Wallet wallet = couple.getWallet();
        if (wallet == null) {
            throw new NotFoundWalletException();
        }
    }

    private BigInteger calculateDuration(Funding funding) {
        LocalDateTime now = LocalDateTime.now();
        return BigInteger.valueOf(funding.getEndDate().toEpochSecond(ZoneOffset.UTC) - now.toEpochSecond(ZoneOffset.UTC));
    }

    private void validateDuration(BigInteger duration) {
        if (duration.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("마감 시간이 현재 시간보다 이전입니다.");
        }
    }
}
