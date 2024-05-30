package com.lovecloud.fundingmanagement.application;

import com.lovecloud.fundingmanagement.application.command.CompleteParticipationCommand;
import com.lovecloud.fundingmanagement.application.command.ParticipateFundingCommand;
import com.lovecloud.fundingmanagement.application.validator.FundingValidator;
import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.fundingmanagement.domain.GuestFunding;
import com.lovecloud.fundingmanagement.domain.ParticipationStatus;
import com.lovecloud.fundingmanagement.domain.repository.FundingRepository;
import com.lovecloud.fundingmanagement.domain.repository.GuestFundingRepository;
import com.lovecloud.fundingmanagement.query.response.ParticipateFundingResponse;
import com.lovecloud.global.util.DateUuidGenerator;
import com.lovecloud.payment.domain.Payment;
import com.lovecloud.payment.domain.repository.PaymentRepository;
import com.lovecloud.payment.exception.InvalidPaymentStatusException;
import com.lovecloud.usermanagement.domain.Guest;
import com.lovecloud.usermanagement.domain.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class FundingParticipationService {

    private final GuestRepository guestRepository;
    private final FundingRepository fundingRepository;
    private final GuestFundingRepository guestFundingRepository;
    private final PaymentRepository paymentRepository;
    private final FundingValidator fundingValidator;

    public ParticipateFundingResponse participateInFunding(ParticipateFundingCommand command) {
        Guest guest = guestRepository.findByIdOrThrow(command.memberId());
        Funding funding = fundingRepository.findByIdOrThrow(command.fundingId());
        String merchantUid = DateUuidGenerator.generateDateUuid();
        GuestFunding guestFunding = command.toGuestFunding(guest, funding, merchantUid);
        GuestFunding savedGuestFunding = guestFundingRepository.save(guestFunding);

        return ParticipateFundingResponse.from(savedGuestFunding);
    }

    public void completeParticipation(CompleteParticipationCommand command) {
        GuestFunding guestFunding = guestFundingRepository.findByIdOrThrow(command.participationId());
        Payment payment = paymentRepository.findByIdOrThrow(command.paymentId());

        fundingValidator.validatePaymentStatus(payment);
        Funding funding = guestFunding.getFunding();
        fundingValidator.validateFundingAmount(funding, payment.getAmount());

        guestFunding.linkPayment(payment);
        guestFunding.updateStatus(ParticipationStatus.PAID);
        funding.increaseCurrentAmount(payment.getAmount());

        guestFundingRepository.save(guestFunding);
        fundingRepository.save(funding);
    }
}
