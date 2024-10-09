package com.lovecloud.fundingmanagement.application;

import com.lovecloud.auth.domain.GuestRepository;
import com.lovecloud.blockchain.application.WalletPathResolver;
import com.lovecloud.blockchain.application.WeddingCrowdFundingService;
import com.lovecloud.blockchain.exception.FundingBlockchainException;
import com.lovecloud.fundingmanagement.application.command.CompleteParticipationCommand;
import com.lovecloud.fundingmanagement.application.command.ParticipateFundingCommand;
import com.lovecloud.fundingmanagement.application.validator.FundingValidator;
import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.fundingmanagement.domain.GuestFunding;
import com.lovecloud.fundingmanagement.domain.ParticipationStatus;
import com.lovecloud.fundingmanagement.domain.repository.FundingRepository;
import com.lovecloud.fundingmanagement.domain.repository.GuestFundingRepository;
import com.lovecloud.fundingmanagement.exception.DuplicateParticipationException;
import com.lovecloud.fundingmanagement.query.response.ParticipateFundingResponse;
import com.lovecloud.global.util.DateUuidGenerator;
import com.lovecloud.payment.domain.Payment;
import com.lovecloud.payment.domain.repository.PaymentRepository;
import com.lovecloud.usermanagement.domain.Guest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class FundingParticipationService {

    private final WeddingCrowdFundingService weddingCrowdFundingService;
    private final GuestRepository guestRepository;
    private final FundingRepository fundingRepository;
    private final GuestFundingRepository guestFundingRepository;
    private final PaymentRepository paymentRepository;
    private final FundingValidator fundingValidator;

    public ParticipateFundingResponse participateInFunding(ParticipateFundingCommand command) {
        Guest guest = guestRepository.findByIdOrThrow(command.memberId());
        Funding funding = fundingRepository.findByIdOrThrow(command.fundingId());

        // 유효성 검사
        fundingValidator.validateFundingStatus(funding);
        fundingValidator.validateTargetAmountNotExceeded(funding, command.fundingAmount());

        // 중복 참여 검사
        List<ParticipationStatus> statuses = Arrays.asList(ParticipationStatus.PENDING, ParticipationStatus.PAID);
        boolean hasAlreadyParticipated = guestFundingRepository.existsByGuestAndFundingWithStatuses(guest, funding, statuses);
        if (hasAlreadyParticipated) {
            throw new DuplicateParticipationException();
        }

        // 펀딩 참여 정보 생성 및 저장
        String merchantUid = DateUuidGenerator.generateDateUuid();
        GuestFunding guestFunding = command.toGuestFunding(guest, funding, merchantUid);
        guestFunding = guestFundingRepository.save(guestFunding);

        return ParticipateFundingResponse.from(guestFunding);
    }

    public void completeParticipation(CompleteParticipationCommand command) {
        GuestFunding guestFunding = guestFundingRepository.findByIdOrThrow(command.guestFundingId());
        Payment payment = paymentRepository.findByIdOrThrow(command.paymentId());

        // 유효성 검사
        fundingValidator.validatePaymentStatus(payment);
        Funding funding = guestFunding.getFunding();
        fundingValidator.validateFundingStatus(funding);
        fundingValidator.validateTargetAmountNotExceeded(funding, payment.getAmount());
        fundingValidator.validateMatchingMerchantUids(guestFunding.getMerchantUid(), payment.getMerchantUid());
        fundingValidator.validateMatchingAmounts(guestFunding.getFundingAmount(), payment.getAmount());

        try {
            String walletFilePath = WalletPathResolver.resolveWalletPath(guestFunding.getGuest().getWallet().getKeyfile());

            // 블록체인 연동 - 토큰 사용 승인 및 펀딩 참여
            String transactionHash = weddingCrowdFundingService.approveAndContribute(
                    BigInteger.valueOf(funding.getId()),
                    BigInteger.valueOf(payment.getAmount()),
                    walletFilePath
            );

            log.info("블록체인 트랜잭션 해시: {}", transactionHash);

        } catch (Exception e) {
            throw new FundingBlockchainException("블록체인 연동 중 오류가 발생하였습니다.");
        }

        // GuestFunding 업데이트
        guestFunding.linkPayment(payment);
        guestFunding.updateStatus(ParticipationStatus.PAID);
        guestFundingRepository.save(guestFunding);

        // Funding 업데이트
        funding.increaseCurrentAmount(payment.getAmount());
        fundingRepository.save(funding);
    }
}
